package cox.writer.stringifier;

import java.util.Collection;

import cox.model.document.XMLDocument;
import cox.writer.XMLOutputOptions;
import cox.writer.stringifier.element.XMLDocumentStringifier;

public class XMLStringifier implements ParametrableXMLStringifier
{
	private XMLDocument document;
	private Collection<XMLOutputOptions> options;
	
	public XMLStringifier(XMLDocument document, Collection<XMLOutputOptions> options)
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
		if(this.document.isEmpty())
		{
			return "";
		}
		else
		{
			return new XMLDocumentStringifier(this.document, this.getOptions()).toString(depth);
		}		
	}
}
