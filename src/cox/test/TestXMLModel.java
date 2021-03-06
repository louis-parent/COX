package cox.test;

import static jul.nla.Asserts.asserts;
import static jul.nla.Asserts.not;
import static jul.nla.Asserts.that;

import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.document.element.XMLElement;
import cox.model.document.element.XMLNodeElement;
import cox.model.document.element.XMLTextElement;
import jul.annotations.Test;
import jul.annotations.TestSet;

@TestSet
public class TestXMLModel
{
	@Test
	public void testXMLDocument()
	{
		XMLElement el = new XMLTextElement("test");
		XMLDocument doc = new SimpleXMLDocument(el);

		asserts(that(doc.getRoot()).isSameInstance(el));
	}

	@Test
	public void testXMLElementParent()
	{
		XMLElement parent = new XMLNodeElement("name");
		XMLElement child = new XMLTextElement(parent, "value");

		asserts(that(child.getParent()).isSameInstance(parent));
	}

	@Test
	public void testXMLAttachElement()
	{
		XMLElement parent1 = new XMLNodeElement("root1");
		XMLElement parent2 = new XMLNodeElement("root2");
		XMLElement el = new XMLTextElement(parent1, "value");

		el.attachTo(parent2);
		asserts(that(el.getParent()).isSameInstance(parent2)
				.and(that(parent2.getChildren()).hasItem(el))
				.and(not(that(parent1.getChildren()).hasItem(el))));
	}

	@Test
	public void testXMLElementValue()
	{
		XMLElement el = new XMLTextElement("value");
		asserts(that(el.getValue()).isEqualTo("value"));
	}

	@Test
	public void testXMLTagName()
	{
		XMLElement el = new XMLNodeElement("name");
		asserts(that(el.getIdentifier()).hasToString("name"));
	}

	@Test
	public void testXMLRoot()
	{
		XMLElement el = new XMLNodeElement("name");
		asserts(that(el.isRoot()).isEqualTo(true));
	}

	@Test
	public void testXMLLeaf()
	{
		XMLElement el = new XMLNodeElement("name");
		asserts(that(el.isLeaf()).isEqualTo(true));
	}

	@Test
	public void testXMLNoChild()
	{
		XMLElement el = new XMLNodeElement("name");
		asserts(that(el.getChildren()).isEmpty());
	}

	@Test
	public void testXMLGetChildren()
	{
		XMLElement parent = new XMLNodeElement("parent");
		XMLElement child1 = new XMLNodeElement(parent, "child1");
		XMLElement child2 = new XMLNodeElement(parent, "child2");
		asserts(that(parent.getChildren().size()).isEqualTo(2).and(that(parent.getChildren()).hasItem(child1)).and(that(parent.getChildren()).hasItem(child2)));
	}

	@Test
	public void testXMLAddAttributes()
	{
		XMLElement el = new XMLNodeElement("name");
		el.addAttribute("attr", "attr");
		asserts(that(el.getAttributes()).hasEntry("attr", "attr"));
	}

	@Test
	public void testXMLRemoveAttribute()
	{
		XMLElement el = new XMLNodeElement("name");
		el.addAttribute("attr", "attr");
		el.removeAttribute("attr");

		asserts(not(that(el.getAttributes()).hasEntry("attr", "attr")));
	}
	
	@Test
	public void testDefaultDocumentProperties()
	{
		XMLDocument doc = new SimpleXMLDocument(null);
		asserts(that(doc.getVersion()).isEqualTo("1.0").and(that(doc.getEncoding()).isEqualTo("UTF-8").and(that(doc.isStandalone()).isEqualTo(false))));
	}
	
	@Test
	public void testFilledDocumentProperties()
	{
		XMLDocument doc = new SimpleXMLDocument("1.1", "UTF-16", true, null);
		asserts(that(doc.getVersion()).isEqualTo("1.1").and(that(doc.getEncoding()).isEqualTo("UTF-16").and(that(doc.isStandalone()).isEqualTo(true))));
	}
}
