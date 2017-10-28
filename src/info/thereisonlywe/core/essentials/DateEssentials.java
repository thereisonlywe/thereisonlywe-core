package info.thereisonlywe.core.essentials;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.regex.Pattern;

public class DateEssentials
{
	public enum DateFormatOrder
	{
		DD_MM_YY, MM_DD_YY, YY_MM_DD, YY_DD_MM;
	}

	public static boolean isLeapYear(int year)
	{
		if (year % 4 != 0)
		{
			return false;
		}
		else if (year % 400 == 0)
		{
			return true;
		}
		else if (year % 100 == 0)
		{
			return false;
		}
		else
		{
			return true;
		}
	}

	public static String getDate(long millis, String dateSeparator)
	{
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(millis);
		return getDate(calendar, dateSeparator);
	}

	public static String getDate(Calendar c, String dateSeparator)
	{
		return getFormattedDate(c.get(Calendar.DAY_OF_MONTH),
			c.get(Calendar.MONTH) + 1, c.get(Calendar.YEAR),
			DateFormatOrder.DD_MM_YY, "/");
	}

	public static boolean isDateInRange(final String formattedDateInQuestion,
		final String formattedStartDate, final String formattedEndDate,
		final DateFormatOrder dfo)
	{
		if (!getRelationTo(formattedStartDate, formattedDateInQuestion, dfo)
			.equals(LanguageEssentials.Math.LESS)
			&& !getRelationTo(formattedEndDate, formattedDateInQuestion, dfo).equals(
				LanguageEssentials.Math.GREATER)) return true;
		return false;
	}

	public static boolean isDateInRange(final Date dateInQuestion,
		final Date startDate, final Date endDate)
	{
		if (!getRelationTo(startDate, dateInQuestion).equals(
			LanguageEssentials.Math.LESS)
			&& !getRelationTo(endDate, dateInQuestion).equals(
				LanguageEssentials.Math.GREATER)) return true;
		return false;
	}

	public static String getRelationTo(final String thisFormattedDate,
		final String formattedDateInQuestion, DateFormatOrder dfo)
	{
		return getRelationTo(toDate(thisFormattedDate, dfo),
			toDate(formattedDateInQuestion, dfo));
	}

	public static String getRelationTo(final Date thisFormattedDate,
		final Date formattedDateInQuestion)
	{
		if (thisFormattedDate == null || formattedDateInQuestion == null) return null;
		else if (formattedDateInQuestion.after(thisFormattedDate)) return LanguageEssentials.Math.GREATER;
		else if (formattedDateInQuestion.before(thisFormattedDate)) return LanguageEssentials.Math.LESS;
		else return LanguageEssentials.Math.EQUALS;
	}

	public static int getDifferenceInDays(Date d1, Date d2)
	{
		return (int) ((d1.getTime() - d2.getTime()) / (1000 * 60 * 60 * 24));
	}

	public static boolean isValidDate(String formattedDate,
		final DateFormatOrder dfo)
	{
		return toDate(formattedDate, dfo) != null;
	}

	public static Date toDate(final String formattedDate,
		final DateFormatOrder dfo)
	{
		String separator = getDateSeparator(formattedDate);
		Date d = null;
		try
		{
			if (dfo == DateFormatOrder.DD_MM_YY) d = new SimpleDateFormat("dd"
				+ separator + "MM" + separator + "yyyy").parse(formattedDate);
			else if (dfo == DateFormatOrder.MM_DD_YY) d = new SimpleDateFormat("MM"
				+ separator + "dd" + separator + "yyyy").parse(formattedDate);
			else if (dfo == DateFormatOrder.YY_DD_MM) d = new SimpleDateFormat("yyyy"
				+ separator + "dd" + separator + "MM").parse(formattedDate);
			else // (dfo == DateFormatOrder.YY_MM_DD)
			d = new SimpleDateFormat("yyyy" + separator + "MM" + separator + "dd")
				.parse(formattedDate);
		}
		catch (Exception e)
		{
		}
		return d;
	}

