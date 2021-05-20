package cox.parser.exception.parsing;

import cox.parser.exception.COXAutomatonException;

public class InvalidCharInProcessingInstruction extends COXAutomatonException
{
	private static final long serialVersionUID = -1837933921041265444L;

	public InvalidCharInProcessingInstruction(char c)
	{
		super("Processing Instruction must be contained between '<?' and '?>', starts with a key and followed by a list of value separated by space. '" + c + "' is not a valid char");
	}
}
