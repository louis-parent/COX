package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLProcessingInstructionElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.parsing.InvalidProcessingInstructionEndException;

public class ProcessingInstructionClosingState implements XMLAutomatonState
{
	private XMLProcessingInstructionElement pi;
	
	public ProcessingInstructionClosingState(XMLProcessingInstructionElement pi)
	{
		this.pi = pi;
	}
	
	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(c == XMLCharaters.CLOSING_TAG.getChar())
		{
			automaton.putProcessingInstruction(this.pi);
			automaton.changeState(new ContentState());
		}
		else
		{
			throw new InvalidProcessingInstructionEndException(c);
		}
	}

}
