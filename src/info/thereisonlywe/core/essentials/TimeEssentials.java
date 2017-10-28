package info.thereisonlywe.core.essentials;

import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

/**
 * @author ahmadnoyan http://www.thereisonlywe.info
 */
public class TimeEssentials
{
	// MINUTES AS MILLIS
	public static final long MINUTES_1 = 60000L;
	public static final long MINUTES_5 = 300000L;
	public static final long MINUTES_15 = 900000L;
	public static final long MINUTES_30 = 1800000L;
	public static final long MINUTES_45 = 2700000L;
	public static final long MINUTES_60 = 3600000L;
	public static final long DAY = 86400000L;
	public static final long WEEK = 604800000L;
	public static final long MONTH = 2419200000L;

	public static int toSeconds(String formattedTime)
	{
		final int index = formattedTime.indexOf(':');
		int h = Integer.parseInt(new String(formattedTime.substring(0, index)));
		final int Lindex = formattedTime.lastIndexOf(':');
		int m = Integer.parseInt(new String(formattedTime.substring(index + 1,
					index + 3)));
		int s = 0;
		if (formattedTime.length() >= 7)
			Integer.parseInt(new String(formattedTime.substring(Lindex + 1,
					Lindex + 3)));
		return s + (m * 60) + (h * 3600);
	}

	public static double toMinutes(String formattedTime)
	{
		return toSeconds(formattedTime) / 60.0;
	}

	public static double toHours(String formattedTime)
	{
		return toSeconds(formattedTime) / 3600.0;
	}

	public static String to12H(String txt)
	{
		return txt.replace("13:", "01:").replace("14:", "02:")
			.replace("15:", "03:").replace("16:", "04:").replace("17:", "05:")
			.replace("18:", "06:").replace("19:", "07:").replace("20:", "08:")
			.replace("21:", "09:").replace("22:", "10:").replace("23:", "11:")
			.replace("00:", "12:");
	}

	public static double getTimeDifferenceBetweenLongitudesInMinutes(double lon1, double lon2)
	{
		if ((lon1 < 0 && lon2 > 0) ||(lon2 < 0 && lon1 > 0))
			return (Math.abs(lon1) + Math.abs(lon2)) * 4.0;
		else
			return Math.abs(lon1 - lon2) * 4.0;
	}

	public static boolean isTimeInRange(String currentTime,
		String formattedTime1, String formattedTime2)
	{
		boolean result = false;
		try
		{
			if (formattedTime1.lastIndexOf(":") < 5) formattedTime1 += ":00";
			if (formattedTime2.lastIndexOf(":") < 5) formattedTime2 += ":00";
			if (currentTime.lastIndexOf(":") < 5) currentTime += ":00";
			result = isInRange(formattedTime1, formattedTime2, currentTime);
		}
		catch (Exception e)
		{
		}
		return result;
	}

	private static boolean isInRange(String argStartTime, String argEndTime,
		String argCurrentTime) throws ParseException
	{
		String reg = "^([0-1][0-9]|2[0-3]):([0-5][0-9]):([0-5][0-9])$";
		//
		if (argStartTime.matches(reg) && argEndTime.matches(reg)
			&& argCurrentTime.matches(reg))
		{
			boolean valid = false;
			// Start Time
			Date startTime = new SimpleDateFormat("HH:mm:ss")
				.parse(argStartTime);
			Calendar startCalendar = Calendar.getInstance();
			startCalendar.setTime(startTime);
			// Current Time
			Date currentTime = new SimpleDateFormat("HH:mm:ss")
				.parse(argCurrentTime);
			Calendar currentCalendar = Calendar.getInstance();
			currentCalendar.setTime(currentTime);
			// End Time
			Date endTime = new SimpleDateFormat("HH:mm:ss")
				.parse(argEndTime);
			Calendar endCalendar = Calendar.getInstance();
			endCalendar.setTime(endTime);
			//
			if (currentTime.compareTo(endTime) < 0)
			{
				currentCalendar.add(Calendar.DATE, 1);
				currentTime = currentCalendar.getTime();
			}
			if (startTime.compareTo(endTime) < 0)
			{
				startCalendar.add(Calendar.DATE, 1);
				startTime = startCalendar.getTime();
			}
			if (currentTime.before(startTime))
			{
				valid = false;
			}
			else
			{
				if (currentTime.after(endTime))
				{
					endCalendar.add(Calendar.DATE, 1);
					endTime = endCalendar.getTime();
				}
				if (currentTime.before(endTime))
				{
					valid = true;
				}
				else
				{
					valid = false;
				}
			}
			return valid;
		}
		else
		{
			throw new IllegalArgumentException(
				"Not a valid time, expecting HH:MM:SS format");
		}
	}

	public static String formatTime(long hour, long minute, long second)
	{
		while (second >= 60)
		{
			second -= 60;
			minute++;
		}
		while (minute >= 60)
		{
			minute -= 60;
			hour++;
		}
		String result = "";
		if (hour < 10)
		{
			result += "0";
		}
		result += hour + ":";
		if (minute < 10)
		{
			result += "0";
		}
		result += minute + ":";
		if (second < 10)
		{
			result += "0";
		}
		result += second;
		return result;
	}

	public static String formatTime(long hour, long minute)
	{
		while (minute >= 60)
		{
			minute -= 60;
			hour++;
		}
		String result = "";
		if (hour < 10)
		{
			result += "0";
		}
		result += hour + ":";
		if (minute < 10)
		{
			result += "0";
		}
		result += minute;
		return result;
	}

