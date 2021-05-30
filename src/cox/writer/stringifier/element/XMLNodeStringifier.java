package cox.writer.stringifier.element;

import java.util.Collection;
import java.util.stream.Collectors;

import cox.model.document.element.XMLElement;
import cox.writer.XMLOutputOptions;
import cox.writer.stringifier.ParametrableXMLStringifier;

public class XMLNodeStringifier implements ParametrableXMLStringifier
{
	private XMLElement element;
	private Collection<XMLOutputOptions> options;

	public XMLNodeStringifier(XMLElement element, Collection<XMLOutputOptions> options)
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
		int innerDepth = this.element.isFictiveRoot() ? depth : depth+1;
		
		String content = this.element.getChildren().stream().map(child -> {
			return new XMLElementStringifier(child, this.getOptions()).toString(innerDepth);
		}).collect(Collectors.joining(this.getChildrenSeparator()));

		if(this.element.isFictiveRoot())
		{
			return content;
		}
		else
		{
			String openTag = this.getIndentation(depth) + "<" + this.element.getIdentifier().toString();
			
			if(this.element.getAttributes().size() > 0)
			{
				openTag += " " + this.pairsToAttributeString(this.element.getAttributes());
			}
			
			openTag += ">" + this.getChildrenSeparator();				
			
			String closeTag = this.getIndentation(depth) + "</" + this.element.getIdentifier().toString() + ">";
			
			if(content.length() > 0)
			{
				closeTag = this.getChildrenSeparator() + closeTag;
			}
			
			return openTag + content + closeTag;
		}
	}
	
	private String getChildrenSeparator()
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
}
