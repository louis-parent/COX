package cox.parser.automaton;

import java.io.IOException;
import java.io.InputStream;

import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.document.element.XMLElement;
import cox.model.document.element.XMLProcessingInstructionElement;
import cox.model.document.element.XMLTextElement;
import cox.model.utils.XMLIdentifier;
import cox.parser.automaton.state.ContentState;
import cox.parser.automaton.state.StartState;
import cox.parser.automaton.state.XMLAutomatonState;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.COXParserException;
import cox.parser.exception.parsing.InvalidContentOutsideTagException;
import cox.parser.exception.parsing.MalformedXMLDeclarationException;
import cox.parser.exception.parsing.MissingClosingTagException;
import cox.parser.exception.parsing.MissingOpeningTagException;

public class XMLAutomaton
{
	private XMLElement root;
	private XMLElement currentElement;
	
	private String version;
	private String encoding;
	private Boolean standalone;
	
	private XMLAutomatonState state;

	public XMLAutomaton(InputStream input) throws COXParserException
	{
		this.root = XMLElement.getEmptyNode();
		this.currentElement = this.root;
		
		this.version = null;
		this.encoding = null;
		this.standalone = null;		
		
		this.state = new StartState();
		this.readAll(input);
	}

	public void putText(XMLTextElement element) throws InvalidContentOutsideTagException
	{
		if(this.currentElement == this.root)
		{
			throw new InvalidContentOutsideTagException();
		}
		else
		{
			this.currentElement.appendChild(element);
		}
	}

	public void openElement(XMLElement element)
	{
		this.currentElement.appendChild(element);
		this.currentElement = element;
	}

	public void closeElement(String tagName) throws COXAutomatonException
	{
		if(this.currentElement.getIdentifier().equals(tagName))
		{
			this.currentElement = this.currentElement.getParent();
		}
		else if(this.currentElement == this.root)
		{			
			throw new MissingOpeningTagException(tagName);
		}
		else
		{
			throw new MissingClosingTagException(tagName, this.currentElement.getIdentifier().toString());
		}
	}
	
	public void openCloseElement(XMLElement element)
	{
		this.currentElement.appendChild(element);
	}
	
	public void putProcessingInstruction(XMLProcessingInstructionElement pi) throws COXAutomatonException
	{
		if(pi.getIdentifier().equals(new XMLIdentifier("xml")))
		{
			this.parseXMLDeclaration(pi.getValue());
		}
		{
			this.openCloseElement(pi);			
		}
	}

	public void changeState(XMLAutomatonState state)
	{
		this.state = state;
	}

	public XMLDocument getDocument()
	{
		String version = this.version != null ? this.version : XMLDocument.DEFAULT_VERSION;
		String encoding = this.encoding != null ? this.encoding : XMLDocument.DEFAULT_ENCODING;
		boolean standalone = this.standalone != null ? this.standalone : XMLDocument.DEFAULT_STANDALONE;
		
		return new SimpleXMLDocument(version, encoding, standalone, this.root);
	}

	private void readAll(InputStream input) throws COXParserException
	{
		int c = ' ';
		
		int currentChar = 1;
		int currentLine = 1;

		try
		{
			while((c = input.read()) != -1)
			{
				try
				{
					this.state.read(this, (char) c);
				}
				catch(COXAutomatonException e)
				{
					throw new COXParserException(e.getMessage(), (char) c, currentLine, currentChar);
				}

				if(c == '\n')
				{
					currentLine++;
					currentChar = 1;
				}
				else
				{
					currentChar++;
				}
			}

			if(this.currentElement != this.root)
			{
				throw new COXParserException("The tag " + this.currentElement.getIdentifier() + " is never closed", (char) c, currentLine, currentChar);
			}
			else if(this.state instanceof ContentState)
			{
				ContentState endContent = (ContentState) this.state;
				if(endContent.hasData())
				{
					throw new COXParserException("There cannot be content after the root element", (char) c, currentLine, currentChar);
				}
			}
			else if(!(this.state instanceof StartState))
			{
				throw new COXParserException("XML root can only be composed of one root element", (char) c, currentLine, currentChar);
			}
		}
		catch(IOException e)
		{
			throw new COXParserException("Parsing Error : " + e.getMessage(), (char) c, currentLine, currentChar);
		}
	}
	
	private void parseXMLDeclaration(String piContent) throws COXAutomatonException
	{
		for(String piValue : piContent.split(" "))
		{
			String[] splitted = piValue.split("=");
			
			if(splitted.length == 2)
			{
				String key = splitted[0];
				String value = "";
				
				if(splitted[1].startsWith("\"") && splitted[1].endsWith("\""))
				{
					value = splitted[1].substring(1, splitted[1].length()-1);
					
					if(key.equals("version"))
					{
						this.version = value;
					}
					else if(key.equals("encoding"))
					{
						this.encoding = value;
					}
					else if(key.equals("standalone"))
					{
						this.standalone = value.equals("yes");
					}
				}
				else
				{
					throw new MalformedXMLDeclarationException();
				}
			}
			else
			{
				throw new MalformedXMLDeclarationException();
			}
		}
	}
}
