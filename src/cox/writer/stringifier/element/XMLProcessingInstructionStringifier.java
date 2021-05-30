package cox.writer.stringifier.element;

import java.util.Collection;

import cox.model.document.element.XMLElement;
import cox.writer.XMLOutputOptions;
import cox.writer.stringifier.ParametrableXMLStringifier;

public class XMLProcessingInstructionStringifier implements ParametrableXMLStringifier
{
	private XMLElement element;
	private Collection<XMLOutputOptions> options;

	public XMLProcessingInstructionStringifier(XMLElement element, Collection<XMLOutputOptions> options)
	{
		this.element = element;
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
		return this.getIndentation(depth) + "<?" + this.element.getIdentifier().toString() + " " + this.element.getValue() + "?>";
	}
}
