package cox.model.element;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import cox.model.attribute.XMLAttribute;

public abstract class XMLElement
{
	private XMLElement parent = this;

	public XMLElement()
	{

	}

	public XMLElement(XMLElement parent)
	{
		this.attach(parent);
	}

	public abstract String getTagName();

	public abstract String getValue();
	
	public abstract boolean isPCData();

	public abstract Collection<XMLAttribute> getAttributes();

	public abstract void addAttribute(XMLAttribute attribute);

	public abstract void removeAttribute(XMLAttribute attribute);

	public abstract List<XMLElement> getChildren();

	public abstract boolean appendChild(XMLElement element);

	public abstract boolean removeChild(XMLElement element);

	public void attach(XMLElement parent)
	{
		parent.appendChild(this);
	}

	public XMLElement getParent()
	{
		return this.parent;
	}

	public boolean isRoot()
	{
		return this.getParent() == this;
	}

	public boolean isLeaf()
	{
		return this.getChildren().size() == 0;
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

	public boolean equals(XMLElement element)
	{
		return this.getTagName().equals(element.getTagName()) && this.getValue().equals(element.getValue()) && hasSameAttributes(element);
	}

	public boolean ascendingEquals(XMLElement element)
	{
		return this.equals(element) && this.getParent().ascendingEquals(element.getParent());
	}

	public boolean descendingEquals(XMLElement element)
	{
		List<XMLElement> otherChildren = element.getChildren();

		if(this.isLeaf())
		{
			return this.equals(element);
		}
		else
		{
			boolean allChildrenEquals = this.getChildren().stream().allMatch(child -> {
				int index = otherChildren.indexOf(child);
				if(index > -1)
				{
					return child.descendingEquals(otherChildren.get(index));
				}
				else
				{
					return false;
				}
			});

			return this.equals(element) && allChildrenEquals;
		}
	}

	protected void setParent(XMLElement parent)
	{
		this.parent.removeChild(this);
		this.parent = parent;
	}
	
	private boolean hasSameAttributes(XMLElement element)
	{
		boolean allAttributesEquals = true;
		
		for(XMLAttribute attributeOfThis : this.getAttributes())
		{
			boolean attributeFound = false;
			
			for(Iterator<XMLAttribute> iterator = element.getAttributes().iterator(); iterator.hasNext() && !attributeFound;)
			{
				attributeFound = attributeOfThis.equals(iterator.next());
			}
			
			allAttributesEquals &= attributeFound;
		}
		
		return allAttributesEquals;
	}
}
