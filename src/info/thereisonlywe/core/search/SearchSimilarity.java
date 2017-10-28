package info.thereisonlywe.core.search;

public class SearchSimilarity
{
	public static double SIMILAR = 0.70;
	public static double HIGHLY_SIMILAR = 0.775;
	public static double EXCEPTIONALLY_SIMILAR = 0.85;
	public static double ALMOST_IDENTICAL = 0.925;
	public static double IDENTICAL = 0.99;
	public static double SAME = 1.0;

	public static boolean areSimilar(String s1, String s2, double percent)
	{
		return calculate(s1, s2) >= computeWeightedPercentage(s1, s2, percent);
	}

	public static double calculate(String s1, String s2)
	{
		if (s1 == null || s2 == null) return 0.0;
		else if (s1.equals(s2)) return 1.0;
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

	private static double computeWeightedPercentage(String phrase,
		String phrase2, double rawPercent)
	{
		if (phrase == null || phrase2 == null) return 1.0;
		else
		{
			int len = Math.min(phrase.length(), phrase2.length());
			if (len <= 2) return 1.0;
			else if (len == 3) return Math.min(1.0, rawPercent + 0.1);
			else if (len == 4) return Math.min(1.0, rawPercent + 0.05);
			else if (len == 5) return Math.min(1.0, rawPercent + 0.025);
			else return rawPercent;
		}
	}

	private static int computeEditDistance(String s1, String s2)
	{
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
}
