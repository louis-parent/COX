package cox.parser.exception.parsing;

import cox.model.XMLCharaters;
import cox.parser.exception.COXAutomatonException;

public class MalformedEndCloseTagException extends COXAutomatonException
{
	private static final long serialVersionUID = -2179041116234286759L;

	public MalformedEndCloseTagException(char c)
	{
		super("Closing tag must end with the identifier followed by" + XMLCharaters.CLOSING_TAG_MARKER + ", '" + c + "' is an invalid char");
	}
}
