package info.thereisonlywe.core.essentials;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

/**
 * @author thereisonlywe
 */
public class MathEssentials
{
	public static int sumDigits(int n)
	{
		return n - 9 * ((n - 1) / 9);
	}

	public static int add(int... values)
	{
		long sum = 0;
		for (final int value : values)
		{
			sum += value;
			if (sum > Integer.MAX_VALUE) { return Integer.MAX_VALUE; }
			if (sum < Integer.MIN_VALUE) { return Integer.MIN_VALUE; }
		}
		return (int) sum;
	}

	public static int subtract(int from, int amount)
	{
		long sum = 0;
		sum = from - amount;
		if (sum > Integer.MAX_VALUE) { return Integer.MAX_VALUE; }
		if (sum < Integer.MIN_VALUE) { return Integer.MIN_VALUE; }
		return (int) sum;
	}

	public static long add(long left, long right)
	{
		if (adds(left, right)) return left + right;
		else return Long.MAX_VALUE;
	}

	public static long subtract(long left, long right)
	{
		if (subtracts(left, right)) return left - right;
		else return Long.MIN_VALUE;
	}

	public static boolean adds(long left, long right)
	{
		if (right < 0 && right != Long.MIN_VALUE)
		{
			return subtracts(left, -right);
		}
		else
		{
			return (~(left ^ right) & (left ^ (left + right))) < 0;
		}
	}

	public static boolean subtracts(long left, long right)
	{
		if (right < 0)
		{
			return adds(left, -right);
		}
		else
		{
			return ((left ^ right) & (left ^ (left - right))) < 0;
		}
	}

	public static boolean adds(int left, int right)
	{
		if (right < 0 && right != Long.MIN_VALUE)
		{
			return subtracts(left, -right);
		}
		else
		{
			return (~(left ^ right) & (left ^ (left + right))) < 0;
		}
	}

	public static boolean subtracts(int left, int right)
	{
		if (right < 0)
		{
			return adds(left, -right);
		}
		else
		{
			return ((left ^ right) & (left ^ (left - right))) < 0;
		}
	}

	public static int bound(int number, int min, int max)
	{
		int temp;
		if (max < min) {temp = max; max = min; min = temp;}
		if (number > max) return max;
		if (number < min) return min;
		else return number;
	}

	public static double bound(double number, double min, double max)
	{
		double temp;
		if (max < min) {temp = max; max = min; min = temp;}
		if (number > max) return max;
		if (number < min) return min;
		else return number;
	}

	public static ArrayList<Integer> changeNumberBase(int sourceNumberBase,
		int targetNumberBase, int[] sourceDigits)
	{
		return toNumberBase(targetNumberBase,
			fromNumberBase(sourceNumberBase, sourceDigits));
	}

	public static String changeNumberBase(int sourceNumberBase,
		int targetNumberBase, int[] sourceDigits, char separator)
	{
		return toNumberBase(targetNumberBase,
			fromNumberBase(sourceNumberBase, sourceDigits), separator);
	}

	public static String changeNumberBase(int sourceNumberBase,
		int targetNumberBase, String sourceDigits, char separator)
	{
		return toNumberBase(targetNumberBase,
			fromNumberBase(sourceNumberBase, sourceDigits, separator), separator);
	}

	public static long changeNumberBase_PreDecimal(int sourceNumberBase,
		int targetNumberBase, long number)
	{
		return toNumberBase_PreDecimal(targetNumberBase,
			fromNumberBase_PreDecimal(sourceNumberBase, number));
	}

	public static long changeNumberBase_ToPreDecimal(int sourceNumberBase,
		int targetNumberBase, String sourceDigits, char separator)
	{
		return toNumberBase_PreDecimal(targetNumberBase,
			fromNumberBase(sourceNumberBase, sourceDigits, separator));
	}

	public static String changeNumberBase_FromPreDecimal(int sourceNumberBase,
		int targetNumberBase, long number, char separator)
	{
		return toNumberBase(targetNumberBase,
			fromNumberBase_PreDecimal(sourceNumberBase, number), separator);
	}

	public static double degreesToRadians(double deg)
	{
		return deg * Math.PI / 180.0;
	}

	public static double radiansToDegrees(double rad)
	{
		return rad * 180.0 / Math.PI;
	}

	/**
	 * @param sourceNumberBase
	 * @param digits
	 *          ordered high to low
	 * @return equivalent number in base 10
	 */
	public static long fromNumberBase(int sourceNumberBase, int[] digits)
	{
		long result = 0;
		long l = 1;
		for (int i = digits.length - 1; i >= 0; i--)
		{
			if (i < digits.length - 1)
			{
				l = l * sourceNumberBase;
			}
			result += l * digits[i];
		}
		return result;
	}

	/**
	 * @param sourceNumberBase
	 * @param number
	 *          digits glued by the separator char
	 * @return equivalent number in base 10
	 */
	public static long fromNumberBase(int sourceNumberBase, String numberDigits,
		char separator)
	{
		return fromNumberBase(sourceNumberBase,
			DataEssentials.toIntArray(numberDigits.split(separator + "")));
	}

	public static long fromNumberBase_PreDecimal(int sourceNumberBase, long number)
	{
		if (sourceNumberBase > 10) throw new InvalidParameterException(
			"Number base cannot exceed 10.");
		else return fromNumberBase(sourceNumberBase,
			DataEssentials.toIntArray(number));
	}

