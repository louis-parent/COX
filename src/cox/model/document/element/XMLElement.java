package cox.model.document.element;

import java.util.List;
import java.util.Map;

import cox.model.XMLElementType;
import cox.model.utils.XMLIdentifier;

public interface XMLElement
{
	public abstract XMLElementType getType();
	
	public abstract XMLElement getParent();
	public abstract void setParent(XMLElement parent);
	
	public abstract XMLIdentifier getIdentifier();
	public abstract void setIdentifier(XMLIdentifier identifier);
	
	
	public abstract String getValue();
	public abstract void setValue(String value);
	
	
	public abstract Map<String, String> getAttributes();
	
	public default String getAttribute(String key)
	{
		return this.getAttributes().get(key);
	}
	
	public default boolean addAttribute(String key, String value)
	{
		if(this.getAttributes().containsKey(key))
		{
			return false;
		}
		else
		{
			return this.getAttributes().put(key, value) == null;
		}
	}
	
	public default boolean setAttribute(String key, String value)
	{
		if(this.getAttributes().containsKey(key))
		{
			return this.getAttributes().put(key, value) != null;
		}
		else
		{
			return false;
		}
	}
	
	public default boolean removeAttribute(String key)
	{
		return this.getAttributes().remove(key) != null;
	}
	
	
	public abstract List<XMLElement> getChildren();
	
	public default boolean appendChild(XMLElement element)
	{
		boolean childAdded = this.getChildren().add(element);
		
		if(childAdded)
		{
			element.getParent().removeChild(element);
			element.setParent(this);
		}
		
		return childAdded;
	}
	
	public default boolean removeChild(XMLElement element)
	{
		return this.getChildren().remove(element);
	}

	public default boolean attachTo(XMLElement element)
	{
		return element.appendChild(this);
	}
	
	
	public default boolean isFictiveRoot()
	{
		return this.isRoot() && this.getIdentifier().equals(XMLIdentifier.IMMUTABLE_EMPTY_IDENTIFIER);
	}
	
	public default boolean isRoot()
	{
		return this.getParent() == this;
	}
	
	public default boolean isLeaf()
	{
		return this.getChildren().size() == 0;
	}
	
	public default boolean hasChild(XMLElement element)
	{
		return this.getChildren().contains(element);
	}
	
	public default boolean equals(XMLElement other)
	{
		return this.getIdentifier().equals(other.getIdentifier()) && this.getValue().equals(other.getValue()) && this.getAttributes().equals(other.getAttributes());
	}
	
	public default boolean ascendingEquals(XMLElement other)
	{
		return this.equals(other) && this.getParent().ascendingEquals(other.getParent());
	}
	
	public default boolean descendingEquals(XMLElement other)
	{
		boolean equals = this.equals(other);
		
		for(int i = 0; i < this.getChildren().size(); i++)
		{
			equals &= this.getChildren().get(i).descendingEquals(other.getChildren().get(i));
		}
		
		return equals;
	}
	
	
	public static XMLElement getEmptyLeaf()
	{
		return new XMLTextElement("");
	}
	
	public static XMLElement getEmptyNode()
	{
		return new XMLNodeElement(XMLIdentifier.IMMUTABLE_EMPTY_IDENTIFIER);
	}
}
