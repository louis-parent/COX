package cox.model.element;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import cox.model.attribute.XMLAttribute;

public class XMLNodeElement extends XMLElement
{
	private String name;

	private Collection<XMLAttribute> attributes;
	private List<XMLElement> chidren;

	public XMLNodeElement(String name, XMLElement parent)
	{
		this(name);
		this.attach(parent);
	}

	public XMLNodeElement(String name)
	{
		this.name = name;

		this.attributes = new HashSet<XMLAttribute>();
		this.chidren = new ArrayList<XMLElement>();
	}

	@Override
	public String getTagName()
	{
		return this.name;
	}

	@Override
	public String getValue()
	{
		return this.getChildren().stream().map(child -> {
			return child.getValue();
		}).collect(Collectors.joining("\n"));
	}
	
	@Override
	public boolean isPCData()
	{
		return false;
	}

	@Override
	public Collection<XMLAttribute> getAttributes()
	{
		return this.attributes;
	}

	@Override
	public void addAttribute(XMLAttribute attribute)
	{
		this.attributes.add(attribute);
	}

	@Override
	public void removeAttribute(XMLAttribute attribute)
	{
		this.attributes.remove(attribute);
	}

	@Override
	public List<XMLElement> getChildren()
	{
		return this.chidren;
	}

	@Override
	public boolean appendChild(XMLElement element)
	{
		element.setParent(this);
		this.chidren.add(element);
		return true;
	}

	@Override
	public boolean removeChild(XMLElement element)
	{
		return this.chidren.remove(element);
	}

	@Override
	public String toString()
	{
		return this.getTagName() + "( " + this.getAttributes().stream().map(attr -> {
			return attr.toString();
		}).collect(Collectors.joining(", ")) + " ) { " + this.getChildren().stream().map(child -> {
			return child.toString();
		}).collect(Collectors.joining(", ")) + " }";
	}
}
