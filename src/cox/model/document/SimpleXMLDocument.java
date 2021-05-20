package cox.model.document;

import java.util.Collection;
import java.util.Collections;

import cox.model.element.XMLElement;
import cox.model.pi.ProcessingInstruction;

public class SimpleXMLDocument implements XMLDocument
{
	private String version;
	private String encoding;
	private boolean standalone;
	
	private Collection<ProcessingInstruction> processingInstructions;
	
	private XMLElement root;

	public SimpleXMLDocument(XMLElement root)
	{
		this(XMLDocument.DEFAULT_VERSION, XMLDocument.DEFAULT_ENCODING, XMLDocument.DEFAULT_STANDALONE, root);
	}
	
	public SimpleXMLDocument(String version, String encoding, boolean standalone, XMLElement root)
	{
		this(version, encoding, standalone, root, Collections.emptySet());
	}
	
	public SimpleXMLDocument(String version, String encoding, boolean standalone, XMLElement root, Collection<ProcessingInstruction> processingInstructions)
	{
		this.version = version;
		this.encoding = encoding;
		this.standalone = standalone;
		
		this.root = root;
		
		this.processingInstructions = processingInstructions;
	}

	@Override
	public String getVersion()
	{
		return this.version;
	}
	
	@Override
	public String getEncoding()
	{
		return this.encoding;
	}
	
	@Override
	public boolean isStandalone()
	{
		return this.standalone;
	}
	
	public void addProcessingInstructions(Collection<ProcessingInstruction> pis)
	{
		this.processingInstructions.addAll(pis);
	}
	
	public void addProcessingInstruction(ProcessingInstruction pi)
	{
		this.processingInstructions.add(pi);
	}
	
	@Override
	public Collection<ProcessingInstruction> getProcessingInstructions()
	{
		return this.processingInstructions;
	}

	@Override
	public XMLElement getRoot()
	{
		return this.root;
	}

	@Override
	public boolean equals(Object obj)
	{
		if(obj instanceof XMLDocument)
		{
			return this.equals((XMLDocument) obj);
		}
		else
		{
			return false;
		}
	}

	@Override
	public String toString()
	{
		if(this.isEmpty())
		{
			return "Empty Document";
		}
		else
		{
			return this.getRoot().toString();
		}
	}
}
