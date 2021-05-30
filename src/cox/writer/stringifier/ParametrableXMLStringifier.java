package cox.writer.stringifier;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import cox.writer.XMLOutputOptions;

public interface ParametrableXMLStringifier
{
	public abstract Collection<XMLOutputOptions> getOptions();
	
	public default boolean isIndented()
	{
		return this.getOptions().contains(XMLOutputOptions.INDENT);
	}
	
	public default boolean useOrphans()
	{
		return this.getOptions().contains(XMLOutputOptions.SIMPLIFY_ORPHANS);
	}
	
	public default boolean hasToTrim()
	{
		return this.getOptions().contains(XMLOutputOptions.TRIM_CONTENT);
	}
	
	public default boolean hasToWriteDeclaration()
	{
		return this.getOptions().contains(XMLOutputOptions.WRITE_DECLARATION);
	}
	
	public default String pairsToAttributeString(Map<String, String> pairs)
	{
		String attributes = "";
		
		for(Entry<String, String> pair : pairs.entrySet())
		{
			attributes += pair.getKey() + "=\"" + pair.getValue() + "\" ";
		}
		
		if(attributes.length() > 0)
		{
			return attributes.substring(0, attributes.length()-1);
		}
		else
		{
			return attributes;
		}
	}	
	
	public default String getIndentation(int depth)
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
	
	@Override
	public abstract String toString();
	
	public abstract String toString(int depth);
}
