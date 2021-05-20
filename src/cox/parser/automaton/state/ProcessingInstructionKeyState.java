package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.pi.SimpleProcessingInstruction;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;
import cox.parser.exception.parsing.InvalidProcessingInstructionStartException;

public class ProcessingInstructionKeyState implements XMLAutomatonState
{
	private String key;
	
	public ProcessingInstructionKeyState()
	{
		this.key = "";
	}
	
	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(XMLCharaters.isIdentifier(c))
		{
			this.key += c;
		}
		else if(XMLCharaters.isWhitespace(c))
		{
			automaton.changeState(new InProcessingInstructionState(new SimpleProcessingInstruction(this.key)));
		}
		else
		{
			throw new InvalidProcessingInstructionStartException(c);
		}
	}

}
