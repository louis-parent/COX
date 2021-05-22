package cox.test;

import static jul.nla.Asserts.asserts;
import static jul.nla.Asserts.that;

import java.util.Arrays;

import cox.model.document.SimpleXMLDocument;
import cox.model.document.XMLDocument;
import cox.model.element.XMLElement;
import cox.model.element.XMLNodeElement;
import cox.model.element.XMLPCDataElement;
import cox.model.pi.XMLSimpleProcessingInstruction;
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
		XMLElement root = new XMLNodeElement("root");
		XMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document)).hasToString("<root></root>"));
	}
	
	@Test
	public void testWriteIndentedRootOnlyDocument()
	{
		XMLElement root = new XMLNodeElement("root");
		XMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.INDENT)).hasToString("<root>\n</root>"));
	}
	
	@Test
	public void testWriteOneNotMifiedOrphanDocument()
	{
		XMLElement root = new XMLNodeElement("root");
		XMLElement orphan = new XMLNodeElement("orphan");
		root.appendChild(orphan);
		XMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document)).hasToString("<root><orphan></orphan></root>"));
	}
	
	@Test
	public void testWriteOneMinifiedOrphanDocument()
	{
		XMLElement root = new XMLNodeElement("root");
		XMLElement orphan = new XMLNodeElement("orphan");
		root.appendChild(orphan);
		XMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.SIMPLIFY_ORPHANS)).hasToString("<root><orphan/></root>"));
	}
	
	@Test
	public void testWriteTwoChildDocument()
	{
		XMLElement root = new XMLNodeElement("root");
		
		XMLElement child1 = new XMLNodeElement("child1");
		child1.appendChild(new XMLPCDataElement("Content 1"));
		
		XMLElement child2 = new XMLNodeElement("child2");
		child2.appendChild(new XMLPCDataElement("Content 2"));
		
		root.appendChild(child1);
		root.appendChild(child2);
		XMLDocument document = new SimpleXMLDocument(root);

		asserts(that(XMLWriter.write(document)).hasToString("<root><child1>Content 1</child1><child2>Content 2</child2></root>"));
	}
	
	@Test
	public void testWriteTrimedChildDocument()
	{
		XMLElement root = new XMLNodeElement("root");
		
		XMLElement child1 = new XMLNodeElement("child");
		child1.appendChild(new XMLPCDataElement("\n\t\tTrimmed Content 1\t\n\n\n"));
		
		root.appendChild(child1);
		XMLDocument document = new SimpleXMLDocument(root);

		asserts(that(XMLWriter.write(document)).hasToString("<root><child>Trimmed Content 1</child></root>"));
	}
	
	@Test
	public void testWriteTwoDepthDocument()
	{
		XMLElement root = new XMLNodeElement("root");
		
		XMLElement child1 = new XMLNodeElement("child");
		child1.appendChild(new XMLNodeElement("subchild1"));
		child1.appendChild(new XMLNodeElement("subchild2"));
		
		root.appendChild(child1);
		XMLDocument document = new SimpleXMLDocument(root);

		asserts(that(XMLWriter.write(document, XMLOutputOptions.INDENT, XMLOutputOptions.SIMPLIFY_ORPHANS)).hasToString("<root>\n\t<child>\n\t\t<subchild1/>\n\t\t<subchild2/>\n\t</child>\n</root>"));
	}
	
	@Test
	public void testWriteDocumentWithDefaultXMLDeclaration()
	{
		XMLElement root = new XMLNodeElement("root");
		SimpleXMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION)).hasToString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><root></root>"));
	}
	
	@Test
	public void testWriteDocumentWithDefaultIndentedXMLDeclaration()
	{
		XMLElement root = new XMLNodeElement("root");
		SimpleXMLDocument document = new SimpleXMLDocument(root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION, XMLOutputOptions.INDENT)).hasToString("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?>\n<root>\n</root>"));
	}
	
	@Test
	public void testWriteDocumentWithCustomXMLDeclaration()
	{
		XMLElement root = new XMLNodeElement("root");
		SimpleXMLDocument document = new SimpleXMLDocument("1.1", "UTF-16", true, root);
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION)).hasToString("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?><root></root>"));
	}
	
	@Test
	public void testWriteDocumentWithCustomXMLDeclarationAndCustomProcessingInstruction()
	{
		XMLElement root = new XMLNodeElement("root");
		SimpleXMLDocument document = new SimpleXMLDocument("1.1", "UTF-16", true, root, Arrays.asList(new XMLSimpleProcessingInstruction("custom-key", Arrays.asList("custom-value"))));
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION)).hasToString("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?><?custom-key custom-value?><root></root>"));
	}
	@Test
	public void testWriteDocumentWithIndentedCustomXMLDeclarationAndCustomProcessingInstruction()
	{
		XMLElement root = new XMLNodeElement("root");
		SimpleXMLDocument document = new SimpleXMLDocument("1.1", "UTF-16", true, root, Arrays.asList(new XMLSimpleProcessingInstruction("custom-key", Arrays.asList("custom-value"))));
		
		asserts(that(XMLWriter.write(document, XMLOutputOptions.WRITE_DECLARATION, XMLOutputOptions.INDENT)).hasToString("<?xml version=\"1.1\" encoding=\"UTF-16\" standalone=\"yes\"?>\n<?custom-key custom-value?>\n<root>\n</root>"));
	}
}
