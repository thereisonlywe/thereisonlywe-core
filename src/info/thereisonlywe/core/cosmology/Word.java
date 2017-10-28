package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.StringEssentials;
import java.security.InvalidParameterException;

/**
 * A Word may only contain Letters and space char.
 * 
 */
public class Word
{
	private final String word;

	public Word(char[] chars)
	{
		this(String.valueOf(chars));
	}

	public Word(Letter[] array)
	{
		final StringBuilder sb = new StringBuilder();
		for (final Letter element : array)
		{
			sb.append(element.toString());
		}
		this.word = normalize(sb.toString());
	}

	public Word(String arabicWord)
	{
		this.word = normalize(arabicWord);
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		final Word other = (Word) obj;
		if (this.word == null)
		{
			if (other.word != null) return false;
		}
		else if (!this.word.equals(other.word)) return false;
		return true;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (this.word == null ? 0 : this.word.hashCode());
		return result;
	}

	public int length()
	{
		return this.word.length();
	}

	public char charAt(int index)
	{
		return this.word.charAt(index);
	}

	public Letter letterAt(int index)
	{
		return Letter.valueOf(charAt(index));
	}

	public Letter[] toArray()
	{
		final Letter[] result = new Letter[this.word.length()];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = letterAt(i);
		}
		return result;
	}

	public static boolean isWord(String wordCanditate)
	{
		for (int i = 0; i < wordCanditate.length(); i++)
		{
			boolean b = Letter.isLetter(wordCanditate.charAt(i));
			if (!b) return false;
		}
		return true;
	}

	@Override
	public String toString()
	{
		return this.word;
	}

	private String normalize(String arabicWord)
	{
		arabicWord = StringEssentials.removeDiacritics(arabicWord);
		if (!isWord(arabicWord)) throw new InvalidParameterException(
			"Invalid Word.");
		return arabicWord;
	}
}
