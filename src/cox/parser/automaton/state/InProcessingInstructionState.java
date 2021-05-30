package cox.parser.automaton.state;

import cox.model.XMLCharaters;
import cox.model.document.element.XMLProcessingInstructionElement;
import cox.parser.automaton.XMLAutomaton;
import cox.parser.exception.COXAutomatonException;

public class InProcessingInstructionState implements XMLAutomatonState
{
	private XMLProcessingInstructionElement pi;
	private String content;
	
	public InProcessingInstructionState(XMLProcessingInstructionElement pi)
	{
		this.pi = pi;
		this.content = "";
	}

	@Override
	public void read(XMLAutomaton automaton, char c) throws COXAutomatonException
	{
		if(c == XMLCharaters.PROCESSING_INSTRUCTION_DELIMITER.getChar())
		{
			this.pi.setValue(this.content);
			automaton.changeState(new ProcessingInstructionClosingState(this.pi));
		}
		else
		{
			this.content += c;
		}
	}
}