	public static String getTime(Calendar c)
	{
		return new SimpleDateFormat("HH:mm").format(c.getTime());
	}

	public static String getTimeWithSeconds(Calendar c)
	{
		return new SimpleDateFormat("HH:mm:ss").format(c.getTime());
	}

	public static String getCurrentTime()
	{
		final Calendar c = Calendar.getInstance();
		// return formatTime(c.get(Calendar.HOUR_OF_DAY),
		// Calendar.MINUTE, Calendar.SECOND);
		return new SimpleDateFormat("hh:mm:ss").format(c.getTime());
	}

	public static String getCurrentDate()
	{
		return getDate(System.currentTimeMillis());
	}

	public static String getDate(long millis)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		String month;
		if (LanguageEssentials
			.isRightToLeftLanguage(LocaleEssentials.LANGUAGE_DEFAULT)) month = "."
			+ StringEssentials.addPaddingToNumber(
				String.valueOf(c.get(Calendar.MONTH) + 1), 2) + ".";
		else month = " "
			+ new DateFormatSymbols().getMonths()[c.get(Calendar.MONTH)] + " ";
		return StringEssentials.addPaddingToNumber(
			String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2)
			+ month + +c.get(Calendar.YEAR);
	}

	public static String getDateAndTime(long millis)
	{
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(millis);
		String month;
		if (LanguageEssentials
			.isRightToLeftLanguage(LocaleEssentials.LANGUAGE_DEFAULT)) month = "."
			+ StringEssentials.addPaddingToNumber(
				String.valueOf(c.get(Calendar.MONTH) + 1), 2) + ".";
		else month = " "
			+ new DateFormatSymbols().getMonths()[c.get(Calendar.MONTH)] + " ";
		return StringEssentials.addPaddingToNumber(
			String.valueOf(c.get(Calendar.DAY_OF_MONTH)), 2)
			+ month
			+ +c.get(Calendar.YEAR)
			+ " | "
			+ formatTime(c.get(Calendar.HOUR_OF_DAY), c.get(Calendar.MINUTE),
				c.get(Calendar.SECOND));
	}

	public static int getHour(String formattedTime)
	{
		final int index = formattedTime.indexOf(':');
		return Integer.parseInt(new String(formattedTime.substring(0, index)));
	}

	public static int getHourOffset(String baseT, String t)
	{
		return getHour(t) - getHour(baseT);
	}

	public static int getMinute(String formattedTime)
	{
		final int index = formattedTime.indexOf(':');
		return Integer.parseInt(new String(formattedTime.substring(index + 1,
			index + 3)));
	}

	public static int getMinuteOffset(String baseT, String t)
	{
		return getMinute(t) - getMinute(baseT);
	}

	public static int getSecond(String formattedTime)
	{
		final int index = formattedTime.lastIndexOf(':');
		return Integer.parseInt(new String(formattedTime.substring(index + 1,
			index + 3)));
	}

	public static int getSecondOffset(String baseT, String t)
	{
		return getSecond(t) - getSecond(baseT);
	}

	/**
	 * @param baseT
	 *          formatted time
	 * @param t
	 *          formatted time 2
	 * @return time difference in seconds
	 */
	public static long getTimeDifference(String baseT, String t)
	{
		int hour = getHourOffset(baseT, t);
		if (hour < 0)
		{
			hour += 12;
		}
		int minute = getMinuteOffset(baseT, t);
		if (minute < 0)
		{
			minute += 60;
			hour--;
		}
		int second = getSecondOffset(baseT, t);
		if (second < 0)
		{
			second += 60;
			minute--;
		}
		return hour * 60 * 60 + minute * 60 + second;
	}

	public static String getTimeDifferenceAsString(String baseT, String t)
	{
		return formatTime(0, 0, getTimeDifference(baseT, t));
	}

	public static double GMTOffset(String tz)
	{
		if (inDST(tz)) return rawGMTOffset(tz) + 1;
		else return rawGMTOffset(tz);
	}

	public static double GMTOffset(String tz, Date d)
	{
		if (inDST(tz, d)) return rawGMTOffset(tz) + 1;
		else return rawGMTOffset(tz);
	}

	public static boolean inDST(String tz)
	{
		return inDST(tz, new Date());
	}

	public static boolean inDST(String tz, Date d)
	{
		return TimeZone.getTimeZone(tz).inDaylightTime(d);
	}

	public static double millisToYears(long m)
	{
		return m / (1000.0 * 60.0 * 60.0 * 24.0 * 365.26);
	}

	public static double rawGMTOffset(String tz)
	{
		final int rawOffset = TimeZone.getTimeZone(tz).getRawOffset();
		final int hour = rawOffset / (60 * 60 * 1000);
		final int minute = Math.abs(rawOffset / (60 * 1000)) % 60;
		final double result = hour + minute / 60.0;
		return result;
	}

	public static String roundSeconds(String formattedTime)
	{
		String[] tmp = formattedTime.split(":");
		if (tmp.length == 2) return formattedTime;
		else
		{
			int hrs = Integer.parseInt(tmp[0]);
			int mins = Integer.parseInt(tmp[1]);
			int secs = Integer.parseInt(tmp[2]);
			if (secs < 30) return tmp[0] + ":" + tmp[1];
			else
			{
				mins++;
				if (mins >= 60)
				{
					mins -= 60;
					hrs++;
				}
				return StringEssentials.addPaddingToNumber(hrs + "", 2) + ":"
					+ StringEssentials.addPaddingToNumber(mins + "", 2);
			}
		}
	}
}