	public static String getFormattedDate(String dayOfMonth, String monthOfYear,
		String Year, DateFormatOrder dfo, String separator)
	{
		return getFormattedDate(Integer.parseInt(dayOfMonth),
			Integer.parseInt(monthOfYear), Integer.parseInt(Year), dfo, separator);
	}

	public static String getFormattedDate(int dayOfMonth, int monthOfYear,
		int Year, DateFormatOrder dfo, String separator)
	{
		if (dfo == DateFormatOrder.DD_MM_YY)
		{
			return StringEssentials.addPaddingToNumber(dayOfMonth, 2) + separator
				+ StringEssentials.addPaddingToNumber(monthOfYear, 2) + separator
				+ StringEssentials.addPaddingToNumber(Year, 2);
		}
		else if (dfo == DateFormatOrder.MM_DD_YY)
		{
			return StringEssentials.addPaddingToNumber(monthOfYear, 2) + separator
				+ StringEssentials.addPaddingToNumber(dayOfMonth, 2) + separator
				+ StringEssentials.addPaddingToNumber(Year, 2);
		}
		else if (dfo == DateFormatOrder.YY_MM_DD)
		{
			return StringEssentials.addPaddingToNumber(Year, 2) + separator
				+ StringEssentials.addPaddingToNumber(monthOfYear, 2) + separator
				+ StringEssentials.addPaddingToNumber(dayOfMonth, 2);
		}
		else // YY_DD_MM
		return StringEssentials.addPaddingToNumber(Year, 2) + separator
			+ StringEssentials.addPaddingToNumber(dayOfMonth, 2) + separator
			+ StringEssentials.addPaddingToNumber(monthOfYear, 2);
	}

	public static int getYear(String formattedDate, DateFormatOrder dfo,
		String separator)
	{
		String[] vals = formattedDate.split(Pattern.quote(separator));
		return getYear(vals, dfo);
	}

	public static int getYear(String[] vals, DateFormatOrder dfo)
	{
		if (dfo == DateFormatOrder.DD_MM_YY || dfo == DateFormatOrder.MM_DD_YY) return Integer
			.parseInt(vals[2]);
		else return Integer.parseInt(vals[0]);
	}

	@Deprecated
	public static int getYear(String formattedDate, DateFormatOrder dfo)
	{
		return getYear(formattedDate, dfo, getDateSeparator(formattedDate));
	}

	public static int getMonth(String formattedDate, DateFormatOrder dfo,
		String separator)
	{
		String[] vals = formattedDate.split(Pattern.quote(separator));
		return getMonth(vals, dfo);
	}

	public static int getMonth(String[] vals, DateFormatOrder dfo)
	{
		if (dfo == DateFormatOrder.DD_MM_YY || dfo == DateFormatOrder.YY_MM_DD) return Integer
			.parseInt(vals[1]);
		else if (dfo == DateFormatOrder.MM_DD_YY) return Integer.parseInt(vals[0]);
		else // YY_DD_MM
		return Integer.parseInt(vals[2]);
	}

	@Deprecated
	public static int getMonth(String formattedDate, DateFormatOrder dfo)
	{
		return getMonth(formattedDate, dfo, getDateSeparator(formattedDate));
	}

	@Deprecated
	public static String[] getDateParts(String formattedDate)
	{
		return getDateParts(formattedDate, getDateSeparator(formattedDate));
	}

	public static String[] getDateParts(String formattedDate, String separator)
	{
		return formattedDate.split(Pattern.quote(separator));
	}

	public static int[] getDatePartsAsInt(String formattedDate, String separator)
	{
		int[] parts = new int[3];
		String[] p = formattedDate.split(Pattern.quote(separator));
		parts[0] = Integer.parseInt(p[0]);
		parts[1] = Integer.parseInt(p[1]);
		parts[2] = Integer.parseInt(p[2]);
		return parts;
	}

	public static int getDay(String formattedDate, DateFormatOrder dfo,
		String separator)
	{
		String[] vals = formattedDate.split(Pattern.quote(separator));
		return getDay(vals, dfo);
	}

