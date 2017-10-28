package info.thereisonlywe.core.essentials;

import info.thereisonlywe.core.search.SearchEngine;
import info.thereisonlywe.core.search.SearchModifier;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 
 * @author thereisonlywe
 */
public class StringEssentials
{
	public static String toArabicNumerals(String str, boolean check)
	{
		if (check && LanguageEssentials.Character.containsArabicCharacter(str)) return str;
		char[] arabicChars = { '٠', '١', '٢', '٣', '٤', '٥', '٦', '٧', '٨', '٩' };
		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < str.length(); i++)
		{
			if (Character.isDigit(str.charAt(i)))
			{
				builder.append(arabicChars[(str.charAt(i)) - 48]);
			}
			else
			{
				builder.append(str.charAt(i));
			}
		}
		return builder.toString();
	}

	public static String toLatinNumerals(String number, boolean check)
	{
		if (check && !LanguageEssentials.Character.containsArabicCharacter(number)) return number;
		char[] chars = new char[number.length()];
		for (int i = 0; i < number.length(); i++)
		{
			char ch = number.charAt(i);
			if (ch >= 0x0660 && ch <= 0x0669) ch -= 0x0660 - '0';
			else if (ch >= 0x06f0 && ch <= 0x06F9) ch -= 0x06f0 - '0';
			chars[i] = ch;
		}
		return new String(chars);
	}

	public static String formatEmail(String email)
	{
		email = StringEssentials.toASCII(email);
		email = email.replace("..", ".");
		email = email.replace("@.", "@");
		if (email.startsWith(".")) email = new String(email.substring(1,
			email.length()));
		if (email.endsWith(".")) email = new String(email.substring(0,
			email.length() - 1));
		if (!email.contains("."))
		{
			int index = email.indexOf("info");
			if (index == -1) index = email.indexOf("com"); // gov, net, biz, org
			if (index != -1) email = new String(email.substring(0, index)) + "."
				+ new String(email.substring(index, email.length()));
		}
		return email;
	}

	public static int count(String string, String charactersToLookFor)
	{
		return indicesOf(string, charactersToLookFor).length;
	}

	public static String limitCharacters(String s, int maxChars)
	{
		if (s.length() < maxChars) return s;
		else
		{
			return new String(s.substring(0, maxChars - 3) + "...");
		}
	}

	public static String camelCaseToStartCase(String s)
	{
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			char c = s.charAt(i);
			if (i != 0 && Character.isUpperCase(c))
			{
				sb.append(" ");
			}
			if (i == 0)
			{
				c = Character.toUpperCase(c);
			}
			sb.append(c);
		}
		return sb.toString();
	}

	public static String capitalizeWords(String s)
	{
		return capitalizeWords(s, null);
	}

	public static String capitalizeWords(String s, Locale locale)
	{
		final String[] words = splitWords(s);
		for (int i = 0; i < words.length; i++)
		{
			String tmp;
			int x = 1;
			char c = words[i].charAt(0);
			while (words[i].length() > x
				&& (c == '(' || c == '{' || c == '[' || c == '"' || c == '\''))
			{
				c = words[i].charAt(x);
				x++;
			}
			tmp = (locale == null ? new String(c + "").toUpperCase() : new String(c
				+ "").toUpperCase(locale));
			words[i] = new String(words[i].substring(0, x - 1)) + tmp
				+ new String(words[i].substring(x));
		}
		return glue(words, " ");
	}

	public static boolean contains(String array[], String s)
	{
		return contains(array, s, SearchModifier.CONTAINS);
	}

	public static boolean contains(String array[], String s, SearchModifier sm)
	{
		if (array == null) return false;
		for (final String element : array)
		{
			if (SearchEngine.search(element, s, sm)) return true;
		}
		return false;
	}

	public static boolean contains(ArrayList<String> array, String s,
		SearchModifier sm)
	{
		if (array == null) return false;
		for (final String element : array)
		{
			if (SearchEngine.search(element, s, sm)) return true;
		}
		return false;
	}

	public static boolean containsAll(String sentence, String[] phrases,
		SearchModifier sm)
	{
		for (final String element : phrases)
		{
			if (element == null || element.trim().length() == 0) continue;
			if (!SearchEngine.search(sentence, element, sm)) return false;
		}
		return true;
	}

	// public static boolean containsSome(ArrayList<String> main,
	// ArrayList<String> valuesToCheckFor,
	// SearchModifier sm, int percent)
	// {
	//
	//
	// for (final String element : phrases)
	// {
	// if (element == null || element.trim().length() == 0) continue;
	// if (!SearchEngine.search(sentence, element, sm)) return false;
	// }
	// return true;
	// }
	public static boolean containsCharacter(String s, Character.UnicodeBlock u)
	{
		for (int l = 0; l < s.length(); l++)
		{
			if (Character.UnicodeBlock.of(s.charAt(l)).equals(u)) return true;
		}
		return false;
	}

	public static boolean containsNumeral(String s)
	{
		return s.matches(".*\\d+.*");
	}

	public static ArrayList<String> cut(String s, String separator)
	{
		return new ArrayList<String>(Arrays.asList(s.split(separator)));
	}

	/**
	 * Checks if two strings are roughly equal. Ignores case, accents and
	 * diacritics.
	 */
	public static boolean equals(String s1, String s2)
	{
		return equals(s1, s2, LocaleEssentials.LOCALE_DEFAULT);
	}

	/**
	 * Checks if two strings are roughly equal. Ignores case, accents and
	 * diacritics.
	 */
	public static boolean equals(String s1, String s2, Locale locale)
	{
		s1 = s1.replace("'", "");
		s1 = s1.toLowerCase(locale);
		s1 = removeDiacritics(s1);
		s2 = s2.replace("'", "");
		s2 = s2.toLowerCase(locale);
		s2 = removeDiacritics(s2);
		return s1.equals(s2);
	}

	public static String enclose(String s, char charToBeEnclosed,
		char charToEncloseWith)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			final char c = s.charAt(i);
			if (c == charToBeEnclosed)
			{
				sb.append(charToEncloseWith);
				sb.append(charToBeEnclosed);
				sb.append(charToEncloseWith);
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static String fetchDelimitedEntry(String s, char delimiter, int entryNo)
	{
		for (int i = 1; i < entryNo; i++)
		{
			s = new String(s.substring(s.indexOf(delimiter) + 1));
		}
		if (s.contains("" + delimiter))
		{
			s = new String(s.substring(0, s.indexOf(delimiter)));
		}
		return s;
	}

	public static String free(String s, char enclosedChar, char enclosingChar)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			final char c = s.charAt(i);
			if (c == enclosingChar && i < s.length() - 2
				&& s.charAt(i + 1) == enclosedChar && s.charAt(i + 2) == enclosingChar)
			{
				sb.append(enclosedChar);
				i += 2;
			}
			else
			{
				sb.append(c);
			}
		}
		return sb.toString();
	}

	public static int getBlanksUpTo(int caret, String text)
	{
		int count = 0;
		for (int i = 0; i < caret; i++)
		{
			if (text.charAt(i) == ' ')
			{
				count++;
			}
		}
		return count;
	}

	public static int getIndexOfNextBlank(int caret, String text)
	{
		for (int i = caret + 1; i < text.length(); i++)
		{
			if (text.charAt(i) == ' ') return i;
		}
		return -1;
	}

	public static int getIndexOfNextChar(int caret, String text, char c)
	{
		for (int i = caret + 1; i < text.length(); i++)
		{
			if (text.charAt(i) == c) return i;
		}
		return -1;
	}

	public static int getIndexOfNthWord(int n, String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (getBlanksUpTo(i, s) == n - 1) return i;
		}
		return -1;
	}

	public static int getIndexOfPreviousBlank(int caret, String text)
	{
		for (int i = caret; i >= 0; i--)
		{
			if (text.charAt(i) == ' ') return i;
		}
		return -1;
	}

	public static int getIndexOfPreviousChar(int caret, String text, char c)
	{
		for (int i = caret; i >= 0; i--)
		{
			if (text.charAt(i) == c) return i;
		}
		return -1;
	}

	public static String getLongestString(String[] s)
	{
		long longest = Long.MIN_VALUE;
		int index = 0;
		for (int i = 0; i < s.length; i++)
		{
			if (s[i].length() > longest)
			{
				longest = s[i].length();
				index = i;
			}
		}
		return s[index];
	}

	public static int getNumberOfDiacritics(String s)
	{
		int count = 0;
		for (int i = 0; i < s.length(); i++)
		{
			if (LanguageEssentials.Character.isDiacriticMark(s.charAt(i)))
			{
				count++;
			}
		}
		return count;
	}

	public static String getShortestString(String[] s)
	{
		long shortest = Long.MAX_VALUE;
		int index = 0;
		for (int i = 0; i < s.length; i++)
		{
			if (s[i].length() < shortest)
			{
				shortest = s[i].length();
				index = i;
			}
		}
		return s[index];
	}

	public static String glue(int[] s, String separator)
	{
		if (s == null || s.length == 0) return "";
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length - 1; i++)
		{
			result.append(s[i]);
			result.append(separator);
		}
		result.append(s[s.length - 1]);
		return result.toString();
	}

	public static <T> String glue(List<T> s, char separator)
	{
		return glue(s, separator + "");
	}

	public static <T> String glue(List<T> s, String separator)
	{
		if (s.isEmpty()) return "";
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.size() - 1; i++)
		{
			result.append(s.get(i));
			result.append(separator);
		}
		result.append(s.get(s.size() - 1));
		return result.toString();
	}

	public static <T> String glue(Set<T> words, char separator)
	{
		return glue(words, separator + "");
	}

	public static <T> String glue(Set<T> words, String separator)
	{
		if (words.isEmpty()) return "";
		final StringBuilder result = new StringBuilder();
		int counter = 1;
		for (final T str : words)
		{
			if (str != null)
			{
				result.append(str.toString());
				if (counter != words.size())
				{
					result.append(separator);
				}
				counter++;
			}
		}
		return result.toString();
	}

	public static <T> String glue(T[] s, char separator)
	{
		return glue(s, separator + "");
	}

	public static <T> String glue(T[] s, String separator)
	{
		if (s == null || s.length == 0) return "";
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length - 1; i++)
		{
			result.append(s[i].toString());
			result.append(separator);
		}
		result.append(s[s.length - 1]);
		return result.toString();
	}

	public static String indentParagraph(String s, String indent)
	{
		if (s == null) return null;
		s = indent + s;
		s = s.replace("\n", "\n".concat(indent));
		return s;
	}

	public static String[] indentParagraph(String[] array, String indent)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = indentParagraph(array[i], indent);
		}
		return array;
	}

	public static int[] indicesOf(String sentence, String phrase)
	{
		return indicesOf(sentence, phrase, SearchModifier.CONTAINS);
	}

	public static int[] indicesOf(String sentence, String phrase,
		SearchModifier sm)
	{
		int i = 0;
		final ArrayList<Integer> occurences = new ArrayList<Integer>();
		while (i < sentence.length())
		{
			final int temp = sentence.indexOf(phrase, i);
			if (temp == -1)
			{
				break;
			}
			else
			{
				if (sm.equals(SearchModifier.CONTAINS)) occurences.add(temp);
				else if (sm.equals(SearchModifier.CONTAINS_PHRASE_EXACTLY))
				{
					if (temp + phrase.length() == sentence.length()
						|| Character.isWhitespace(sentence.charAt(temp + phrase.length())))
					{
						if (temp == 0 || Character.isWhitespace(sentence.charAt(temp - 1))) occurences
							.add(temp);
					}
				}
				else if (sm.equals(SearchModifier.CONTAINS_PHRASE_ENDING_WITH))
				{
					if (temp + phrase.length() == sentence.length()
						|| Character.isWhitespace(sentence.charAt(temp + phrase.length())))
					{
						occurences.add(temp);
					}
				}
				else if (sm.equals(SearchModifier.CONTAINS_PHRASE_STARTING_WITH))
				{
					if (temp == 0 || Character.isWhitespace(sentence.charAt(temp - 1))) occurences
						.add(temp);
				}
			}
			i = temp + phrase.length();
		}
		if (occurences.size() <= 0) return new int[0];
		final int[] result = new int[occurences.size()];
		for (int j = 0; j < result.length; j++)
		{
			result[j] = occurences.get(j).intValue();
		}
		return result;
	}

	public static boolean isLowerCase(String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (Character.isUpperCase(s.charAt(i))) { return false; }
		}
		return true;
	}

	public static boolean isUpperCase(String s)
	{
		for (int i = 0; i < s.length(); i++)
		{
			if (Character.isLowerCase(s.charAt(i))) { return false; }
		}
		return true;
	}

	public static boolean areAlmostSimilar(String s1, String s2)
	{
		return areSimilar(s1, s2, 0.5);
	}

	public static boolean areAlmostSimilar(String[] s1, String[] s2)
	{
		return areSimilar(s1, s2, 0.5);
	}

	public static boolean areAlmostSimilar(List<String> s1, List<String> s2)
	{
		return areSimilar(s1, s2, 0.5);
	}

	public static boolean areSimilar(String s1, String s2)
	{
		return areSimilar(s1, s2, 0.75);
	}

	public static boolean areSimilar(String[] s1, String[] s2)
	{
		return areSimilar(s1, s2, 0.75);
	}

	public static boolean areSimilar(List<String> s1, List<String> s2)
	{
		return areSimilar(s1, s2, 0.75);
	}

	public static boolean areHighlySimilar(String s1, String s2)
	{
		return areSimilar(s1, s2, 0.85);
	}

	public static boolean areHighlySimilar(String[] s1, String[] s2)
	{
		return areSimilar(s1, s2, 0.85);
	}

	public static boolean areHighlySimilar(List<String> s1, List<String> s2)
	{
		return areSimilar(s1, s2, 0.85);
	}

	public static boolean areExceptionallySimilar(String s1, String s2)
	{
		return areSimilar(s1, s2, 0.90);
	}

	public static boolean areExceptionallySimilar(String[] s1, String[] s2)
	{
		return areSimilar(s1, s2, 0.90);
	}

	public static boolean areExceptionallySimilar(List<String> s1, List<String> s2)
	{
		return areSimilar(s1, s2, 0.90);
	}

	public static boolean areAlmostIdentical(String s1, String s2)
	{
		return areSimilar(s1, s2, 0.95);
	}

	public static boolean areAlmostIdentical(String[] s1, String[] s2)
	{
		return areSimilar(s1, s2, 0.95);
	}

	public static boolean areAlmostIdentical(List<String> s1, List<String> s2)
	{
		return areSimilar(s1, s2, 0.95);
	}

	public static boolean areSimilar(String s1, String s2, double percent)
	{
		return getSimilarity(s1, s2) >= percent;
	}

	public static boolean areSimilar(List<String> s1, List<String> s2,
		double percent)
	{
		return getSimilarity(glue(s1, "."), glue(s2, ".")) >= percent;
	}

	public static boolean areSimilar(String[] s1, String[] s2, double percent)
	{
		return getSimilarity(glue(s1, "."), glue(s2, ".")) >= percent;
	}

	public static double getSimilarity(String[] s1, String[] s2)
	{
		return getSimilarity(glue(s1, "."), glue(s2, "."));
	}

	public static double getSimilarity(List<String> s1, List<String> s2)
	{
		return getSimilarity(glue(s1, "."), glue(s2, "."));
	}

	public static double getSimilarity(String s1, String s2)
	{
		if (s1 == null || s2 == null || s1.equals(s2)) return 1.0;
		else
		{
			if (s1.length() < s2.length())
			{ // s1 should always be bigger
				String swap = s1;
				s1 = s2;
				s2 = swap;
			}
			int bigLen = s1.length();
			if (bigLen == 0) { return 1.0; /* both strings are zero length */}
			return (bigLen - computeEditDistance(s1, s2)) / (double) bigLen;
		}
	}

	private static int computeEditDistance(String s1, String s2)
	{
		s1 = s1.toLowerCase();
		s2 = s2.toLowerCase();
		int[] costs = new int[s2.length() + 1];
		for (int i = 0; i <= s1.length(); i++)
		{
			int lastValue = i;
			for (int j = 0; j <= s2.length(); j++)
			{
				if (i == 0) costs[j] = j;
				else
				{
					if (j > 0)
					{
						int newValue = costs[j - 1];
						if (s1.charAt(i - 1) != s2.charAt(j - 1)) newValue = Math.min(
							Math.min(newValue, lastValue), costs[j]) + 1;
						costs[j - 1] = lastValue;
						lastValue = newValue;
					}
				}
			}
			if (i > 0) costs[s2.length()] = lastValue;
		}
		return costs[s2.length()];
	}

	public static <T> String list(double[] array, T[] names)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++)
		{
			sb.append(names[i].toString());
			sb.append(": ");
			sb.append(array[i]);
			if (i + 1 < array.length)
			{
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static <T> String list(int[] array, T[] names)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++)
		{
			sb.append(names[i].toString());
			sb.append(": ");
			sb.append(array[i]);
			if (i + 1 < array.length)
			{
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static <T> String list(T[] array, T[] names)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < array.length; i++)
		{
			sb.append(names[i].toString());
			sb.append(": ");
			sb.append(array[i].toString());
			if (i + 1 < array.length)
			{
				sb.append("\n");
			}
		}
		return sb.toString();
	}

	public static String multiLine(String s, int maxCharsPerLine)
	{
		final char[] digit = s.toCharArray();
		int count = 0;
		for (int i = 0; i < digit.length; i++)
		{
			count++;
			if (count > maxCharsPerLine)
			{
				if (digit[i] == ' ')
				{
					digit[i] = '\n';
					count = 0;
				}
			}
		}
		s = new String(digit);
		return s;
	}

	public static String normalizeAleph(String s)
	{
		return s
			.replace(LanguageEssentials.Character.ALEPH_WASLA,
				LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_WAVY_HAMZA_ABOVE,
				LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_WAVY_HAMZA_BELOW,
				LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_HIGH_HAMZA,
				LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_HAMZA_ABOVE,
				LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_HAMZA_BELOW,
				LanguageEssentials.Character.ALEPH)
			// .replace(LanguageEssentials.Character.ALEPH_SUPERSCRIPT,
			// LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_MADDA,
				LanguageEssentials.Character.ALEPH)
			.replace(LanguageEssentials.Character.ALEPH_WTF,
				LanguageEssentials.Character.ALEPH);
	}

	public static String addPaddingToNumber(int number, int numberOfDigits)
	{
		return addPaddingToNumber(String.valueOf(number), numberOfDigits);
	}

	public static String addPaddingToNumber(String numberAsString,
		int numberOfTotalDigits)
	{
		int dif = numberOfTotalDigits - numberAsString.length();
		if (dif <= 0) return numberAsString;
		else
		{
			String s = "";
			for (int i = 0; i < dif; i++)
				s += "0";
			s += numberAsString;
			return s;
		}
	}

	public static String removeAll(String s, char c)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			final char ch = s.charAt(i);
			if (ch != c)
			{
				sb.append(ch);
			}
		}
		return sb.toString();
	}

	public static String removeControlCharacters(String s)
	{
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			final char ch = s.charAt(i);
			if (!Character.isISOControl(ch))
			{
				result.append(ch);
			}
		}
		return result.toString();
	}

	public static String[] removeDiacritics(String s[])
	{
		for (int i = 0; i < s.length; i++)
		{
			s[i] = removeDiacritics(s[i]);
		}
		return s;
	}

	public static String removeDiacritics(String s)
	{
		return removeDiacritics(s, true, true);
	}

	public static String removeDiacritics(String s,
		boolean preserveTurkishCharacters, boolean normalizeAleph)
	{
		if (s == null) return s;
		s = s.replace("|", "VVV");
		s = s.replace("1", "_I_");
		s = s.replace("2", "_II_");
		s = s.replace("3", "_III_");
		s = s.replace("4", "_IV_");
		s = s.replace("5", "_V_");
		s = s.replace("6", "_VI_");
		s = s.replace("7", "_VII_");
		s = s.replace("8", "_VIII_");
		s = s.replace("9", "_IX_");
		// ğüşiçö
		if (normalizeAleph) s = normalizeAleph(s);
		if (preserveTurkishCharacters)
		{
			s = s.replace("ğ", "_gggg_");
			s = s.replace("ü", "_uuuu_");
			s = s.replace("ş", "_ssss_");
			s = s.replace("ç", "_cccc_");
			s = s.replace("ö", "_oooo_");
			s = s.replace("İ", "_IIII_");
			s = s.replace("Ğ", "_GGGG_");
			s = s.replace("Ş", "_SSSS_");
			s = s.replace("Ü", "_UUUU_");
			s = s.replace("Ç", "_CCCC_");
			s = s.replace("Ö", "_OOOO_");
		}
		String result = "";
		final Pattern pattern = Pattern
			.compile("\\p{InCombiningDiacriticalMarks}+");
		result = s.replaceAll("[^\\p{L}\\p{Z}\\p{P}]", "");
		result = pattern.matcher(Normalizer.normalize(result, Normalizer.Form.NFD))
			.replaceAll("");
		result = result.replace("VVV", "|");
		result = result.replace("_I_", "1");
		result = result.replace("_II_", "2");
		result = result.replace("_III_", "3");
		result = result.replace("_IV_", "4");
		result = result.replace("_V_", "5");
		result = result.replace("_VI_", "6");
		result = result.replace("_VII_", "7");
		result = result.replace("_VIII_", "8");
		result = result.replace("_IX_", "9");
		if (preserveTurkishCharacters)
		{
			result = result.replace("_gggg_", "ğ");
			result = result.replace("_uuuu_", "ü");
			result = result.replace("_ssss_", "ş");
			result = result.replace("_cccc_", "ç");
			result = result.replace("_oooo_", "ö");
			result = result.replace("_IIII_", "İ");
			result = result.replace("_GGGG_", "Ğ");
			result = result.replace("_SSSS_", "Ş");
			result = result.replace("_UUUU_", "Ü");
			result = result.replace("_CCCC_", "Ç");
			result = result.replace("_OOOO_", "Ö");
		}
		if (LanguageEssentials.Character.isValidArabicChar(result))
		{
			final StringBuilder sb = new StringBuilder();
			for (int i = 0; i < result.length(); i++)
			{
				final char c = result.charAt(i);
				if (c == LanguageEssentials.Character.HAMZA_ABOVE.charAt(0)
					|| c == LanguageEssentials.Character.HAMZA_BELOW.charAt(0)
					|| c == LanguageEssentials.Character.MADDAH.charAt(0) || c == 'ۦ'
					|| c == 'ـ' || c == 'ۥ')
				{
				}
				else
				{
					sb.append(c);
				}
			}
			result = null;
			return sb.toString();
		}
		else return result;
	}

	public static LinkedHashSet<String> removeDuplicates(List<String> s)
	{
		return new LinkedHashSet<String>(s);
	}

	public static String removeConsecutiveCharacters(String s,
		int maxAllowedRepetition)
	{
		return s.replaceAll("(.)\\1{" + maxAllowedRepetition + ",}", "$1");
	}

	public static String removeConsecutiveLetters(String s,
		int maxAllowedRepetition)
	{
		return s.replaceAll("(\\p{L}\\p{M}*)\\1{" + maxAllowedRepetition + ",}",
			"$1");
		// return s.replaceAll("(\\p{L}\\p{M}*)(\\1{2,})", "$1");
	}

	/**
	 * Removes duplicate chars from the String.
	 */
	public static LinkedHashSet<String> removeDuplicates(String s)
	{
		return removeDuplicates(toStringList(s));
	}

	public static LinkedHashSet<String> removeDuplicates(String[] s)
	{
		final LinkedHashSet<String> lines = new LinkedHashSet<String>(s.length);
		for (final String element : s)
		{
			lines.add(element);
		}
		return lines;
	}

	public static LinkedHashSet<String> removeDuplicatesAndEmptyEntries(String[] s)
	{
		final LinkedHashSet<String> lines = new LinkedHashSet<String>(s.length);
		for (final String element : s)
		{
			if (!element.trim().equals("")) lines.add(element);
		}
		return lines;
	}

	public static String[] removeEmptyEntriesFromArray(String[] array)
	{
		final ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] != null && !array[i].trim().equals(""))
			{
				result.add(array[i]);
			}
		}
		final String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}

	public static String[] removeEntriesFromArray(String[] array,
		String stringToBeRemoved)
	{
		if (array == null) return null;
		else if (stringToBeRemoved == null || stringToBeRemoved.trim().equals("")) return removeEmptyEntriesFromArray(array);
		else if (array.length == 1) { return (array[0] == null || array[0]
			.equals(stringToBeRemoved)) ? null : array; }
		final ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < array.length; i++)
		{
			if (array[i] == null) result.add(array[i]); // keep null entry
			else if (!array[i].equals(stringToBeRemoved))
			{
				result.add(array[i]);
			}
		}
		final String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}

	public static String removeForeignChars(String s,
		Character.UnicodeBlock[] CharacterUnicodeBlocks)
	{
		if (s == null || s.equals("") || s.equals(" ")) return s;
		else
		{
			StringBuilder sb = new StringBuilder();
			for (int i = 0; i < s.length(); i++)
			{
				char c = s.charAt(i);
				if (c == ' ')
				{
					sb.append(c);
				}
				else
				{
					for (int z = 0; z < CharacterUnicodeBlocks.length; z++)
					{
						if (Character.UnicodeBlock.of((c))
							.equals(CharacterUnicodeBlocks[z]))
						{
							sb.append(c);
							break;
						}
					}
				}
			}
			return sb.toString();
		}
	}

	public static String removeForeignChars(String s,
		Character.UnicodeBlock CharacterUnicodeBlock)
	{
		return removeForeignChars(s,
			new Character.UnicodeBlock[] { CharacterUnicodeBlock });
	}

	public static String[] removeSimilarEntriesFromArray(String[] array,
		String stringToBeRemoved)
	{
		final ArrayList<String> result = new ArrayList<String>();
		for (int i = 0; i < array.length; i++)
		{
			if (!array[i].contains(stringToBeRemoved))
			{
				result.add(array[i]);
			}
		}
		final String[] res = new String[result.size()];
		result.toArray(res);
		return res;
	}

	public static String[] removeEntriesFromArray(String[] array,
		String[] stringsToBeRemoved)
	{
		for (final String element : stringsToBeRemoved)
		{
			array = removeEntriesFromArray(array, element);
		}
		return array;
	}

	public static String[] removeSimilarEntriesFromArray(String[] array,
		String[] stringsToBeRemoved)
	{
		for (final String element : stringsToBeRemoved)
		{
			array = removeSimilarEntriesFromArray(array, element);
		}
		return array;
	}

	public static String removeNumbers(String x)
	{
		x = x.replace("0", "");
		x = x.replace("1", "");
		x = x.replace("2", "");
		x = x.replace("3", "");
		x = x.replace("4", "");
		x = x.replace("5", "");
		x = x.replace("6", "");
		x = x.replace("7", "");
		x = x.replace("8", "");
		x = x.replace("9", "");
		return x;
	}

	public static String removeNonNumbers(String x)
	{
		return x.replaceAll("[^\\d]", "");
	}

	public static String removeNonTextCharacters(String x)
	{
		x = x.replace(";", "");
		x = x.replace(":", "");
		x = x.replace("-", "");
		x = x.replace("_", "");
		x = x.replace("?", "");
		x = x.replace("!", "");
		x = x.replace(",", "");
		x = x.replace(".", "");
		x = x.replace("&", "");
		x = x.replace("(", "");
		x = x.replace(")", "");
		x = x.replace("<", "");
		x = x.replace(">", "");
		x = x.replace("{", "");
		x = x.replace("}", "");
		x = x.replace("[", "");
		x = x.replace("]", "");
		x = x.replace("\"", "");
		x = x.replace("/", "");
		x = x.replace("’", "");
		x = x.replace("‘", "");
		x = x.replace("–", "");
		x = x.replace("”", "");
		x = x.replace("“", "");
		x = x.replace("*", "");
		x = x.replace("»", "");
		x = x.replace("«", "");
		x = x.replace("'", "");
		x = x.replace("´", "");
		x = x.replace("—", "");
		x = x.replace("|", "");
		x = x.replace("+", "");
		return x;
	}

	public static String removeBlanks(String s)
	{
		return s.replace(" ", "").replace("\u00A0", "").replace("\u202F", "")
			.replace("\uFEFF", "");
	}

	public static String[] removePunctuation(String s[])
	{
		for (int i = 0; i < s.length; i++)
		{
			s[i] = removePunctuation(s[i]);
		}
		return removeEmptyEntriesFromArray(s);
	}

	public static String removePunctuation(String s)
	{
		final StringBuilder result = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			if (!LanguageEssentials.Character.isQuranicPauseMark(s.charAt(i))
				&& (Character.isLetterOrDigit(s.charAt(i)) || LanguageEssentials.Character
					.isValidArabicChar(s.charAt(i))))
			{
				result.append(s.charAt(i));
			}
		}
		return result.toString();
	}

	public static String replaceMatches(String s, Pattern p, String replacement)
	{
		Matcher m = p.matcher(s);
		while (m.find())
		{
			s = s.replace(m.group(), replacement);
		}
		return s;
	}

	public static String[] replace(String[] array, String OLD, String NEW)
	{
		for (int i = 0; i < array.length; i++)
		{
			array[i] = array[i].replace(OLD, NEW);
		}
		return array;
	}

	public static String replaceWeirdBlanks(String s)
	{
		return s.replace("\u00A0", " ").replace("\u202F", " ")
			.replace("\uFEFF", " ");
	}

	// public static String replaceAll(String s, char OLD, char NEW)
	// {
	// final StringBuilder sb = new StringBuilder();
	// for (int i = 0; i < s.length(); i++)
	// {
	// final char curC = s.charAt(i);
	// if (curC != OLD)
	// {
	// sb.append(curC);
	// }
	// else
	// {
	// sb.append(NEW);
	// }
	// }
	// return sb.toString();
	// }
	public static String reverseWordOrder(String s)
	{
		final String[] words = splitWords(s);
		final StringBuilder sb = new StringBuilder();
		for (int i = words.length - 1; i >= 0; i--)
		{
			sb.append(words[i]);
			if (i - 1 >= 0)
			{
				sb.append(" ");
			}
		}
		return sb.toString();
	}

	public static String[] reverseWordOrder(String[] s)
	{
		final String result[] = new String[s.length];
		for (int i = 0; i < s.length; i++)
		{
			result[i] = reverseWordOrder(s[i]);
		}
		return result;
	}

	public static String scatterChar(String s, char charToScatter)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			sb.append(s.charAt(i));
			sb.append(charToScatter);
		}
		return sb.toString();
	}

	public static String[] splitLines(String s)
	{
		// return s.split("[\n\r\u0085\u2028\u2028]++");
		return s.split("\\r?\\n");
	}

	public static String[] splitStatement(String s, char separator)
	{
		final String[] array = new String[2];
		final int i = s.indexOf(separator);
		if (i == -1)
		{
			array[0] = s;
			return array;
		}
		else
		{
			array[0] = new String(s.substring(0, i));
			array[1] = new String(s.substring(i + 1));
		}
		return array;
	}

	public static String[] splitStatement(String s, String separator)
	{
		final String[] array = new String[2];
		final int i = s.indexOf(separator);
		if (i == -1)
		{
			array[0] = s;
			return array;
		}
		else
		{
			array[0] = new String(s.substring(0, i));
			array[1] = new String(s.substring(i + 1));
		}
		return array;
	}

	public static String[][] splitStatement(String[] s, char separator)
	{
		final String[][] res = new String[2][s.length];
		for (int i = 0; i < s.length; i++)
		{
			res[0][i] = splitStatement(s[i], separator)[0];
			res[1][i] = splitStatement(s[i], separator)[1];
		}
		return res;
	}

	public static String[] splitWords(String s)
	{
		return removeEmptyEntriesFromArray(s.split(" "));
	}

	public static String toASCII(String s)
	{
		return Normalizer.normalize(s, Normalizer.Form.NFD).replaceAll(
			"[^\\p{ASCII}]", "");
	}

	public static String toHTMLString(String s)
	{
		StringBuilder builder = new StringBuilder();
		boolean previousWasASpace = false;
		for (char c : s.toCharArray())
		{
			if (c == ' ')
			{
				if (previousWasASpace)
				{
					builder.append("&nbsp;");
					previousWasASpace = false;
					continue;
				}
				previousWasASpace = true;
			}
			else
			{
				previousWasASpace = false;
			}
			switch (c)
			{
				case '<':
					builder.append("&lt;");
					break;
				case '>':
					builder.append("&gt;");
					break;
				case '&':
					builder.append("&amp;");
					break;
				case '"':
					builder.append("&quot;");
					break;
				case '\n':
					builder.append("<br>");
					break;
				// We need Tab support here, because we print StackTraces as HTML
				case '\t':
					builder.append("&nbsp; &nbsp; &nbsp;");
					break;
				default:
					if (c < 128)
					{
						builder.append(c);
					}
					else
					{
						builder.append("&#").append((int) c).append(";");
					}
			}
		}
		return builder.toString();
	}

	public static String toHTMLString(String[] parts)
	{
		final StringBuilder sb = new StringBuilder();
		sb.append("<html><table><tr>");
		for (final String part : parts)
		{
			sb.append("<td>");
			sb.append(part);
			sb.append("</td>");
		}
		sb.append("</tr></table></html>");
		return sb.toString();
	}

	public static String toHTMLTable(String[] columns, String[][] rows)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" /></head><table border=\"1\" width=\"95%\">");
		sb.append("<tr>");
		for (int i = 0; i < columns.length; i++)
		{
			sb.append("<th>");
			sb.append(columns[i]);
			sb.append("</th>");
		}
		sb.append("</tr>");
		for (int i = 0; i < rows.length; i++)
		{
			sb.append("<tr>");
			for (int j = 0; j < columns.length; j++)
			{
				sb.append("<td>");
				sb.append(rows[i][j]);
				sb.append("</td>");
			}
			sb.append("</tr>");
		}
		sb.append("</table>");
		sb.append("</html>");
		return sb.toString();
	}

	public static String[] toStringArray(String s)
	{
		char[] c = s.toCharArray();
		String[] result = new String[c.length];
		for (int i = 0; i < c.length; i++)
		{
			result[i] = c[i] + "";
		}
		return result;
	}

	public static List<String> toStringList(String s)
	{
		char[] c = s.toCharArray();
		List<String> list = new ArrayList<String>();
		for (char element : c)
		{
			list.add(element + "");
		}
		return list;
	}

	public static String unescapeUnicode(String s)
	{
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < s.length(); i++)
		{
			if (s.charAt(i) == '\\' && s.charAt(i + 1) == 'u')
			{
				final String t = new String(s.substring(i, i + 6));
				final char c = (char) Integer.parseInt(new String(t.substring(2)), 16);
				sb.append(c);
				i += 5;
			}
			else
			{
				sb.append(s.charAt(i));
			}
		}
		return sb.toString();
	}

	public static final String THEREISONLYWE_LINE_SEPARATOR = ")|(";
	public static final String THEREISONLYWE_VALUE_SEPARATOR = "#:#";
}
