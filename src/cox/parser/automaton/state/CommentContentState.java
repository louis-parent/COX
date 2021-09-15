package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;

public class CommentContentState implements XMLAutomatonState
{
	private String content = "";
	
	public CommentContentState()
	{
		this("");
	}
	
	public CommentContentState(String content)
	{
		this.content = content;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(c == XMLCharaters.COMMENT_CONTENT_DELIMITER.getChar())
		{
			automaton.changeState(new CommentClosingState(content));
		}
		else
		{
			content += c;
		}
	}

}
