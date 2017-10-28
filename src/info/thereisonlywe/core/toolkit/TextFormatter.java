package info.thereisonlywe.core.toolkit;

import info.thereisonlywe.core.essentials.LocaleEssentials;
import info.thereisonlywe.core.essentials.MathEssentials;
import info.thereisonlywe.core.essentials.StringEssentials;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ahmadnoyan http://www.thereisonlywe.info
 */
public class TextFormatter
{
	public static class TextFormatSpecs
	{
		boolean fix;
		boolean encloseTitle = false;
		boolean indentParagraphs = false;
		boolean removeDiacricitics = false;
		Locale locale = LocaleEssentials.LOCALE_DEFAULT;
		char encloseBeginChar = '(';
		char encloseEndChar = ')';
		String indent = "\t";
		int maxAllowedConsecutiveLetters = 2;

		public TextFormatSpecs(boolean fix, boolean indentParagraphs,
			boolean encloseTitle, boolean removeDiacritics, Locale locale)
		{
			this.fix = fix;
			this.encloseTitle = encloseTitle;
			this.indentParagraphs = indentParagraphs;
			this.removeDiacricitics = removeDiacritics;
			if (locale != null) this.locale = locale;
		}

		public TextFormatSpecs()
		{
			fix = true;
		}

		public void setEncloseChars(char begin, char end)
		{
			encloseBeginChar = begin;
			encloseEndChar = end;
		}

		public void setIndent(String indent)
		{
			this.indent = indent;
		}
	}

	public static String fix(String text)
	{
		return format(text, null);
	}

