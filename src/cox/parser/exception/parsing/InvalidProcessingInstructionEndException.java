package cox.parser.exception.parsing;

import cox.parser.exception.COXAutomatonException;

public class InvalidProcessingInstructionEndException extends COXAutomatonException
{
	private static final long serialVersionUID = -5894023563046538087L;

	public InvalidProcessingInstructionEndException(char c)
	{
		super("Processing instruction must end with '?>' instead of '" + c + "'");
	}
}
