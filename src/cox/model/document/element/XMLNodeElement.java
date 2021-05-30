package cox.model.document.element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import cox.model.XMLElementType;
import cox.model.utils.XMLIdentifier;

public class XMLNodeElement implements XMLElement
{	
	private XMLElement parent;

	private Map<String, String> attributes;
	private List<XMLElement> children;
	
	private XMLIdentifier identifier;
	
	public XMLNodeElement(String name)
	{
		this(new XMLIdentifier(name));
	}
	
	public XMLNodeElement(XMLIdentifier identifier)
	{
		this(null, identifier);
	}

	public XMLNodeElement(XMLElement parent, String name)
	{
		this(parent, new XMLIdentifier(name));
	}
	
	public XMLNodeElement(XMLElement parent, XMLIdentifier identifier)
	{
		this.parent = this;
		this.attributes = new HashMap<String, String>();
		this.children = new ArrayList<XMLElement>();
		
		this.identifier = identifier;

		if(parent != null)
		{
			this.attachTo(parent);
		}		
	}

	@Override
	public XMLElementType getType()
	{
		return XMLElementType.NODE;
	}

	@Override
	public XMLElement getParent()
	{
		return this.parent;
	}

	@Override
	public void setParent(XMLElement parent)
	{
		this.parent.removeChild(this);
		this.parent = parent;
	}

	@Override
	public XMLIdentifier getIdentifier()
	{
		return this.identifier;
	}

	@Override
	public void setIdentifier(XMLIdentifier identifier)
	{
		this.identifier = identifier;
	}

	@Override
	public String getValue()
	{
		return this.getChildren().stream().map(child -> {
			return child.getValue();
		}).collect(Collectors.joining("\n"));
	}

	@Override
	public void setValue(String value)
	{
	}

	@Override
	public Map<String, String> getAttributes()
	{
		return this.attributes;
	}

	@Override
	public List<XMLElement> getChildren()
	{
		return this.children;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof XMLElement)
		{
			return this.equals((XMLElement) obj);
		}
		else
		{
			return false;
		}
	}
	
	@Override
	public String toString()
	{
		String str = this.getIdentifier().toString() + "(";
		
		str += String.join(", ", this.getAttributes().entrySet().stream().map(attr -> {
			return attr.getKey() + "=" + attr.getValue();
		}).collect(Collectors.toList()));
		
		str += ")[";
		
		str += String.join(", ", this.getChildren().stream().map(child -> {
			return child.toString();
		}).collect(Collectors.toList()));
		
		return str + "]";
	}
}
