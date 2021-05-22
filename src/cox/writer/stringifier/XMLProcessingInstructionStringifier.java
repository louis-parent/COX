package cox.writer.stringifier;

import java.util.Arrays;
import java.util.Collection;

import cox.model.document.XMLDocument;
import cox.model.pi.XMLProcessingInstruction;
import cox.writer.XMLOutputOptions;

public class XMLProcessingInstructionStringifier implements ParametrableXMLWriter
{
	private String key;
	private Collection<String> values;
	
	private Collection<XMLOutputOptions> options;
	
	public XMLProcessingInstructionStringifier(XMLProcessingInstruction pi, Collection<XMLOutputOptions> options)
	{
		this(pi.getKey(), pi.getValues(), options);
	}
	
	public XMLProcessingInstructionStringifier(XMLDocument doc, Collection<XMLOutputOptions> options)
	{
		this("xml", Arrays.asList("version=\"" + doc.getVersion() + "\"", "encoding=\"" + doc.getEncoding() + "\"", "standalone=\"" + (doc.isStandalone() ? "yes" : "no") + "\""), options);
	}
	
	public XMLProcessingInstructionStringifier(String key, Collection<String> values, Collection<XMLOutputOptions> options)
	{
		this.key = key;
		this.values = values;
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
		return "<?" + this.key + " " + String.join(" ", this.values) + "?>" + (this.isIndented() ? "\n" : "");
	}
}
