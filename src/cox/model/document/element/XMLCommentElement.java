package cox.model.document.element;

import cox.model.XMLElementType;

public class XMLCommentElement extends XMLUnnamedElement
{
	public XMLCommentElement(String value)
	{
		super(value);
	}
	
	public XMLCommentElement(XMLElement parent, String value)
	{
		super(parent, value);
	}
	
	@Override
	public XMLElementType getType()
	{
		return XMLElementType.COMMENT;
	}
	
	@Override
	public String toString()
	{
		return "|" + this.getValue() + "|";
	}
}
