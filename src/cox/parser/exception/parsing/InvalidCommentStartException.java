package cox.parser.exception.parsing;

import cox.model.XMLCharaters;
import cox.parser.exception.COXAutomatonException;

public class InvalidCommentStartException extends COXAutomatonException
{
	private static final long serialVersionUID = -4902304561183344085L;

	public InvalidCommentStartException(char c)
	{
		super("Comment markup must begin by '" + XMLCharaters.OPENING_TAG + XMLCharaters.COMMENT_DELIMITER + XMLCharaters.COMMENT_CONTENT_DELIMITER + XMLCharaters.COMMENT_CONTENT_DELIMITER + "' and not with '" + c + "'");
	}
}
