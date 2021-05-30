package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLNodeElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.DuplicateAttributeNameException;

public class AttributeValueState implements XMLAutomatonState
{
	private XMLNodeElement element;
	private String name;
	private String value = "";

	public AttributeValueState(XMLNodeElement element, String name)
	{
		this.element = element;
		this.name = name;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws DuplicateAttributeNameException
	{
		if(c == XMLCharaters.ATTRIBUTE_DELIMITER.getChar())
		{
			boolean added = this.element.addAttribute(this.name, this.value);
			
			if(added)
			{
				automaton.changeState(new InOpenTagState(this.element));
			}
			else
			{
				throw new DuplicateAttributeNameException(this.name);
			}
		}
		else
		{
			this.value += c;
		}
	}
}
