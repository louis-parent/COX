package cox.writer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;

import cox.model.document.XMLDocument;
import cox.writer.stringifier.XMLStringifier;

public class XMLWriter
{	
	public static void write(XMLDocument document, File file, XMLOutputOptions... options) throws IOException
	{
		Files.writeString(file.toPath(), XMLWriter.write(document, options), StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);
	}
	
	public static String write(XMLDocument document, XMLOutputOptions... options)
	{
		return new XMLStringifier(document, Arrays.asList(options)).toString();
	}
}