	public static String format(String text, TextFormatSpecs specs)
	{
		if (specs == null) specs = new TextFormatSpecs();
		if (specs.fix) text = StringEssentials
			.removeConsecutiveLetters(StringEssentials.replaceWeirdBlanks(text),
				specs.maxAllowedConsecutiveLetters).replace("         ", " ")
			.replace("       ", " ").replace("     ", " ").replace("   ", " ")
			.replace("  ", " ").replace("\" \" \"", "\"").replace("\" \"", "\"")
			.replace("\"\"", "\"").replace(", , ,", ",").replace(",,,", ",")
			.replace(", ,", ",").replace(",,", ",").replace(". . .", ".")
			.replace("...", ".").replace(". .", ".").replace("..", ".")
			.replace("; ; ;", ";").replace(";;;", ";").replace("; ;", ";")
			.replace(";;", ";").replace(": : :", ":").replace(":::", ":")
			.replace(": :", ":").replace("::", ":").replace("! ! !", "!")
			.replace("!!!", "!").replace("! !", "!").replace("!!", "!")
			.replace("? ? ?", "?").replace("???", "?").replace("? ?", "?")
			.replace("??", "?").replace("' ' '", "'").replace("'''", "'")
			.replace("' '", "'").replace("''", "'").replace("“ “ “", "“")
			.replace("“““", "“").replace("“ “", "“").replace("““", "“")
			.replace("” ” ”", "”").replace("”””", "”").replace("” ”", "”")
			.replace("””", "”").replace("« « «", "«").replace("«««", "«")
			.replace("« «", "«").replace("««", "«").replace("» » »", "»")
			.replace("»»»", "»").replace("» »", "»").replace("»»", "»")
			.replace("‘ ‘ ‘", "‘").replace("‘‘‘", "‘").replace("‘ ‘", "‘")
			.replace("‘‘", "‘").replace("’ ’ ’", "’").replace("’’’", "’")
			.replace("’ ’", "’").replace("’’", "’").replace("´ ´ ´", "´")
			.replace("´´´", "´").replace("´ ´", "´").replace("´´", "´")
			.replaceAll("(\r?\n){3,}", "\n\n");
		StringBuilder sb;
		if (specs.fix)
		{
			sb = new StringBuilder();
			sb.ensureCapacity(text.length());
			// boolean before_1 = true, before_2 = true, before_3 = true;
			for (int i = 0; i < text.length(); i++)
			{
				String c = String.valueOf(text.charAt(i));
				if (c.equals(",") || c.equals(".") || c.equals("?") || c.equals("!")
					|| c.equals(":") || c.equals(";") || c.equals("“") || c.equals("”")
					|| c.equals("«") || c.equals("»") || c.equals("‘") || c.equals("’")
					|| c.equals("'") || c.equals("´"))
				{
					int p = 0;
					// if(c.equals("\""))
					// {
					// p = before_1 ? -1 : 1;
					// before_1 = !before_1;
					// }
					// if(c.equals("'"))
					// {
					// p = before_2 ? -1 : 1;
					// before_2 = !before_2;
					// }
					// if(c.equals("´"))
					// {
					// p = before_3 ? -1 : 1;
					// before_3 = !before_3;
					// }
					if (i > 0
						&& (c.equals(",") || c.equals(".") || c.equals("?")
							|| c.equals("!") || c.equals(":") || c.equals(";")
							|| c.equals("”") || c.equals("’") || c.equals("»") || p == 1))
					{
						String before = String.valueOf(text.charAt(i - 1));
						if (before.equals(" "))
						{
							sb.deleteCharAt(sb.length() - 1);
						}
					}
					if (i + 1 < text.length())
					{
						String after = String.valueOf(text.charAt(i + 1));
						if ((c.equals("“") || c.equals("‘") || c.equals("«") || p == -1)
							&& after.equals(" "))
						{
							i++;
						}
						else if ((c.equals(",") || c.equals(".") || c.equals("?")
							|| c.equals("!") || c.equals(":") || c.equals(";"))
							&& !after.equals(" "))
						{
							char c_ = after.charAt(0);
							if (!c.equals(".")
								|| (!Character.isDigit(c_) && !(c_ == '\"' || c_ == '“'
									|| c_ == '‘' || c_ == '«' || c_ == '\'' || c_ == '´')))
							{
								c = c + " ";
							}
						}
					}
				}
				else if (c.equals("\""))
				{
					if (i + 1 < text.length())
					{
						if (i > 0)
						{
							if (sb.charAt(sb.length() - 1) == ' ')
							{
								c = c.concat(String.valueOf(text.charAt(i + 1)).toUpperCase(
									specs.locale));
								i++;
							}
							else if (sb.charAt(sb.length() - 1) == '.')
							{
								String t = String.valueOf(text.charAt(i + 1)).toUpperCase(
									specs.locale);
								if (!t.equals(" "))
								{
									c = c.concat(" ").concat(
										String.valueOf(text.charAt(i + 1))
											.toUpperCase(specs.locale));
									i += 1;
								}
							}
						}
					}
				}
				sb.append(c);
			}
			text = sb.toString();
		}
		Matcher matcher = null;
		Pattern p1 = null;
		Pattern p2 = null;
		if (specs.fix)
		{
			p1 = Pattern.compile("(\"[^\"\\\\]*(?:\\\\.[^\"\\\\]*)*\")");
			p2 = Pattern.compile("(\'[^\'\\\\]*(?:\\\\.[^\'\\\\]*)*\')");
		}
		String[] lines = StringEssentials.splitLines(text);
		for (int i = 0; i < lines.length; i++)
		{
			if (specs.removeDiacricitics) lines[i] = StringEssentials
				.removeDiacritics(lines[i]);
			if (specs.fix) lines[i] = lines[i].trim().replace("         ", " ")
				.replace("       ", " ").replace("     ", " ").replace("   ", " ")
				.replace("  ", " ");
			if (lines[i].equals("")) continue;
			if (specs.fix)
			{
				// if(specs.fixSentenceCase)
				sb = new StringBuilder();
				String tmp = String.valueOf(lines[i].charAt(0));
				if (StringEssentials.isLowerCase(tmp)) sb.append(tmp
					.toUpperCase(specs.locale));
				else sb.append(tmp);
				for (int j = 1; j < lines[i].length(); j++)
				{
					char c = lines[i].charAt(j);
					if ((c == '.' || c == ':' || c == '?' || c == '!')
						&& (j + 1 < lines[i].length()))
					{
						char n = lines[i].charAt(j + 1);
						if (n != ' ')
						{
							sb.append(c);
							sb.append(String.valueOf(n).toUpperCase(specs.locale));
							j++;
						}
						else if (j + 2 < lines[i].length())
						{
							sb.append(c);
							sb.append(n);
							String s = String.valueOf(lines[i].charAt(j + 2));
							String s_ = s.toUpperCase(specs.locale);
							sb.append(s_);
							if (s.equals(s_))
							{
								if (j + 3 < lines[i].length())
								{
									String s2 = String.valueOf(lines[i].charAt(j + 3));
									String s2_ = s2.toUpperCase(specs.locale);
									sb.append(s2_);
									if (!s2.equals(s2_))
									{
										j += 3;
									}
									else if (j + 4 < lines[i].length())
									{
										String s3 = String.valueOf(lines[i].charAt(j + 4));
										String s3_ = s3.toUpperCase(specs.locale);
										sb.append(s3_);
										j += 4;
									}
									else j += 3;
								}
								else j += 2;
							}
							else j += 2;
						}
					}
					else sb.append(c);
				}
				lines[i] = sb.toString();
			}
			if (specs.fix)
			{
				// if(specs.fixWordCase)
				String[] words = StringEssentials.splitWords(lines[i]);
				for (int k = 0; k < words.length; k++)
				{
					String temp[] = words[k].split("-");
					if (temp.length > 0)
					{
						for (int m = 0; m < temp.length; m++)
						{
							if (temp[m].length() > 1)
							{
								int index = 0;
								char c = temp[m].charAt(0);
								if (c == '\"' || c == '“' || c == '‘' || c == '«' || c == '\''
									|| c == '´') index++;
								temp[m] = new String(temp[m].substring(0, index)).concat(
									new String(temp[m].substring(index, index + 1))).concat(
									new String(temp[m].substring(index + 1))
										.toLowerCase(specs.locale));
							}
						}
						words[k] = StringEssentials.glue(temp, "-");
					}
					else
					{
						if (words[k].length() > 1)
						{
							int index = 0;
							char c = words[k].charAt(0);
							if (c == '\"' || c == '“' || c == '‘' || c == '«' || c == '\''
								|| c == '´') index++;
							words[k] = new String(words[k].substring(0, index)).concat(
								new String(words[k].substring(index, index + 1))).concat(
								new String(words[k].substring(index + 1).toLowerCase(
									specs.locale)));
						}
					}
				}
				lines[i] = StringEssentials.glue(words, " ");
			}
			if (specs.encloseTitle)
			{
				if (Character.isLetter(lines[i].charAt(lines[i].length() - 1)))
				{
					int a = lines[i].lastIndexOf('.'), b = lines[i].lastIndexOf('?'), c = lines[i]
						.lastIndexOf('!');
					int max = MathEssentials.getMaxValue(new int[] { a, b, c });
					if (max == -1) lines[i] = specs.encloseBeginChar + lines[i]
						+ specs.encloseEndChar;
					else
					{
						if (lines[i].length() <= max + 1 || lines[i].charAt(max + 1) != ' ') lines[i] = new String(
							lines[i].substring(0, max + 1))
							+ specs.encloseBeginChar
							+ new String(lines[i].substring(max + 1)).trim()
							+ specs.encloseEndChar;
						else
						{
							lines[i] = new String(lines[i].substring(0, max + 2))
								+ specs.encloseBeginChar
								+ new String(lines[i].substring(max + 2)).trim()
								+ specs.encloseEndChar;
						}
					}
				}
			}
			if (specs.indentParagraphs)
			{
				lines[i] = StringEssentials.indentParagraph(lines[i], specs.indent);
			}
			if (specs.fix)
			{
				matcher = p1.matcher(lines[i]);
				while (matcher.find())
				{
					lines[i] = lines[i].replace(matcher.group(), matcher.group().trim());
				}
				matcher = p2.matcher(lines[i]);
				while (matcher.find())
				{
					lines[i] = lines[i].replace(matcher.group(), matcher.group().trim());
				}
			}
		}
		text = StringEssentials.glue(lines, "\n");
		return text;
	}
}
