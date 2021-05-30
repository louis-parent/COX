package cox.writer.stringifier.element;

import java.util.Collection;

import cox.model.document.XMLDocument;
import cox.model.document.element.XMLProcessingInstructionElement;
import cox.model.utils.XMLIdentifier;
import cox.writer.XMLOutputOptions;
import cox.writer.stringifier.ParametrableXMLStringifier;

public class XMLDocumentStringifier implements ParametrableXMLStringifier
{
	private static final XMLIdentifier XML_DECLARATION_IDENTIFIER = new XMLIdentifier("xml");
	
	private XMLDocument document;
	private Collection<XMLOutputOptions> options;
	
	public XMLDocumentStringifier(XMLDocument document, Collection<XMLOutputOptions> options)
	{
		this.document = document;
		this.options = options;
	}
	
	@Override
	public Collection<XMLOutputOptions> getOptions()
	{
		return this.options;
	}
	
	@Override
	public String toString()
	{
		return this.toString(0);
	}

	@Override
	public String toString(int depth)
	{
		String doc = "";
		
		if(this.hasToWriteDeclaration())
		{
			doc += new XMLProcessingInstructionStringifier(new XMLProcessingInstructionElement(XMLDocumentStringifier.XML_DECLARATION_IDENTIFIER, this.getXMLDeclarationValue()), this.getOptions()).toString(depth);
			
			if(this.isIndented())
			{
				doc += "\n";
			}
		}
		
		doc += new XMLElementStringifier(this.document.getRoot(), this.getOptions()).toString(depth);
		
		return doc;
	}

	private String getXMLDeclarationValue()
	{
		return "version=\"" + this.document.getVersion() + "\" encoding=\"" + this.document.getEncoding() + "\" standalone=\"" + (this.document.isStandalone() ? "yes" : "no") + "\"";
	}
}
