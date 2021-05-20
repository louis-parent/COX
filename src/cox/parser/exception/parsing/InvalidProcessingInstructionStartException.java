package cox.parser.exception.parsing;

import cox.parser.exception.COXAutomatonException;

public class InvalidProcessingInstructionStartException extends COXAutomatonException
{
	private static final long serialVersionUID = 5348259703831382137L;

	public InvalidProcessingInstructionStartException(char c)
	{
		super("Processing Instruction must start with identifier after '<?' instead of '" + c + "'");
	}
}
