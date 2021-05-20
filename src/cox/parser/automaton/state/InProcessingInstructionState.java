package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.pi.SimpleProcessingInstruction;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.parsing.InvalidCharInProcessingInstruction;

public class InProcessingInstructionState implements XMLAutomatonState
{
	private SimpleProcessingInstruction pi;
	
	public InProcessingInstructionState(SimpleProcessingInstruction pi)
	{
		this.pi = pi;
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(XMLCharaters.isIdentifier(c))
		{
			automaton.changeState(new ProcessingInstructionValueState(this.pi, c));
		}
		else if(c == XMLCharaters.PROCESSING_INSTRUCTION_DELIMITER.getChar())
		{
			automaton.changeState(new ProcessingInstructionClosingState(this.pi));
		}
		else if(XMLCharaters.isWhitespace(c))
		{
			throw new InvalidCharInProcessingInstruction(c);
		}
	}
}
