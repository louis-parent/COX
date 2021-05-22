package cox.model.document;

import java.util.Collection;

import cox.model.element.XMLElement;
import cox.model.pi.XMLProcessingInstruction;

public interface XMLDocument
{
	public static final String DEFAULT_VERSION = "1.0";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final boolean DEFAULT_STANDALONE = false;
	
	public abstract String getVersion();
	public abstract String getEncoding();
	public abstract boolean isStandalone();
	
	public abstract Collection<XMLProcessingInstruction> getProcessingInstructions();
	
	public abstract XMLElement getRoot();
	
	public default boolean isEmpty()
	{
		return this.getRoot() == null;
	}

	public default boolean equals(XMLDocument doc)
	{
		return this.getRoot().equals(doc.getRoot());
	}
	
	public static XMLDocument getEmpty()
	{
		return new SimpleXMLDocument(null);
	}
}
