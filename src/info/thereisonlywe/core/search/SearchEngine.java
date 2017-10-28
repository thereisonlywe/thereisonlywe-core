package info.thereisonlywe.core.search;

import info.thereisonlywe.core.essentials.LocaleEssentials;
import info.thereisonlywe.core.essentials.StringEssentials;
import java.util.Arrays;
import java.util.Locale;

// matchExpected is only true if we are expecting a match beforehand to shorten search time, otherwise it should be set to false. It is only relevant when we are dealing with similarities
public class SearchEngine
{
	public static SearchResult[] sortMatches(String text[], String phrase)
	{
		return sortMatches(
			text,
			phrase,
			findMatches(text, phrase, SearchScope.NO_LIMITATIONS, true, null,
				SearchSimilarity.SIMILAR, false));
	}

	public static SearchResult[] sortMatches(String text[], String phrase,
		SearchScope scope, boolean caseSensitive, Locale locale, double similarity,
		boolean matchExpected)
	{
		return sortMatches(
			text,
			phrase,
			findMatches(text, phrase, scope, caseSensitive, locale, similarity,
				matchExpected));
	}

	public static SearchResult[] sortMatches(String text[], String phrase,
		int[] matchInfo)
	{
		SearchResult[] sr = new SearchResult[text.length];
		for (int i = 0; i < sr.length; i++)
			sr[i] = new SearchResult(i, text[i], matchInfo[i]);
		Arrays.sort(sr);
		return sr;
	}

	public static int[] findMatches(String text[], String phrase,
		SearchScope scope, boolean caseSensitive, Locale locale, double similarity,
		boolean matchExpected)
	{
		int[] result = new int[text.length];
		for (int i = 0; i < text.length; i++)
		{
			result[i] = findMatch(text[i], phrase, scope, caseSensitive, locale,
				similarity, matchExpected);
		}
		return result;
	}

	public static int findMatch(String text, String phrase, SearchScope scope,
		boolean caseSensitive, Locale locale, double similarity,
		boolean matchExpected)
	{
		if (scope == SearchScope.ASCII_ONLY)
		{
			text = StringEssentials.toASCII(text);
			phrase = StringEssentials.toASCII(phrase);
		}
		else if (scope == SearchScope.NO_ACCENTS_AND_DIACRITICS)
		{
			text = StringEssentials.removeDiacritics(text);
			phrase = StringEssentials.removeDiacritics(phrase);
		}
		else if (scope == SearchScope.NO_LIMITATIONS)
		{
		}
		if (!caseSensitive)
		{
			if (locale == null)
			{
				locale = LocaleEssentials.LOCALE_DEFAULT;
			}
			text = text.toLowerCase(locale);
			phrase = phrase.toLowerCase(locale);
		}
		if (matchExpected)
		{
			if (matchesExactly(text, phrase)) return SearchResult.EXACT_MATCH;
			else if (contains(text, phrase)) return SearchResult.CONTAINS_MATCH;
			else if (matchesExactlySimilar(text, phrase, similarity, matchExpected)) return SearchResult.SIMILAR_EXACT_MATCH;
			else if (containsSimilar(text, phrase, similarity, matchExpected)) return SearchResult.SIMILAR_CONTAINS_MATCH;
			else return SearchResult.UNMATCHED;
		}
		else
		{
			if (containsSimilar(text, phrase, similarity, matchExpected))
			{
				if (matchesExactly(text, phrase)) return SearchResult.EXACT_MATCH;
				else if (contains(text, phrase)) return SearchResult.CONTAINS_MATCH;
				else if (matchesExactlySimilar(text, phrase, similarity, matchExpected)) return SearchResult.SIMILAR_EXACT_MATCH;
				else return SearchResult.SIMILAR_CONTAINS_MATCH;
			}
			else return SearchResult.UNMATCHED;
		}
	}

	public static boolean matchesExactlySimilar(String text, String phrase,
		double similarity, boolean matchExpected)
	{
		if (matchExpected && matchesExactly(text, phrase)) return true;
		else return (SearchSimilarity.areSimilar(text, phrase, similarity));
	}

	public static boolean containsSimilar(String text, String phrase,
		double similarity, boolean matchExpected)
	{
		if (matchExpected && contains(text, phrase)) return true;
		else
		{
			int i = phrase.charAt(phrase.length() - 1);
			if (phrase.length() >= 30 || i == '.' || i == '!' || i == '?') return SearchSimilarity
				.areSimilar(text, phrase, similarity);
			else
			{
				String[] words = StringEssentials.splitWords(phrase);
				String[] words2 = StringEssentials.splitWords(text);
				boolean[] found = new boolean[words.length];
				int count = 0;
				OUTER: for (int j = 0; j < words.length; j++)
				{
					for (int k = 0; k < words2.length; k++)
					{
						if (SearchSimilarity.areSimilar(words2[k], words[j], similarity))
						{
							found[j] = true;
							count++;
							continue OUTER;
						}
					}
				}
				if (count == words.length) return true;
				else if (words.length == 1 && count == 0) return false;
				else if (count >= 1 && words.length - count == 1)
				{
					OUTER: for (int j = 0; j < words.length; j++)
					{
						if (found[j]) continue;
						for (int k = 0; k < words2.length; k++)
						{
							/*
							 * if (similarity <= SearchSimilarity.SIMILAR &&
							 * words2[k].contains(words[j])) { count++; continue OUTER; }
							 */
							if (words2[k].startsWith(words[j]))
							{
								count++;
								continue OUTER;
							}
						}
					}
					if (count == words.length) return true;
				}
				return false;
			}
		}
	}

