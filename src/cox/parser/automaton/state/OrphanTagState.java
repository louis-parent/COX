package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLNodeElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.parsing.MalformedIdentifierException;
import cox.parser.exception.parsing.MissingOrphanTagEndException;

public class OrphanTagState implements XMLAutomatonState, IdentifierParser
{
	private XMLNodeElement element;

	public OrphanTagState(String name) throws MalformedIdentifierException
	{
		this.element = new XMLNodeElement(this.parseIdentifier(name));
	}

	public OrphanTagState(XMLNodeElement element)
	{
		this.element = element;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(c == XMLCharaters.CLOSING_TAG.getChar())
		{
			automaton.openCloseElement(this.element);
			automaton.changeState(new ContentState());
		}
		else
		{
			throw new MissingOrphanTagEndException();
		}
	}

}
