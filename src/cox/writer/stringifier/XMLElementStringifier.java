package cox.writer.stringifier;

import java.util.Collection;
import java.util.stream.Collectors;

import cox.model.attribute.XMLAttribute;
import cox.model.element.XMLElement;
import cox.writer.XMLOutputOptions;

public class XMLElementStringifier implements ParametrableXMLWriter
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
	
	public String toString(int depth)
	{
		String indentation = this.getIndentation(depth);
		
		if(this.element.isPCData())
		{
			String value = this.element.getValue();
			
			if(this.hasToTrim())
			{
				value = value.trim();
			}
			
			return indentation + value;
		}
		else if(this.element.isLeaf() && !this.element.isRoot() && this.useOrphans())
		{
			return indentation + this.getOrphanTag();
		}
		else
		{
			String tag = indentation + this.getOpenTag() + getLineSeparator();
			tag += this.element.isLeaf() ? "" : this.getContent(depth+1) + getLineSeparator();
			tag += indentation + this.getCloseTag();
			
			return tag;
		}
	}

	private String getIndentation(int depth)
	{
		String indent = "";
		
		if(this.isIndented())
		{
			for(int i = 0; i < depth; i++)
			{
				indent += "\t";
			}
		}
		
		return indent;
	}
	
	private String getOrphanTag()
	{
		return "<" + this.element.getTagName() + this.getAttributesStringList() + "/>";
	}
	
	private String getOpenTag()
	{
		return "<" + this.element.getTagName() + this.getAttributesStringList() + ">";
	}

	private String getAttributesStringList()
	{
		String attributesString = "";
		
		Collection<XMLAttribute> attributes = this.element.getAttributes();
		if(attributes.size() > 0)
		{
			for(XMLAttribute attribute : attributes)
			{				
				attributesString += " " + attribute.getName() + "=\"" + attribute.getValue() + "\"";
			}
		}
		return attributesString;
	}
	
	private String getContent(int depth)
	{
		return this.element.getChildren().stream().map(child -> {
			return new XMLElementStringifier(child, this.options).toString(depth);
		}).collect(Collectors.joining(getLineSeparator()));
	}

	private String getLineSeparator()
	{
		if(this.isIndented())
		{
			return "\n";
		}
		else
		{
			return "";
		}
	}
	
	private String getCloseTag()
	{
		return "</" + this.element.getTagName() + ">";
	}
}
