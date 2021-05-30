package cox.model.document.element;

import cox.model.XMLElementType;

public class XMLTextElement extends XMLUnnamedElement
{
	public XMLTextElement(String value)
	{
		super(value);
	}
	
	public XMLTextElement(XMLElement parent, String value)
	{
		super(parent, value);
	}
	
	@Override
	public XMLElementType getType()
	{
		return XMLElementType.TEXT;
	}
	
	@Override
	public String toString()
	{
		return this.getValue();
	}
}
