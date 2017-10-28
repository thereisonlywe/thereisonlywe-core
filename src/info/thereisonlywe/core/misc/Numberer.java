package info.thereisonlywe.core.misc;

import info.thereisonlywe.core.essentials.IOEssentials;
import info.thereisonlywe.core.essentials.StringEssentials;
import java.io.File;
import java.util.ArrayList;

/**
 * Transforms words to numbers in a given string/file. Output is not guaranteed
 * to be unique.
 */
public class Numberer
{
	public static String transformString(File f)
	{
		return transformString(IOEssentials.readString(f));
	}

	public static String transformString(File f, boolean removeDiacritics)
	{
		return transformString(IOEssentials.readString(f), removeDiacritics);
	}

	public static String transformString(File f, String wordSeparator)
	{
		return transformString(IOEssentials.readString(f), wordSeparator);
	}

	public static String transformString(String s)
	{
		return transformString(s, false);
	}

	public static String transformString(String s, boolean removeDiacritics)
	{
		return transformString(s, removeDiacritics, "\n");
	}

	public static String transformString(String s, boolean removeDiacritics,
		String wordSeparator)
	{
		final ArrayList<String> result = new ArrayList<String>();
		final String lines[] = StringEssentials.splitLines(s);
		// for each new line in input
		for (final String line : lines)
		{
			final String[] words = StringEssentials.splitWords(line);
			// transform each word to a number and add to the arraylist
			for (final String word : words)
			{
				result.add(String.valueOf(wordAsNumber(word, removeDiacritics)));
			}
		}
		// glue values in arraylist with newline and return
		return StringEssentials.glue(result, wordSeparator);
	}

	public static String transformString(String s, String wordSeparator)
	{
		return transformString(s, false, wordSeparator);
	}

	private static int wordAsNumber(String s, boolean removeDiacritics)
	{
		if (removeDiacritics)
		{
			StringEssentials.removeDiacritics(s);
		}
		int result = 0;
		for (int i = 0; i < s.length(); i++)
		{
			result += Character.codePointAt(s, i);
		}
		return result;
	}
}
