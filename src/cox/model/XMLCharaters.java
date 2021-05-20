package cox.model;

import java.security.InvalidParameterException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Optional;

public enum XMLCharaters
{
	OPENING_TAG('<'),
	CLOSING_TAG('>'),
	CLOSING_TAG_MARKER('/'),
	ATTRIBUTE_KEY_VALUE_SEPARATOR('='),
	ATTRIBUTE_DELIMITER('"'),
	CHAR_ESCAPER('&'),
	PROCESSING_INSTRUCTION_DELIMITER('?');

	private final char c;

	private XMLCharaters(char c)
	{
		this.c = c;
	}

	public char getChar()
	{
		return this.c;
	}

	@Override
	public String toString()
	{
		return String.valueOf(this.c);
	}

	public static XMLCharaters forChar(char c)
	{
		Optional<XMLCharaters> xmlCharacter = XMLCharaters.characters().stream().filter(xmlChar -> {
			return xmlChar.getChar() == c;
		}).findFirst();

		if(xmlCharacter.isEmpty())
		{
			throw new InvalidParameterException("No XML special char for " + c);
		}
		else
		{
			return xmlCharacter.get();
		}
	}

	public static boolean isSpecialChar(char c)
	{
		return XMLCharaters.characters().stream().anyMatch(xmlChar -> {
			return xmlChar.getChar() == c;
		});
	}

	public static boolean isIdentifier(char c)
	{
		return Character.isAlphabetic(c) || Character.isDigit(c) || (c == '-') || (c == '_') || (c == '.') || (c == ':');
	}

	public static boolean isWhitespace(char c)
	{
		return Character.isWhitespace(c);
	}

	private static Collection<XMLCharaters> characters()
	{
		return Arrays.asList(XMLCharaters.values());
	}
}
