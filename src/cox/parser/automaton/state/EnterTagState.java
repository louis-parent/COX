package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.InvalidTagStartException;

public class EnterTagState implements XMLAutomatonState
{
	@Override
	public void read(XMLAutomaton automaton, char c) throws InvalidTagStartException
	{
		if(XMLCharaters.isIdentifier(c))
		{
			automaton.changeState(new OpenTagNameState(c));
		}
		else if(c == XMLCharaters.CLOSING_TAG_MARKER.getChar())
		{
			automaton.changeState(new CloseTagOpenedState());
		}
		else
		{
			throw new InvalidTagStartException();
		}
	}
}