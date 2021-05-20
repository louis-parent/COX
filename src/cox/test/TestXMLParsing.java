package cox.test;

import static jul.nla.Asserts.asserts;
import static jul.nla.Asserts.not;
import static jul.nla.Asserts.that;

import cox.model.attribute.SimpleXMLAttribute;
import cox.model.document.XMLDocument;
import cox.model.element.XMLElement;
import cox.model.element.XMLNodeElement;
import cox.parser.COXParser;
import cox.parser.exception.COXParserException;
import jul.annotations.Test;
import jul.annotations.TestSet;

@TestSet
public class TestXMLParsing
{
	@Test
	public void testEmptyDocument() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("");
		asserts(that(doc.isEmpty()));
	}

	@Test
	public void testNotEmptyDocument() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<root></root>");
		asserts(not(that(doc.isEmpty())));
	}
	
	@Test
	public void testRootOnlyDocument() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<root></root>");
		asserts(that(doc.getRoot().descendingEquals(new XMLNodeElement("root"))).isEqualTo(true));
	}

	@Test
	public void testRootAndChildrenDocument() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<root><child1></child1><child2></child2></root>");
		XMLElement root = new XMLNodeElement("root");
		new XMLNodeElement("child1", root);
		new XMLNodeElement("child2", root);

		asserts(that(doc.getRoot().descendingEquals(root)).isEqualTo(true));
	}

	@Test
	public void testRootAndAttributeDocument() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<root test=\"test\"></root>");
		XMLElement root = new XMLNodeElement("root");
		root.addAttribute(new SimpleXMLAttribute("test", "test"));

		asserts(that(doc.getRoot()).isEqualTo(root));
	}

	@Test
	public void testRootAndAttributesDocument() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<root test1=\"test1\" test2=\"test2\"></root>");
		XMLElement root = new XMLNodeElement("root");
		root.addAttribute(new SimpleXMLAttribute("test1", "test1"));
		root.addAttribute(new SimpleXMLAttribute("test2", "test2"));

		asserts(that(doc.getRoot()).isEqualTo(root));
	}
	
	@Test
	public void testMinimalXMLDeclaration() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<?xml version=\"1.1\"?><root></root>");
		asserts(that(doc.getVersion()).isEqualTo("1.1").and(that(doc.getEncoding()).isEqualTo("UTF-8")).and(that(doc.isStandalone()).isEqualTo(false)));
	}
	
	@Test
	public void testMinimalXMLDeclarationWithEncoding() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<?xml version=\"1.1\" encoding=\"UTF-16\"?><root></root>");
		asserts(that(doc.getVersion()).isEqualTo("1.1").and(that(doc.getEncoding()).isEqualTo("UTF-16")).and(that(doc.isStandalone()).isEqualTo(false)));
	}
	
	@Test
	public void testMinimalXMLDeclarationWithEncodingAndStandalone() throws COXParserException
	{
		XMLDocument doc = COXParser.parse("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?><root></root>");
		asserts(that(doc.getVersion()).isEqualTo("1.1").and(that(doc.getEncoding()).isEqualTo("UTF-16")).and(that(doc.isStandalone()).isEqualTo(true)));
	}
}
