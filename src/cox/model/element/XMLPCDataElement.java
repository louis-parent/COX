package cox.model.element;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

import cox.model.attribute.XMLAttribute;

public class XMLPCDataElement extends XMLElement
{
	private static final String PCDATA_TAG_NAME = "pcdata";

	private String value;

	public XMLPCDataElement(String value, XMLElement parent)
	{
		this(value);
		this.attach(parent);
	}

	public XMLPCDataElement(String value)
	{
		super();
		this.value = value.trim();
	}

	@Override
	public String getTagName()
	{
		return XMLPCDataElement.PCDATA_TAG_NAME;
	}

	@Override
	public String getValue()
	{
		return this.value;
	}
	
	@Override
	public boolean isPCData()
	{
		return true;
	}

	@Override
	public Collection<XMLAttribute> getAttributes()
	{
		return Collections.emptySet();
	}

	@Override
	public void addAttribute(XMLAttribute attribute)
	{

	}

	@Override
	public void removeAttribute(XMLAttribute attribute)
	{

	}

	@Override
	public List<XMLElement> getChildren()
	{
		return Collections.emptyList();
	}

	@Override
	public boolean appendChild(XMLElement element)
	{
		return false;
	}

	@Override
	public boolean removeChild(XMLElement element)
	{
		return false;
	}

	@Override
	public String toString()
	{
		return this.getValue();
	}
}
