package cox.model.document;

import cox.model.element.XMLElement;

public interface XMLDocument
{
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
