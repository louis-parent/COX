package cox.model.document.element;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cox.model.utils.XMLIdentifier;

public abstract class XMLUnnamedElement implements XMLElement
{
	private XMLElement parent;
	private String value;
	
	public XMLUnnamedElement()
	{
		this("");
	}
	
	public XMLUnnamedElement(String value)
	{
		this(null, value);
	}
	
	public XMLUnnamedElement(XMLElement parent, String value)
	{
		this.parent = this;
		this.value = value;

		if(parent != null)
		{
			this.attachTo(parent);
		}
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
		return XMLIdentifier.IMMUTABLE_EMPTY_IDENTIFIER;
	}
	
	@Override
	public void setIdentifier(XMLIdentifier identifier)
	{
	}
	
	@Override
	public String getValue()
	{
		return this.value;
	}
	
	@Override
	public void setValue(String value)
	{
		this.value = value;
	}

	@Override
	public Map<String, String> getAttributes()
	{
		return Collections.emptyMap();
	}
	
	@Override
	public List<XMLElement> getChildren()
	{
		return Collections.emptyList();
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
}
