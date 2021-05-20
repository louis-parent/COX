package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.pi.SimpleProcessingInstruction;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;

public class ProcessingInstructionValueState implements XMLAutomatonState
{
	private SimpleProcessingInstruction pi;
	private String value;
	
	public ProcessingInstructionValueState(SimpleProcessingInstruction pi, char firstChar)
	{
		this.pi = pi;
		this.value = String.valueOf(firstChar);
	}
	
	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(XMLCharaters.isWhitespace(c))
		{
			this.pi.addValue(this.value);
			automaton.changeState(new InProcessingInstructionState(this.pi));
		}
		else if(c == XMLCharaters.PROCESSING_INSTRUCTION_DELIMITER.getChar())
		{
			this.pi.addValue(this.value);
			automaton.changeState(new ProcessingInstructionClosingState(this.pi));
		}
		else
		{
			this.value += c;
		}
	}
}
