package cox.writer.stringifier.element;

import java.util.Collection;

import cox.model.XMLElementType;
import cox.model.document.element.XMLElement;
import cox.writer.XMLOutputOptions;
import cox.writer.stringifier.ParametrableXMLStringifier;

public class XMLElementStringifier implements ParametrableXMLStringifier
{
	private XMLElement element;
	private Collection<XMLOutputOptions> options;
	
	public XMLElementStringifier(XMLElement element, Collection<XMLOutputOptions> options)
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
		if(this.element.getType() == XMLElementType.TEXT)
		{
			return new XMLTextStringifier(this.element, this.getOptions()).toString(depth);
		}
		else if(this.element.getType() == XMLElementType.PROCESSING_INSTRUCTION)
		{
			return new XMLProcessingInstructionStringifier(this.element, this.getOptions()).toString(depth);
		}
		else if(this.element.getType() == XMLElementType.NODE)
		{
			if(this.element.isLeaf() && this.useOrphans())
			{
				return new XMLOrphanStringifier(this.element, this.getOptions()).toString(depth);
			}
			else
			{				
				return new XMLNodeStringifier(this.element, this.getOptions()).toString(depth);
			}
		}
		else
		{
			return null;
		}
	}
}
