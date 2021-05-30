package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLNodeElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.InvalidCharInTagException;

public class InOpenTagState implements XMLAutomatonState
{
	private XMLNodeElement element;

	public InOpenTagState(XMLNodeElement element)
	{
		this.element = element;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws InvalidCharInTagException
	{
		if(XMLCharaters.isIdentifier(c))
		{
			automaton.changeState(new AttributeNameState(this.element, c));
		}
		else if(c == XMLCharaters.CLOSING_TAG.getChar())
		{
			automaton.openElement(this.element);
			automaton.changeState(new ContentState());
		}
		else if(c == XMLCharaters.CLOSING_TAG_MARKER.getChar())
		{
			automaton.changeState(new OrphanTagState(this.element));
		}
		else if(!XMLCharaters.isWhitespace(c))
		{
			throw new InvalidCharInTagException();
		}
	}

}
