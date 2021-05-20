package cox.writer.stringifier;

import java.util.Collection;

import cox.writer.XMLOutputOptions;

public interface ParametrableXMLWriter
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
}
