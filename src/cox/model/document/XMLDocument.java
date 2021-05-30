package cox.model.document;

import cox.model.document.element.XMLElement;

public interface XMLDocument
{
	public static final String DEFAULT_VERSION = "1.0";
	public static final String DEFAULT_ENCODING = "UTF-8";
	public static final boolean DEFAULT_STANDALONE = false;
	
	public abstract String getVersion();
	public abstract String getEncoding();
	public abstract boolean isStandalone();
		
	public abstract XMLElement getRoot();
	
	public default boolean isEmpty()
	{
		return this.getRoot().isLeaf();
	}

	public default boolean equals(XMLDocument doc)
	{
		return this.getRoot().equals(doc.getRoot()) && this.getVersion().equals(doc.getVersion()) && this.getEncoding().equals(doc.getEncoding()) && this.isStandalone() == doc.isStandalone();
	}
	
	public static XMLDocument getEmpty()
	{
		return new SimpleXMLDocument(XMLElement.getEmptyNode());
	}
}
