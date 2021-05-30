package cox.model.document.element;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import cox.model.XMLElementType;
import cox.model.utils.XMLIdentifier;

public class XMLProcessingInstructionElement implements XMLElement
{
	private XMLElement parent;
	
	private XMLIdentifier identifier;
	private String value;
	
	public XMLProcessingInstructionElement(XMLIdentifier identifier)
	{
		this(identifier, "");
	}
	
	public XMLProcessingInstructionElement(XMLIdentifier identifier, String value)
	{
		this(null, identifier, value);
	}
	
	public XMLProcessingInstructionElement(XMLElement parent, XMLIdentifier identifier, String value)
	{		
		this.parent = this;
		this.identifier = identifier;
		this.value = value;

		if(parent != null)
		{
			this.attachTo(parent);
		}
	}
	
	@Override
	public XMLElementType getType()
	{
		return XMLElementType.PROCESSING_INSTRUCTION;
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
	
	@Override
	public String toString()
	{
		return this.getIdentifier().toString() + " {" + this.getValue() + "}";
	}
}
