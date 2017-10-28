package info.thereisonlywe.core.toolkit;

import info.thereisonlywe.core.essentials.StringEssentials;

/**
 * 
 * @author thereisonlywe
 */
public class AbjadCalculator
{
	public enum AbjadSystem
	{
		EASTERN, WESTERN, IBN_ARABI;
	}

	private static AbjadSystem abjadSystem = AbjadSystem.IBN_ARABI;

	public static AbjadSystem getAbjadSystem()
	{
		return abjadSystem;
	}

	public static int getDotlessAbjadValue(final String s)
	{
		return getDotlessAbjadValue(s, abjadSystem);
	}

	public static int getDotlessAbjadValue(String S, AbjadSystem abjadSystem)
	{
		S = StringEssentials.normalizeAleph(S);
		int total = 0;
		for (int i = 0; i < S.length(); i++)
		{
			switch (S.charAt(i))
			{
				case 'ا':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ء':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'د':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ه':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'و':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ح':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ط':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ك':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ل':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'م':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'س':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ع':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ص':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ر':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				default:
					break;
			}
		}
		return total;
	}

	public static int getDotsOnlyAbjadValue(final String s)
	{
		return getDotsOnlyAbjadValue(s, abjadSystem);
	}

	public static int getDotsOnlyAbjadValue(String S, AbjadSystem abjadSystem)
	{
		S = StringEssentials.normalizeAleph(S);
		int total = 0;
		for (int i = 0; i < S.length(); i++)
		{
			switch (S.charAt(i))
			{
				case 'ب':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ة':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ج':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ز':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ى':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ي':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ن':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ف':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ق':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ش':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ت':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ث':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'خ':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ذ':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ض':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'ظ':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				case 'غ':
					total += getTrueAbjadValue(S.charAt(i) + "", abjadSystem);
					break;
				default:
					break;
			}
		}
		return total;
	}

	public static int getLiteralAbjadValue(final String s)
	{
		return getLiteralAbjadValue(s, abjadSystem);
	}

	public static int getLiteralAbjadValue(String s, AbjadSystem abjadSystem)
	{
		s = StringEssentials.normalizeAleph(s);
		int total = 0;
		final String S = StringEssentials.removeNonTextCharacters(s);
		for (int i = 0; i < S.length(); i++)
		{
			switch (S.charAt(i))
			{
				case 'ّ':
					total += getLiteralAbjadValue(S.charAt(i - 1) + "", abjadSystem);
					break;
				case 'ا':
					total += getTrueAbjadValue("ألف", abjadSystem);
					break;
				case 'ء':
					total += getTrueAbjadValue("همز", abjadSystem);
					break;
				case 'ب':
					total += getTrueAbjadValue("باء", abjadSystem);
					break;
				case 'ج':
					total += getTrueAbjadValue("جيم", abjadSystem);
					break;
				case 'د':
					total += getTrueAbjadValue("دال", abjadSystem);
					break;
				case 'ه':
					total += getTrueAbjadValue("هاء", abjadSystem);
					break;
				case 'ة':
					total += getTrueAbjadValue("هاء", abjadSystem);
					break;
				case 'و':
					total += getTrueAbjadValue("واو", abjadSystem);
					break;
				case 'ز':
					total += getTrueAbjadValue("ذال", abjadSystem);
					break;
				case 'ح':
					total += getTrueAbjadValue("حاء", abjadSystem);
					break;
				case 'ط':
					total += getTrueAbjadValue("طاء", abjadSystem);
					break;
				case 'ى':
					total += getTrueAbjadValue("ياء", abjadSystem);
					break;
				case 'ي':
					total += getTrueAbjadValue("ياء", abjadSystem);
					break;
				case 'ك':
					total += getTrueAbjadValue("كاف", abjadSystem);
					break;
				case 'ل':
					total += getTrueAbjadValue("لام", abjadSystem);
					break;
				case 'م':
					total += getTrueAbjadValue("ميم", abjadSystem);
					break;
				case 'ن':
					total += getTrueAbjadValue("نون", abjadSystem);
					break;
				case 'س':
					total += getTrueAbjadValue("سين", abjadSystem);
					break;
				case 'ع':
					total += getTrueAbjadValue("عين", abjadSystem);
					break;
				case 'ف':
					total += getTrueAbjadValue("فاء", abjadSystem);
					break;
				case 'ص':
					total += getTrueAbjadValue("صاد", abjadSystem);
					break;
				case 'ق':
					total += getTrueAbjadValue("قاف", abjadSystem);
					break;
				case 'ر':
					total += getTrueAbjadValue("راء", abjadSystem);
					break;
				case 'ش':
					total += getTrueAbjadValue("شين", abjadSystem);
					break;
				case 'ت':
					total += getTrueAbjadValue("تاء", abjadSystem);
					break;
				case 'ث':
					total += getTrueAbjadValue("ثاء", abjadSystem);
					break;
				case 'خ':
					total += getTrueAbjadValue("خاء", abjadSystem);
					break;
				case 'ذ':
					total += getTrueAbjadValue("زاي", abjadSystem);
					break;
				case 'ض':
					total += getTrueAbjadValue("ضاد", abjadSystem);
					break;
				case 'ظ':
					total += getTrueAbjadValue("ظاء", abjadSystem);
					break;
				case 'غ':
					total += getTrueAbjadValue("غين", abjadSystem);
					break;
				default:
					break;
			}
		}
		return total;
	}

