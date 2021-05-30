package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLNodeElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.MalformedAttributeValueException;

public class AttributeTransitionState implements XMLAutomatonState
{
	private XMLNodeElement element;
	private String name;

	public AttributeTransitionState(XMLNodeElement element, String name)
	{
		this.element = element;
		this.name = name;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws MalformedAttributeValueException
	{
		if(c == XMLCharaters.ATTRIBUTE_DELIMITER.getChar())
		{
			automaton.changeState(new AttributeValueState(this.element, this.name));
		}
		else if(!XMLCharaters.isWhitespace(c))
		{
			throw new MalformedAttributeValueException();
		}
	}
}
