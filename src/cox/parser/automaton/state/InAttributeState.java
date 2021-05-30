package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLNodeElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.MissingAttributeKeyValueSeparator;

public class InAttributeState implements XMLAutomatonState
{
	private XMLNodeElement element;
	private String name;

	public InAttributeState(XMLNodeElement element, String name)
	{
		this.element = element;
		this.name = name;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws MissingAttributeKeyValueSeparator
	{
		if(c == XMLCharaters.ATTRIBUTE_KEY_VALUE_SEPARATOR.getChar())
		{
			automaton.changeState(new AttributeTransitionState(this.element, this.name));
		}
		else if(!XMLCharaters.isWhitespace(c))
		{
			throw new MissingAttributeKeyValueSeparator();
		}
	}

}
