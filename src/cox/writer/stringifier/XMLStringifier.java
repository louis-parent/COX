package cox.writer.stringifier;

import java.util.Collection;

import cox.model.document.XMLDocument;
import cox.writer.XMLOutputOptions;

public class XMLStringifier
{
	private XMLDocument document;
	private Collection<XMLOutputOptions> options;
	
	public XMLStringifier(XMLDocument document, Collection<XMLOutputOptions> options)
	{
		this.document = document;
		this.options = options;
	}
	
	@Override
	public String toString()
	{
		if(this.document.isEmpty())
		{
			return "";
		}
		else
		{
			return new XMLElementStringifier(document.getRoot(), this.options).toString();
		}
	}
}
