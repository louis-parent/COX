package cox.parser.exception.parsing;

import cox.parser.exception.COXAutomatonException;

public class DuplicateAttributeNameException extends COXAutomatonException
{
	private static final long serialVersionUID = 7616407816629435101L;

	public DuplicateAttributeNameException(String name)
	{
		super("Multiple attribute with same name \"" + name + "\" define for the same tag");
	}
}
