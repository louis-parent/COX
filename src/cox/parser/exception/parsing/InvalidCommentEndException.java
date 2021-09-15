package cox.parser.exception.parsing;

import cox.model.XMLCharaters;
import cox.parser.exception.COXAutomatonException;

public class InvalidCommentEndException extends COXAutomatonException
{
	private static final long serialVersionUID = -4902304561183344085L;

	public InvalidCommentEndException(char c)
	{
		super("Comment markup must end with '" + XMLCharaters.COMMENT_CONTENT_DELIMITER + XMLCharaters.COMMENT_CONTENT_DELIMITER + XMLCharaters.CLOSING_TAG + "' and not with '" + c + "'");
	}
}
