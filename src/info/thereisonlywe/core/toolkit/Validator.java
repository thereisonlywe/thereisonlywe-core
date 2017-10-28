package info.thereisonlywe.core.toolkit;

public class Validator
{
	public static boolean isNullLocation(double latitude, double longitude)
	{
		if (latitude == 0.0 && longitude == 0.0) return true;
		return false;
	}

	public static boolean isValidAltitude(double altitudeInMeters)
	{
		if (!isValidDouble(altitudeInMeters)) return false;
		if (altitudeInMeters > 8848) return false; // Everest Mountain (highest
		// point)
		if (altitudeInMeters < -424) return false; // Dead Sea shore (lowest
		// point)
		return true;
	}

	public static boolean isValidDouble(double d)
	{
		if (Double.isNaN(d) || Double.isInfinite(d)
			|| d == Double.NEGATIVE_INFINITY || d == Double.POSITIVE_INFINITY) return false;
		return true;
	}

	public static boolean isValidInt(String s)
	{
		try
		{
			Integer.parseInt(s);
			return true;
		}
		catch (final NumberFormatException e)
		{
			return false;
		}
	}

	public static boolean isValidLocation(double latitude, double longitude,
		boolean allowHighLats, boolean allowNullLocation)
	{
		if (allowNullLocation)
		{
			return isValidLocation(latitude, longitude, allowHighLats);
		}
		else if (!isNullLocation(latitude, longitude))
		{
			return isValidLocation(latitude, longitude, allowHighLats);
		}
		else return false;
	}

	public static boolean isValidLocation(double latitude, double longitude,
		boolean allowHighLats)
	{
		if (!isValidDouble(latitude) || !isValidDouble(longitude)) return false;
		if (allowHighLats)
		{
			if (latitude >= -90 && latitude <= 90 && longitude >= -180
				&& longitude <= 180) return true;
			else return false;
		}
		else
		{
			if (latitude > -60 && latitude < 60 && longitude >= -180
				&& longitude <= 180) return true;
			else return false;
		}
	}

	public static boolean isValidLocation(double latitude, double longitude)
	{
		return isValidLocation(latitude, longitude, true);
	}

	/**
	 * @param UTC
	 *          (between -12 and 14 inclusive)
	 */
	public static boolean isValidTimeZone(double UTC)
	{
		if (!isValidDouble(UTC)) return false;
		if (UTC >= -12 && UTC <= 14) return true;
		return false;
	}

	public static boolean isNumeric(String str)
	{
		if (str == null || str.equals("")) return false;
		final boolean val = str.matches("[+-]?\\d*(\\.\\d+)?");
		if (!val) return str.equals("0.");
		return val;
	}

	public static boolean isASCII(String str)
	{
		return str.matches("^\\p{ASCII}*$");
	}
}
