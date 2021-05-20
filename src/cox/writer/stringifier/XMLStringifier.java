package cox.writer.stringifier;

import java.util.Collection;

import cox.model.document.XMLDocument;
import cox.model.pi.ProcessingInstruction;
import cox.writer.XMLOutputOptions;

public class XMLStringifier implements ParametrableXMLWriter
{
	private XMLDocument document;
	private Collection<XMLOutputOptions> options;
	
	public XMLStringifier(XMLDocument document, Collection<XMLOutputOptions> options)
	{
		this.document = document;
		this.options = options;
	}
	
	@Override
	public Collection<XMLOutputOptions> getOptions()
	{
		return this.options;
	}
	
	@Override
	public String toString()
	{
		if(this.document.isEmpty())
		{
			return "";
		}
		else
		{
			String declaration = "";
			
			if(this.hasToWriteDeclaration())
			{
				declaration += new XMLProcessingInstructionStringifier(this.document, this.options).toString();
				
				for(ProcessingInstruction pi : this.document.getProcessingInstructions())
				{
					declaration += new XMLProcessingInstructionStringifier(pi, this.options).toString();
				}
			}
			
			return declaration + new XMLElementStringifier(document.getRoot(), this.options).toString();
		}
	}
}
