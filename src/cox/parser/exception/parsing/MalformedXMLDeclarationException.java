package cox.parser.exception.parsing;

import cox.parser.exception.COXAutomatonException;

public class MalformedXMLDeclarationException extends COXAutomatonException
{
	private static final long serialVersionUID = 887205219282612003L;

	public MalformedXMLDeclarationException()
	{
		super("XML Declaration values must be formed like attributes of XML tags");
	}
}
