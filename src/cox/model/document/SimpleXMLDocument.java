package cox.model.document;

import cox.model.document.element.XMLElement;

public class SimpleXMLDocument implements XMLDocument
{
	private String version;
	private String encoding;
	private boolean standalone;
		
	private XMLElement root;

	public SimpleXMLDocument(XMLElement root)
	{
		this(XMLDocument.DEFAULT_VERSION, XMLDocument.DEFAULT_ENCODING, XMLDocument.DEFAULT_STANDALONE, root);
	}
	
	public SimpleXMLDocument(String version, String encoding, boolean standalone, XMLElement root)
	{
		this.version = version;
		this.encoding = encoding;
		this.standalone = standalone;
		
		this.root = root;
	}

	@Override
	public String getVersion()
	{
		return this.version;
	}
	
	@Override
	public String getEncoding()
	{
		return this.encoding;
	}
	
	@Override
	public boolean isStandalone()
	{
		return this.standalone;
	}

	@Override
	public XMLElement getRoot()
	{
		return this.root;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof XMLDocument)
		{
			return this.equals((XMLDocument) obj);
		}
		else
		{
			return false;
		}
	}

	@Override
	public String toString()
	{
		if(this.isEmpty())
		{
			return "Empty Document";
		}
		else
		{
			return this.getRoot().toString();
		}
	}
}
