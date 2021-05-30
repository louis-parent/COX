package cox.parser.automaton.state;

import cox.model.utils.XMLIdentifier;
import cox.parser.exception.parsing.MalformedIdentifierException;

public interface IdentifierParser
{
	public default XMLIdentifier parseIdentifier(String identifier) throws MalformedIdentifierException
	{
		String[] splitted = identifier.split(":");
		
		if(splitted.length == 1)
		{
			return new XMLIdentifier(splitted[0]);
		}
		else if (splitted.length == 2)
		{
			return new XMLIdentifier(splitted[0], splitted[1]);
		}
		else
		{
			throw new MalformedIdentifierException(identifier);
		}
	}
}
