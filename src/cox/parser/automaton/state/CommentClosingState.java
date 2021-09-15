package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLCommentElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.parsing.InvalidCommentEndException;

public class CommentClosingState implements XMLAutomatonState
{
	private boolean secondDelimiterFound = false;
	private String content;
	
	public CommentClosingState(String content)
	{
		this.content = content;
	}
	
	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(!this.secondDelimiterFound)
		{
			if(c == XMLCharaters.COMMENT_CONTENT_DELIMITER.getChar())
			{
				secondDelimiterFound = true;
			}
			else
			{				
				automaton.changeState(new CommentContentState(content + XMLCharaters.COMMENT_CONTENT_DELIMITER.getChar()));
			}
		}
		else
		{
			if(c == XMLCharaters.CLOSING_TAG.getChar())
			{
				automaton.openCloseElement(new XMLCommentElement(content));
				automaton.changeState(new ContentState());
			}
			else
			{
				throw new InvalidCommentEndException(c);
			}
		}
	}

}
