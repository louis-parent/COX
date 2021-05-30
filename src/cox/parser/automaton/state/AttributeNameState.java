package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLNodeElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.InvalidAttributeNameException;

public class AttributeNameState implements XMLAutomatonState
{
	private XMLNodeElement openTag;
	private String name;

	public AttributeNameState(XMLNodeElement openTag, char firstChar)
	{
		this.openTag = openTag;
		this.name = String.valueOf(firstChar);
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws InvalidAttributeNameException
	{
		if(XMLCharaters.isIdentifier(c))
		{
			this.name += c;
		}
		else if(c == XMLCharaters.ATTRIBUTE_KEY_VALUE_SEPARATOR.getChar())
		{
			automaton.changeState(new AttributeTransitionState(this.openTag, this.name));
		}
		else if(XMLCharaters.isWhitespace(c))
		{
			automaton.changeState(new InAttributeState(this.openTag, this.name));
		}
		else
		{
			throw new InvalidAttributeNameException();
		}
	}

}