	public static boolean contains_SimilarPhraseExactly(String text,
		String phrase, double similarity, boolean matchExpected)
	{
		if (SearchSimilarity.areSimilar(text, phrase, similarity)) return true;
		else if (containsSimilar(text, " " + phrase + " ", similarity,
			matchExpected)) return true;
		else
		{
			int b = text.lastIndexOf(" ");
			if (b != -1
				&& containsSimilar(new String(text.substring(b)), phrase, similarity,
					matchExpected)) return true;
			int a = text.indexOf(" ");
			if (a != -1
				&& containsSimilar(new String(text.substring(0, a)), phrase,
					similarity, matchExpected)) return true;
		}
		return false;
	}

	public static boolean contains_SimilarPhraseEndingWith(String text,
		String phrase, double similarity, boolean matchExpected)
	{
		if (containsSimilar(text, phrase + " ", similarity, matchExpected)) return true;
		else if (text.length() > phrase.length()
			&& matchesExactlySimilar(
				new String(text.substring(text.length() - phrase.length(),
					text.length())), phrase, similarity, matchExpected)) return true;
		else return matchesExactlySimilar(text, phrase, similarity, matchExpected);
	}

	public static boolean contains_SimilarPhraseStartingWith(String text,
		String phrase, double similarity, boolean matchExpected)
	{
		if (containsSimilar(text, " " + phrase, similarity, matchExpected)) return true;
		else return (matchesExactlySimilar(text, phrase, similarity, matchExpected));
	}

	public static boolean endsWithSimilar(String text, String phrase,
		double similarity, boolean matchExpected)
	{
		if (matchExpected && text.endsWith(phrase)) return true;
		else return text.length() >= phrase.length()
			&& SearchSimilarity.areSimilar(
				new String(text.substring(text.length() - phrase.length())), phrase,
				similarity);
	}

	public static boolean startsWithSimilar(String text, String phrase,
		double similarity, boolean matchExpected)
	{
		if (matchExpected && text.startsWith(phrase)) return true;
		else return text.length() >= phrase.length()
			&& SearchSimilarity.areSimilar(
				new String(text.substring(0, phrase.length())), phrase, similarity);
	}

	public static boolean contains(String text, String phrase)
	{
		return text.contains(phrase);
	}

	public static boolean contains_PhraseExactly(String text, String phrase)
	{
		if (text.equals(phrase)) return true;
		else if (text.contains(" " + phrase + " ")) return true;
		else if (text.endsWith(" " + phrase)) return true;
		else if (text.startsWith(phrase + " ")) return true;
		else return false;
	}

	public static boolean contains_PhraseStartingWith(String text, String phrase)
	{
		if (text.contains(" " + phrase)) return true;
		else if (text.length() >= phrase.length())
		{
			if (new String(text.substring(0, phrase.length())).equals(phrase)) return true;
			else return false;
		}
		else return false;
	}

	public static boolean contains_PhraseEndingWith(String text, String phrase)
	{
		if (text.contains(phrase + " ")) return true;
		else if (text.length() > phrase.length())
		{
			if (new String(text.substring(text.length() - phrase.length(),
				text.length())).equals(phrase)) return true;
			else if (new String(text.substring(text.length() - phrase.length() - 1,
				text.length() - 1)).equals(phrase)) return true;
		}
		else if (text.equals(phrase)) return true;
		return false;
	}

	public static boolean endsWith(String text, String phrase)
	{
		return text.endsWith(phrase);
	}

	public static boolean matchesExactly(String text, String phrase)
	{
		return text.equals(phrase);
	}

	public static boolean search(String text, String phrase,
		SearchModifier modifier)
	{
		return search(text, phrase, modifier, SearchScope.NO_LIMITATIONS, true,
			null, SearchSimilarity.SIMILAR, false);
	}

	public static boolean search(String text, String phrase,
		SearchModifier modifier, boolean caseSensitive)
	{
		return search(text, phrase, modifier, SearchScope.NO_LIMITATIONS,
			caseSensitive, null, SearchSimilarity.SIMILAR, false);
	}

	public static boolean search(String text, String phrase,
		SearchModifier modifier, boolean caseSensitive, Locale locale)
	{
		return search(text, phrase, modifier, SearchScope.NO_LIMITATIONS,
			caseSensitive, locale, SearchSimilarity.SIMILAR, false);
	}

	public static boolean search(String text, String phrase,
		SearchModifier modifier, SearchScope scope)
	{
		return search(text, phrase, modifier, scope, true, null,
			SearchSimilarity.SIMILAR, false);
	}

