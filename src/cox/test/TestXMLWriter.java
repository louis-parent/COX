package cox.test;

import static jul.nla.Asserts.asserts;
import static jul.nla.Asserts.that;

import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.document.element.XMLElement;
import cox.model.document.element.XMLNodeElement;
import cox.model.document.element.XMLProcessingInstructionElement;
import cox.model.document.element.XMLTextElement;
import cox.model.utils.XMLIdentifier;
import cox.writer.XMLOutputOptions;
import cox.writer.XMLWriter;
import jul.annotations.Test;
import jul.annotations.TestSet;

@TestSet
public class TestXMLWriter
{
	@Test
	public void testWriteEmptyDocument()
	{
		XMLDocument document = XMLDocument.getEmpty();
		asserts(that(XMLWriter.write(document)).hasToString(""));
	}
	
	@Test
	public void testWriteRootOnlyDocument()
	{
		XMLElement root = XMLElement.getEmptyNode();
		new XMLNodeElement(root, "root");
		
		XMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document)).hasToString("<root></root>"));
	}
	
	@Test
	public void testWriteIndentedRootOnlyDocument()
	{
		XMLElement root = XMLElement.getEmptyNode();
		new XMLNodeElement(root, "root");
		
		XMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.INDENT)).hasToString("<root>\n</root>"));
	}
	
	@Test
	public void testWriteOneNotMifiedOrphanDocument()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		XMLElement root = new XMLNodeElement(docRoot, "root");
		new XMLNodeElement(root, "orphan");
		XMLDocument document = new SimpleXMLDocument(docRoot);
		
		asserts(that(XMLWriter.write(document)).hasToString("<root><orphan></orphan></root>"));
	}
	
	@Test
	public void testWriteOneMinifiedOrphanDocument()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		XMLElement root = new XMLNodeElement(docRoot, "root");
		new XMLNodeElement(root, "orphan");
		XMLDocument document = new SimpleXMLDocument(docRoot);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.SIMPLIFY_ORPHANS)).hasToString("<root><orphan/></root>"));
	}
	
	@Test
	public void testWriteTwoChildDocument()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		XMLElement root = new XMLNodeElement(docRoot, "root");
		
		XMLElement child1 = new XMLNodeElement(root, "child1");
		new XMLTextElement(child1, "Content 1");
		
		XMLElement child2 = new XMLNodeElement(root, "child2");
		new XMLTextElement(child2, "Content 2");
		
		XMLDocument document = new SimpleXMLDocument(docRoot);

		asserts(that(XMLWriter.write(document)).hasToString("<root><child1>Content 1</child1><child2>Content 2</child2></root>"));
	}
	
	@Test
	public void testWriteTrimedChildDocument()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		XMLElement root = new XMLNodeElement(docRoot, "root");
		
		XMLElement child1 = new XMLNodeElement(root, "child");
		child1.appendChild(new XMLTextElement("\n\t\tTrimmed Content 1\t\n\n\n"));
		
		XMLDocument document = new SimpleXMLDocument(docRoot);

		asserts(that(XMLWriter.write(document, XMLOutputOptions.TRIM_CONTENT)).hasToString("<root><child>Trimmed Content 1</child></root>"));
	}
	
	@Test
	public void testWriteTwoDepthDocument()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		XMLElement root = new XMLNodeElement(docRoot, "root");
		
		XMLElement child1 = new XMLNodeElement(root, "child");
		child1.appendChild(new XMLNodeElement("subchild1"));
		child1.appendChild(new XMLNodeElement("subchild2"));
		
		XMLDocument document = new SimpleXMLDocument(docRoot);

		asserts(that(XMLWriter.write(document, XMLOutputOptions.INDENT, XMLOutputOptions.SIMPLIFY_ORPHANS)).hasToString("<root>\n\t<child>\n\t\t<subchild1/>\n\t\t<subchild2/>\n\t</child>\n</root>"));
	}
	
	@Test
	public void testWriteDocumentWithDefaultXMLDeclaration()
	{
		XMLElement root = XMLElement.getEmptyNode();
		new XMLNodeElement(root, "root");
		SimpleXMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION)).hasToString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root></root>"));
	}
	
	@Test
	public void testWriteDocumentWithDefaultIndentedXMLDeclaration()
	{
		XMLElement root = XMLElement.getEmptyNode();
		new XMLNodeElement(root, "root");
		SimpleXMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION, XMLOutputOptions.INDENT)).hasToString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<root>\n</root>"));
	}
	
	@Test
	public void testWriteDocumentWithCustomXMLDeclaration()
	{
		XMLElement root = XMLElement.getEmptyNode();
		new XMLNodeElement(root, "root");
		SimpleXMLDocument document = new SimpleXMLDocument("1.1", "UTF-16", true, root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION)).hasToString("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?><root></root>"));
	}
	
	@Test
	public void testWriteDocumentWithCustomXMLDeclarationAndCustomProcessingInstruction()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		new XMLProcessingInstructionElement(docRoot, new XMLIdentifier("custom-key"), "custom-value");
		new XMLNodeElement(docRoot, "root");
		
		SimpleXMLDocument document = new SimpleXMLDocument("1.1", "UTF-16", true, docRoot);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION)).hasToString("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?><?custom-key custom-value?><root></root>"));
	}
	@Test
	public void testWriteDocumentWithIndentedCustomXMLDeclarationAndCustomProcessingInstruction()
	{
		XMLElement docRoot = XMLElement.getEmptyNode();
		new XMLProcessingInstructionElement(docRoot, new XMLIdentifier("custom-key"), "custom-value");
		new XMLNodeElement(docRoot, "root");
		SimpleXMLDocument document = new SimpleXMLDocument("1.1", "UTF-16", true, docRoot);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION, XMLOutputOptions.INDENT)).hasToString("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?>\n<?custom-key custom-value?>\n<root>\n</root>"));
	}
}