	public static int getDay(String[] vals, DateFormatOrder dfo)
	{
		if (dfo == DateFormatOrder.DD_MM_YY) return Integer.parseInt(vals[0]);
		else if (dfo == DateFormatOrder.MM_DD_YY || dfo == DateFormatOrder.YY_DD_MM) return Integer
			.parseInt(vals[1]);
		else // YY_MM_DD
		return Integer.parseInt(vals[2]);
	}

	@Deprecated
	public static int getDay(String formattedDate, DateFormatOrder dfo)
	{
		return getDay(formattedDate, dfo, getDateSeparator(formattedDate));
	}

	public static String getDateSeparator(String date)
	{
		String[] vals = null;
		vals = date.split(Pattern.quote(LanguageEssentials.Character.UNDERSCORE));
		if (vals.length != 3)
		{
			vals = date
				.split(Pattern.quote(LanguageEssentials.Character.DOT_DECIMAL));
			if (vals.length != 3)
			{
				vals = date.split(Pattern.quote(" "));
				if (vals.length != 3)
				{
					vals = date.split(Pattern
						.quote(LanguageEssentials.Character.SCORE_MINUS));
					if (vals.length != 3)
					{
						vals = date.split(Pattern.quote(LanguageEssentials.Character.DASH));
						if (vals.length != 3)
						{
							vals = date.split(Pattern
								.quote(LanguageEssentials.Character.SLASH));
							if (vals.length != 3)
							{
								vals = date.split(Pattern
									.quote(LanguageEssentials.Character.BACKSLASH));
								if (vals.length != 3) return null;
								else return LanguageEssentials.Character.BACKSLASH;
							}
							else return LanguageEssentials.Character.SLASH;
						}
						else return LanguageEssentials.Character.DASH;
					}
					else return LanguageEssentials.Character.SCORE_MINUS;
				}
				else return " ";
			}
			else return LanguageEssentials.Character.DOT_DECIMAL;
		}
		else return LanguageEssentials.Character.UNDERSCORE;
	}

	public static String correctFormatting(String date, DateFormatOrder dfo)
	{
		return correctFormatting(date, dfo, getDateSeparator(date));
	}

	public static String correctFormatting(String date, DateFormatOrder dfo,
		String separator)
	{
		String[] vals = date.split(Pattern.quote(separator));
		if (vals.length != 3)
		{
			String ds = getDateSeparator(date);
			if (ds == null) return null;
			else vals = date.split(Pattern.quote(ds));
		}
		if (vals.length != 3) return null;
		else
		{
			for (int i = 0; i < vals.length; i++)
			{
				vals[i] = StringEssentials.removeNonNumbers(vals[i]).trim();
			}
			int a = getDay(vals, dfo);
			int b = getMonth(vals, dfo);
			int c = getYear(vals, dfo);
			int temp;
			if (b > 12)
			{
				temp = b;
				if (a <= 12)
				{
					b = a;
					a = temp;
				}
				else
				{
					b = c;
					c = temp;
				}
			}
			if (a > 31)
			{
				temp = a;
				if (c <= 31)
				{
					a = c;
					c = temp;
				}
				else
				{
					a = b;
					b = temp;
				}
			}
			return getFormattedDate(a, b, c, dfo, separator);
		}
	}

	public static String toFormattedMonth(String monthName)
	{
		return toFormattedMonth(monthName, LocaleEssentials.LOCALE_DEFAULT);
	}

	public static String toFormattedMonth(String monthName, Locale locale)
	{
		SimpleDateFormat inputFormat = new SimpleDateFormat("MMMM", locale);
		Calendar cal = Calendar.getInstance();
		try
		{
			cal.setTime(inputFormat.parse(monthName));
			SimpleDateFormat outputFormat = new SimpleDateFormat("MM", locale); // 01-12
			return outputFormat.format(cal.getTime());
		}
		catch (ParseException e)
		{
		}
		return null;
	}
}
