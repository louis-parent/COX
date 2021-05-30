package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLTextElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.parsing.InvalidContentOutsideTagException;

public class ContentState implements XMLAutomatonState
{
	private String content = "";

	@Override
	public void read(XMLAutomaton automaton, char c) throws InvalidContentOutsideTagException
	{
		if(c == XMLCharaters.OPENING_TAG.getChar())
		{
			if(this.hasData())
			{
				automaton.putText(new XMLTextElement(this.content));
			}

			automaton.changeState(new EnterTagState());
		}
		else if(this.hasData() || !XMLCharaters.isWhitespace(c))
		{
			this.content += c;
		}
	}

	public boolean hasData()
	{
		return this.content.length() > 0;
	}
}
