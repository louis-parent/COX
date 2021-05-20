package cox.test;

import static jul.nla.Asserts.asserts;
import static jul.nla.Asserts.not;
import static jul.nla.Asserts.that;

import java.util.Arrays;

import cox.model.attribute.SimpleXMLAttribute;
import cox.model.attribute.XMLAttribute;
import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.element.XMLElement;
import cox.model.element.XMLNodeElement;
import cox.model.element.XMLPCDataElement;
import cox.model.pi.ProcessingInstruction;
import cox.model.pi.SimpleProcessingInstruction;
import jul.annotations.Test;
import jul.annotations.TestSet;

@TestSet
public class TestXMLModel
{
	@Test
	public void testXMLAttribute()
	{
		XMLAttribute attr = new SimpleXMLAttribute("name", "value");
		asserts(that(attr.getName()).isEqualTo("name").and(that(attr.getValue()).isEqualTo("value")));
	}

	@Test
	public void testXMLDocument()
	{
		XMLElement el = new XMLPCDataElement("test");
		XMLDocument doc = new SimpleXMLDocument(el);

		asserts(that(doc.getRoot()).isSameInstance(el));
	}

	@Test
	public void testXMLElementParent()
	{
		XMLElement parent = new XMLNodeElement("name");
		XMLElement child = new XMLPCDataElement("value", parent);

		asserts(that(child.getParent()).isSameInstance(parent));
	}

	@Test
	public void testXMLAttachElement()
	{
		XMLElement parent1 = new XMLNodeElement("root1");
		XMLElement parent2 = new XMLNodeElement("root2");
		XMLElement el = new XMLPCDataElement("value", parent1);

		el.attach(parent2);
		asserts(that(el.getParent()).isSameInstance(parent2).and(that(parent2.getChildren()).hasItem(el)).and(not(that(parent1.getChildren()).hasItem(el))));
	}

	@Test
	public void testXMLElementValue()
	{
		XMLElement el = new XMLPCDataElement("value");
		asserts(that(el.getValue()).isEqualTo("value"));
	}

	@Test
	public void testXMLTagName()
	{
		XMLElement el = new XMLNodeElement("name");
		asserts(that(el.getTagName()).isEqualTo("name"));
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
		XMLElement child1 = new XMLNodeElement("child1", parent);
		XMLElement child2 = new XMLNodeElement("child2", parent);
		asserts(that(parent.getChildren().size()).isEqualTo(2).and(that(parent.getChildren()).hasItem(child1)).and(that(parent.getChildren()).hasItem(child2)));
	}

	@Test
	public void testXMLAddAttributes()
	{
		XMLElement el = new XMLNodeElement("name");
		XMLAttribute attr = new SimpleXMLAttribute("attr");
		el.addAttribute(attr);
		asserts(that(el.getAttributes()).hasItem(attr));
	}

	@Test
	public void testXMLRemoveAttribute()
	{
		XMLElement el = new XMLNodeElement("name");
		XMLAttribute attr = new SimpleXMLAttribute("attr");
		el.addAttribute(attr);
		el.removeAttribute(attr);

		asserts(that(el.getAttributes()).isEmpty().and(not(that(el.getAttributes()).hasItem(attr))));
	}
	
	@Test
	public void testEmptyXMLProcessingInstruction()
	{
		ProcessingInstruction pi = new SimpleProcessingInstruction("key");
		asserts(that(pi.getKey()).isEqualTo("key").and(that(pi.getValues()).isEmpty()));
	}
	
	@Test
	public void testXMLProcessingInstructionWithValues()
	{
		ProcessingInstruction pi = new SimpleProcessingInstruction("key", Arrays.asList("value1", "value2"));
		asserts(that(pi.getKey()).isEqualTo("key").and(that(pi.getValues()).hasItem("value1")).and(that(pi.getValues()).hasItem("value2")));
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
	
	@Test
	public void testXMLDocumentWithProcessingInstructions()
	{
		ProcessingInstruction pi1 = new SimpleProcessingInstruction("key1");
		ProcessingInstruction pi2 = new SimpleProcessingInstruction("key2");
		XMLDocument doc = new SimpleXMLDocument("1.0", "UTF-8", false, null, Arrays.asList(pi1, pi2));
		
		asserts(that(doc.getProcessingInstructions()).hasItem(pi1).and(that(doc.getProcessingInstructions()).hasItem(pi2)));
	}
}