	public static boolean search(String text, String phrase,
		SearchModifier modifier, SearchScope scope, boolean caseSensitive)
	{
		return search(text, phrase, modifier, scope, caseSensitive, null,
			SearchSimilarity.SIMILAR, false);
	}

	public static boolean search(String text, String phrase,
		SearchModifier modifier, SearchScope scope, boolean caseSensitive,
		Locale locale, double similarity, boolean matchExpected)
	{
		if (scope == SearchScope.ASCII_ONLY)
		{
			text = StringEssentials.toASCII(text);
			phrase = StringEssentials.toASCII(phrase);
		}
		else if (scope == SearchScope.NO_ACCENTS_AND_DIACRITICS)
		{
			text = StringEssentials.removeDiacritics(text);
			phrase = StringEssentials.removeDiacritics(phrase);
		}
		else if (scope == SearchScope.NO_LIMITATIONS)
		{
		}
		if (!caseSensitive)
		{
			if (locale == null)
			{
				locale = LocaleEssentials.LOCALE_DEFAULT;
			}
			text = text.toLowerCase(locale);
			phrase = phrase.toLowerCase(locale);
		}
		if (modifier == SearchModifier.CONTAINS) return contains(text, phrase);
		else if (modifier == SearchModifier.CONTAINS_PHRASE_EXACTLY) return contains_PhraseExactly(
			text, phrase);
		else if (modifier == SearchModifier.CONTAINS_PHRASE_ENDING_WITH) return contains_PhraseEndingWith(
			text, phrase);
		else if (modifier == SearchModifier.CONTAINS_PHRASE_STARTING_WITH) return contains_PhraseStartingWith(
			text, phrase);
		else if (modifier == SearchModifier.STARTS_WITH) return startsWith(text,
			phrase);
		else if (modifier == SearchModifier.EXACT) return matchesExactly(text,
			phrase);
		else if (modifier == SearchModifier.ENDS_WITH) return endsWith(text, phrase);
		else if (modifier == SearchModifier.CONTAINS_SIMILAR) return containsSimilar(
			text, phrase, similarity, matchExpected);
		else if (modifier == SearchModifier.CONTAINS_SIMILAR_PHRASE_EXACTLY) return contains_SimilarPhraseExactly(
			text, phrase, similarity, matchExpected);
		else if (modifier == SearchModifier.CONTAINS_SIMILAR_PHRASE_ENDING_WITH) return contains_SimilarPhraseEndingWith(
			text, phrase, similarity, matchExpected);
		else if (modifier == SearchModifier.CONTAINS_SIMILAR_PHRASE_STARTING_WITH) return contains_SimilarPhraseStartingWith(
			text, phrase, similarity, matchExpected);
		else if (modifier == SearchModifier.STARTS_WITH_SIMILAR) return startsWithSimilar(
			text, phrase, similarity, matchExpected);
		else if (modifier == SearchModifier.EXACT_SIMILAR) return matchesExactlySimilar(
			text, phrase, similarity, matchExpected);
		else if (modifier == SearchModifier.ENDS_WITH_SIMILAR) return endsWithSimilar(
			text, phrase, similarity, matchExpected);
		else return false;
	}

	public static boolean[] search(String[] text, String phrase,
		SearchModifier modifier)
	{
		return search(text, phrase, modifier, SearchScope.NO_LIMITATIONS, true,
			null, SearchSimilarity.SIMILAR, false);
	}

	public static boolean[] search(String[] text, String phrase,
		SearchModifier modifier, boolean caseSensitive)
	{
		return search(text, phrase, modifier, SearchScope.NO_LIMITATIONS,
			caseSensitive, null, SearchSimilarity.SIMILAR, false);
	}

	public static boolean[] search(String[] text, String phrase,
		SearchModifier modifier, boolean caseSensitive, Locale locale)
	{
		return search(text, phrase, modifier, SearchScope.NO_LIMITATIONS,
			caseSensitive, locale, SearchSimilarity.SIMILAR, false);
	}

	public static boolean[] search(String[] text, String phrase,
		SearchModifier modifier, SearchScope scope)
	{
		return search(text, phrase, modifier, scope, true, null,
			SearchSimilarity.SIMILAR, false);
	}

	public static boolean[] search(String[] text, String phrase,
		SearchModifier modifier, SearchScope scope, boolean caseSensitive)
	{
		return search(text, phrase, modifier, scope, caseSensitive, null,
			SearchSimilarity.SIMILAR, false);
	}

	public static boolean[] search(String[] text, String phrase,
		SearchModifier modifier, SearchScope scope, boolean caseSensitive,
		Locale locale, double similarity, boolean matchExpected)
	{
		boolean[] result = new boolean[text.length];
		for (int i = 0; i < text.length; i++)
		{
			result[i] = search(text[i], phrase, modifier, scope, caseSensitive,
				locale, similarity, matchExpected);
		}
		return result;
	}

	public static boolean startsWith(String text, String phrase)
	{
		return text.startsWith(phrase);
	}
}
