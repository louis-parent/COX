package cox.parser.exception.parsing;

import cox.parser.exception.COXAutomatonException;

public class MalformedIdentifierException extends COXAutomatonException
{
	private static final long serialVersionUID = -5956731335624125783L;

	public MalformedIdentifierException(String identifier)
	{
		super("Valid XML identifier can only be composed of a name, or a namespace and a name separated by ':', \"" + identifier + "\" is not a valid identifier");
	}
}