	public static int getMod12AbjadValue(final String s)
	{
		return getMod12AbjadValue(s, abjadSystem);
	}

	public static int getMod12AbjadValue(String S, AbjadSystem abjadSystem)
	{
		S = StringEssentials.normalizeAleph(S);
		int total = 0;
		for (int i = 0; i < S.length(); i++)
		{
			total += getTrueAbjadValue(new String(S.substring(i, i + 1)), abjadSystem) % 12;
		}
		return total;
	}

	public static int getSingleDigitAbjadValue(final String s)
	{
		return getSingleDigitAbjadValue(s, abjadSystem);
	}

	public static int getSingleDigitAbjadValue(String S, AbjadSystem abjadSystem)
	{
		S = StringEssentials.normalizeAleph(S);
		int total = 0;
		for (int i = 0; i < S.length(); i++)
		{
			int temp = getTrueAbjadValue(new String(S.substring(i, i + 1)),
				abjadSystem);
			while (temp >= 10)
			{
				temp /= 10;
			}
			total += temp;
		}
		return total;
	}

	public static int getTrueAbjadValue(final String s)
	{
		return getTrueAbjadValue(s, abjadSystem);
	}

	public static int getTrueAbjadValue(String S, AbjadSystem abjadSystem)
	{
		S = StringEssentials.normalizeAleph(S);
		int total = 0;
		for (int i = 0; i < S.length(); i++)
		{
			switch (S.charAt(i))
			{
				case 'ا':
					total += 1;
					break;
				case 'ء':
					total += 1;
					break;
				case 'ب':
					total += 2;
					break;
				case 'ج':
					total += 3;
					break;
				case 'د':
					total += 4;
					break;
				case 'ه':
					total += 5;
					break;
				case 'ة':
					total += 5;
					break;
				case 'و':
					total += 6;
					break;
				case 'ز':
					total += 7;
					break;
				case 'ح':
					total += 8;
					break;
				case 'ط':
					total += 9;
					break;
				case 'ى':
					total += 10;
					break;
				case 'ي':
					total += 10;
					break;
				case 'ك':
					total += 20;
					break;
				case 'ل':
					total += 30;
					break;
				case 'م':
					total += 40;
					break;
				case 'ن':
					if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 55;
					}
					else
					{
						total += 50;
					}
					break;
				case 'س':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 60;
					}
					else if (abjadSystem == AbjadSystem.WESTERN)
					{
						total += 300;
					}
					else if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 303;
					}
					break;
				case 'ع':
					total += 70;
					break;
				case 'ف':
					if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 88;
					}
					else
					{
						total += 80;
					}
					break;
				case 'ص':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 90;
					}
					else
					{
						total += 60;
					}
					break;
				case 'ق':
					total += 100;
					break;
				case 'ر':
					total += 200;
					break;
				case 'ش':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 300;
					}
					else
					{
						total += 1000;
					}
					break;
				case 'ت':
					if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 404;
					}
					else
					{
						total += 400;
					}
					break;
				case 'ث':
					if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 505;
					}
					else
					{
						total += 500;
					}
					break;
				case 'خ':
					total += 600;
					break;
				case 'ذ':
					if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 707;
					}
					else
					{
						total += 700;
					}
					break;
				case 'ض':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 800;
					}
					else
					{
						total += 90;
					}
					break;
				case 'ظ':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 900;
					}
					else if (abjadSystem == AbjadSystem.WESTERN)
					{
						total += 800;
					}
					else if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 808;
					}
					break;
				case 'غ':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 1000;
					}
					else
					{
						total += 900;
					}
					break;
				default:
					break;
			}
		}
		return total;
	}

	public static void setAbjadSystem(AbjadSystem abjadSystem)
	{
		AbjadCalculator.abjadSystem = abjadSystem;
	}

	private static int getNumberAbjadValue(final String s)
	{
		return getNumberAbjadValue(s, abjadSystem);
	}

	// TODO: incomplete method
	private static int getNumberAbjadValue(String s, AbjadSystem abjadSystem)
	{
		s = StringEssentials.normalizeAleph(s);
		int total = 0;
		String S = StringEssentials.removeNonTextCharacters(s);
		S = StringEssentials.removeDiacritics(S);
		S = S.replace("ى", "ي");
		for (int i = 0; i < S.length(); i++)
		{
			switch (S.charAt(i))
			{
				case 'ا':
					total += getTrueAbjadValue("واحد", abjadSystem);
					break;
				case 'ء':
					total += getTrueAbjadValue("واحد", abjadSystem);
					break;
				case 'ب':
					total += getTrueAbjadValue("اثنان", abjadSystem);
					break;
				case 'ج':
					total += getTrueAbjadValue("ثلاثة", abjadSystem);
					break;
				case 'د':
					total += getTrueAbjadValue("أربعة", abjadSystem);
					break;
				case 'ه':
					total += getTrueAbjadValue("خمسة", abjadSystem);
					break;
				case 'ة':
					total += getTrueAbjadValue("خمسة", abjadSystem);
					break;
				case 'و':
					total += getTrueAbjadValue("ستة", abjadSystem);
					break;
				case 'ز':
					total += getTrueAbjadValue("سبعة", abjadSystem);
					break;
				case 'ح':
					total += getTrueAbjadValue("ثمانية", abjadSystem);
					break;
				case 'ط':
					total += getTrueAbjadValue("تسعة", abjadSystem);
					break;
				case 'ى':
					total += getTrueAbjadValue("عشرة", abjadSystem);
					break;
				case 'ي':
					total += getTrueAbjadValue("عشرة", abjadSystem);
					break;
				case 'ك':
					total += getTrueAbjadValue("عشرون", abjadSystem);
					break;
				case 'ل':
					total += getTrueAbjadValue("ثلاثون", abjadSystem);
					break;
				case 'م':
					total += getTrueAbjadValue("أربعون", abjadSystem);
					break;
				case 'ن':
					if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						getTrueAbjadValue("خمس وخمسين", abjadSystem);
					}
					else
					{
						total += getTrueAbjadValue("خمسون", abjadSystem);
					}
					break;
				case 'س':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += getTrueAbjadValue("ستون", abjadSystem);
					}
					else if (abjadSystem == AbjadSystem.WESTERN)
					{
						total += getTrueAbjadValue("ثلاث مئة", abjadSystem);
					}
					else if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 303;
					}
					break;
				case 'ع':
					total += getTrueAbjadValue("سبعون", abjadSystem);
					break;
				case 'ف':
					total += getTrueAbjadValue("ثمانون", abjadSystem);
					break;
				case 'ص':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += getTrueAbjadValue("تسعون", abjadSystem);
					}
					else
					{
						total += getTrueAbjadValue("ستون", abjadSystem);
					}
					break;
				case 'ق':
					total += 100;
					break;
				case 'ر':
					total += 200;
					break;
				case 'ش':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 300;
					}
					else
					{
						total += 1000;
					}
					break;
				case 'ت':
					total += 400;
					break;
				case 'ث':
					total += 500;
					break;
				case 'خ':
					total += 600;
					break;
				case 'ذ':
					total += 700;
					break;
				case 'ض':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 800;
					}
					else
					{
						total += 90;
					}
					break;
				case 'ظ':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += 900;
					}
					else if (abjadSystem == AbjadSystem.WESTERN)
					{
						total += 800;
					}
					else if (abjadSystem == AbjadSystem.IBN_ARABI)
					{
						total += 808;
					}
					break;
				case 'غ':
					if (abjadSystem == AbjadSystem.EASTERN)
					{
						total += getTrueAbjadValue("الف", abjadSystem);
					}
					else
					{
						total += 900;
					}
					break;
				default:
					break;
			}
		}
		return total;
	}
}