	public static int getClosestToRadix(int[] values, int radix)
	{
		int previndex = 0;
		int bestValue = Integer.MAX_VALUE;
		for (int i = 0; i < values.length; i++)
		{
			final int tmp = Math.abs(subtract(radix, values[i]));
			if (tmp < bestValue)
			{
				previndex = i;
				bestValue = tmp;
			}
		}
		return previndex;
	}

	public static int getClosestValueToRadix(int[] values, int radix)
	{
		return values[getClosestToRadix(values, radix)];
	}

	/**
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return distance in kilometers.
	 */
	public static double getDistance(double lat1, double lon1, double lat2,
		double lon2)
	{
		final double theta = lon1 - lon2;
		double dist = Math.sin(degreesToRadians(lat1))
			* Math.sin(degreesToRadians(lat2)) + Math.cos(degreesToRadians(lat1))
			* Math.cos(degreesToRadians(lat2)) * Math.cos(degreesToRadians(theta));
		dist = Math.acos(dist);
		dist = radiansToDegrees(dist);
		dist = dist * 60 * 1.1515;
		dist = dist * 1.609344;
		return dist; // km
	}

	public static int getMaxIndex(int[] values)
	{
		return getClosestToRadix(values, Integer.MAX_VALUE);
	}

	public static int getMaxValue(int[] values)
	{
		return values[getMaxIndex(values)];
	}

	public static int getMinIndex(int[] values)
	{
		return getClosestToRadix(values, Integer.MIN_VALUE);
	}

	public static int getMinValue(int[] values)
	{
		return values[getMinIndex(values)];
	}

	// http://floating-point-gui.de/errors/comparison/
	public static boolean nearlyEqual(double a, double b, double errorMargin)
	{
		final double absA = Math.abs(a);
		final double absB = Math.abs(b);
		final double diff = Math.abs(a - b);
		if (a == b) return true;
		else if (a == 0 || b == 0 || diff < Double.MIN_NORMAL) // a or b is zero or
		// both are
		// extremely close
		// to it
		// relative error is less meaningful here
		return diff < errorMargin * Double.MIN_NORMAL;
		else return diff / (absA + absB) < errorMargin;
	}

	public static int newRandom()
	{
		final Random r = new Random();
		final int randint = r.nextInt(Integer.MAX_VALUE);
		return randint;
	}

	public static int newRandom(int lastValueAllowed)
	{
		final Random r = new Random();
		final int randint = r.nextInt(lastValueAllowed + 1);
		return randint;
	}

	// reduces number by reductionInterval continously until it is bigger or
	// equal to the critical value
	public static double reduce(double number, double reductionInterval,
		double criticalValue)
	{
		if (number < 0)
		{
			number *= -1;
		}
		if (reductionInterval == criticalValue) return number % criticalValue;
		while (number >= criticalValue)
		{
			number -= reductionInterval;
		}
		return number;
	}

	// reduces number by reductionInterval continously until it is bigger or
	// equal to the critical value
	public static long reduce(long number, long reductionInterval,
		long criticalValue)
	{
		if (number < 0)
		{
			number *= -1;
		}
		if (reductionInterval == criticalValue) return number % criticalValue;
		while (number >= criticalValue)
		{
			number -= reductionInterval;
		}
		return number;
	}

	// reduces number by reductionInterval continously until it is bigger or
	// equal to the critical value
	public static int reduce(int number, int reductionInterval, int criticalValue)
	{
		if (number < 0)
		{
			number *= -1;
		}
		if (reductionInterval == criticalValue) return number % criticalValue;
		while (number >= criticalValue)
		{
			number -= reductionInterval;
		}
		return number;
	}

	public static ArrayList<Integer> toNumberBase(int base, long number)
	{
		final ArrayList<Integer> result = new ArrayList<Integer>();
		while (number >= base)
		{
			final int remainder = (int) (number % base);
			result.add(remainder);
			number = number / base;
		}
		result.add((int) number);
		Collections.reverse(result);
		return result;
	}

	public static String toNumberBase(int base, long number, char separator)
	{
		return StringEssentials.glue(
			DataEssentials.toStringArrayList(MathEssentials.toNumberBase(28, 111)),
			separator);
	}

	public static long toNumberBase_PreDecimal(int base, long number)
	{
		if (base > 10) throw new InvalidParameterException(
			"Number base cannot exceed 10.");
		else return Long.parseLong(StringEssentials.glue(DataEssentials
			.toStringArrayList(MathEssentials.toNumberBase(base, number)), ""));
	}

	public static double[] toPercentage(double[] values)
	{
		double counter = 0;
		for (final double t : values)
		{
			counter += t;
		}
		for (int i = 0; i < values.length; i++)
		{
			values[i] = Math.round(values[i] * 100.0 / counter);
		}
		return values;
	}

	public static double[] toPercentage(int[] values)
	{
		final double[] result = new double[values.length];
		int counter = 0;
		for (final int t : values)
		{
			counter += t;
		}
		for (int i = 0; i < values.length; i++)
		{
			result[i] = values[i] * 100.0 / counter;
		}
		return result;
	}

	public static double round(double d, int decimalPlaces,
		RoundingMode roundingMode)
	{
		try
		{
			return new BigDecimal(d).setScale(decimalPlaces, roundingMode)
				.doubleValue();
		}
		catch (Exception e)
		{
			// Logger.log(Level.WARNING, e);
			return d;
		}
	}
}
