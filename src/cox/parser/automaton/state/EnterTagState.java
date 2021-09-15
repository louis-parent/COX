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
		else if(c == XMLCharaters.PROCESSING_INSTRUCTION_DELIMITER.getChar())
		{
			automaton.changeState(new ProcessingInstructionKeyState());
		}
		else if(c == XMLCharaters.COMMENT_DELIMITER.getChar())
		{
			automaton.changeState(new CommentOpeningState());
		}
		else
		{
			throw new InvalidTagStartException();
		}
	}
}
