package cox.parser.automaton;

import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashSet;

import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.element.XMLElement;
import cox.model.element.XMLPCDataElement;
import cox.model.pi.XMLProcessingInstruction;
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
	
	private Collection<XMLProcessingInstruction> processingInstructions;

	private XMLAutomatonState state;

	public XMLAutomaton(InputStream input) throws COXParserException
	{
		this.version = null;
		this.encoding = null;
		this.standalone = null;
		this.processingInstructions = new HashSet<XMLProcessingInstruction>();
		
		
		this.state = new StartState();
		this.readAll(input);
	}

	public void putElement(XMLPCDataElement element) throws InvalidContentOutsideTagException
	{
		if(this.currentElement == null)
		{
			throw new InvalidContentOutsideTagException();
		}
		else
		{
			this.currentElement.appendChild(element);
		}
	}

	public void putElement(XMLElement element)
	{
		if(this.root == null)
		{
			this.root = element;
			this.currentElement = this.root;
		}
		else
		{
			this.currentElement.appendChild(element);
			this.currentElement = element;
		}
	}

	public void closeElement(String tagName) throws COXAutomatonException
	{
		if(this.currentElement == null)
		{
			throw new MissingOpeningTagException(tagName);
		}
		else if(this.currentElement.getTagName().equals(tagName))
		{
			if(this.currentElement.isRoot())
			{
				this.currentElement = null;
			}
			else
			{
				this.currentElement = this.currentElement.getParent();
			}
		}
		else
		{
			throw new MissingClosingTagException(tagName, this.currentElement.getTagName());
		}
	}
	
	public void putProcessingInstruction(XMLProcessingInstruction pi) throws COXAutomatonException
	{
		if(pi.getKey().equals("xml"))
		{
			this.parseXMLDeclaration(pi);
		}
		else
		{
			this.processingInstructions.add(pi);			
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
		
		return new SimpleXMLDocument(version, encoding, standalone, this.root, this.processingInstructions);
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

			if(this.currentElement != null)
			{
				throw new COXParserException("The tag " + this.currentElement.getTagName() + " is never closed", (char) c, currentLine, currentChar);
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
	
	private void parseXMLDeclaration(XMLProcessingInstruction pi) throws COXAutomatonException
	{
		for(String piValue : pi.getValues())
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
