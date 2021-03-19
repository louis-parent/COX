package cox.test;

import cox.model.attribute.SimpleXMLAttribute;
import cox.model.attribute.XMLAttribute;
import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.element.XMLElement;
import cox.model.element.XMLNodeElement;
import cox.model.element.XMLPCDataElement;
import jul.annotations.Test;
import jul.annotations.TestSet;
import jul.nla.Asserts;

@TestSet
public class TestXMLModel
{
	@Test
	public void testXMLAttribute()
	{
		XMLAttribute attr = new SimpleXMLAttribute("name", "value");
		Asserts.asserts(Asserts.that(attr.getName()).isEqualTo("name").and(Asserts.that(attr.getValue()).isEqualTo("value")));
	}

	@Test
	public void testXMLDocument()
	{
		XMLElement el = new XMLPCDataElement("test");
		XMLDocument doc = new SimpleXMLDocument(el);

		Asserts.asserts(Asserts.that(doc.getRoot()).isSameInstance(el));
	}

	@Test
	public void testXMLElementParent()
	{
		XMLElement parent = new XMLNodeElement("name");
		XMLElement child = new XMLPCDataElement("value", parent);

		Asserts.asserts(Asserts.that(child.getParent()).isSameInstance(parent));
	}

	@Test
	public void testXMLAttachElement()
	{
		XMLElement parent1 = new XMLNodeElement("root1");
		XMLElement parent2 = new XMLNodeElement("root2");
		XMLElement el = new XMLPCDataElement("value", parent1);

		el.attach(parent2);
		Asserts.asserts(Asserts.that(el.getParent()).isSameInstance(parent2).and(Asserts.that(parent2.getChildren()).hasItem(el)).and(Asserts.not(Asserts.that(parent1.getChildren()).hasItem(el))));
	}

	@Test
	public void testXMLElementValue()
	{
		XMLElement el = new XMLPCDataElement("value");
		Asserts.asserts(Asserts.that(el.getValue()).isEqualTo("value"));
	}

	@Test
	public void testXMLTagName()
	{
		XMLElement el = new XMLNodeElement("name");
		Asserts.asserts(Asserts.that(el.getTagName()).isEqualTo("name"));
	}

	@Test
	public void testXMLRoot()
	{
		XMLElement el = new XMLNodeElement("name");
		Asserts.asserts(Asserts.that(el.isRoot()).isEqualTo(true));
	}

	@Test
	public void testXMLLeaf()
	{
		XMLElement el = new XMLNodeElement("name");
		Asserts.asserts(Asserts.that(el.isLeaf()).isEqualTo(true));
	}

	@Test
	public void testXMLNoChild()
	{
		XMLElement el = new XMLNodeElement("name");
		Asserts.asserts(Asserts.that(el.getChildren()).isEmpty());
	}

	@Test
	public void testXMLGetChildren()
	{
		XMLElement parent = new XMLNodeElement("parent");
		XMLElement child1 = new XMLNodeElement("child1", parent);
		XMLElement child2 = new XMLNodeElement("child2", parent);
		Asserts.asserts(Asserts.that(parent.getChildren().size()).isEqualTo(2).and(Asserts.that(parent.getChildren()).hasItem(child1)).and(Asserts.that(parent.getChildren()).hasItem(child2)));
	}

	@Test
	public void testXMLAddAttributes()
	{
		XMLElement el = new XMLNodeElement("name");
		XMLAttribute attr = new SimpleXMLAttribute("attr");
		el.addAttribute(attr);
		Asserts.asserts(Asserts.that(el.getAttributes()).hasItem(attr));
	}

	@Test
	public void testXMLRemoveAttribute()
	{
		XMLElement el = new XMLNodeElement("name");
		XMLAttribute attr = new SimpleXMLAttribute("attr");
		el.addAttribute(attr);
		el.removeAttribute(attr);

		Asserts.asserts(Asserts.that(el.getAttributes()).isEmpty().and(Asserts.not(Asserts.that(el.getAttributes()).hasItem(attr))));
	}
}
