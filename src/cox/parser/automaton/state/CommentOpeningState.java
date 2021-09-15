package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.parsing.InvalidCommentStartException;

public class CommentOpeningState implements XMLAutomatonState
{
	private static final int LOOKING_FOR = 2;

	private int found = 0;
	
	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(c == XMLCharaters.COMMENT_CONTENT_DELIMITER.getChar())
		{
			found++;
		}
		else
		{
			throw new InvalidCommentStartException(c);
		}
		
		if(this.found == CommentOpeningState.LOOKING_FOR)
		{
			automaton.changeState(new CommentContentState());
		}
	}

}
