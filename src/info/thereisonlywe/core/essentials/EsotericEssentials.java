package info.thereisonlywe.core.essentials;

import info.thereisonlywe.core.cosmology.Element;
import info.thereisonlywe.core.cosmology.Gender;
import info.thereisonlywe.core.cosmology.Letter;
import info.thereisonlywe.core.cosmology.Modality;
import info.thereisonlywe.core.cosmology.Planet;
import info.thereisonlywe.core.cosmology.Temperament;
import info.thereisonlywe.core.cosmology.Word;
import info.thereisonlywe.core.cosmology.ZodiacAspect;
import info.thereisonlywe.core.cosmology.ZodiacDegree;
import info.thereisonlywe.core.cosmology.ZodiacMansion;
import info.thereisonlywe.core.cosmology.ZodiacSign;
import info.thereisonlywe.core.objects.CircularArray;
import info.thereisonlywe.core.objects.NumberRange;
import java.util.ArrayList;
import java.util.List;

public class EsotericEssentials
{
	public static ZodiacSign getTropicalSunSign(String formattedDate,
		String separator, double gmtHour)
	{
		return getTropicalSunDegree(formattedDate, separator, gmtHour).zodiacSign;
	}

	public static ZodiacSign getSiderealSunSign(String formattedDate,
		String separator, double gmtHour)
	{
		return getSiderealSunDegree(formattedDate, separator, gmtHour).zodiacSign;
	}

	public static ZodiacDegree getTropicalSunDegree(String formattedDate,
		String separator, double gmtHour)
	{
		int vals[] = DateEssentials.getDatePartsAsInt(formattedDate, separator);
		return getTropicalSunDegree(vals[0], vals[1], vals[2], gmtHour);
	}

	public static ZodiacDegree getSiderealSunDegree(String formattedDate,
		String separator, double gmtHour)
	{
		int vals[] = DateEssentials.getDatePartsAsInt(formattedDate, separator);
		return toSiderealZodiac(
			getTropicalSunDegree(vals[0], vals[1], vals[2], gmtHour), vals[2]);
	}

	public static ZodiacDegree getMoonDegree(ZodiacDegree sunDegree,
		int moonPhaseDegree)
	{
		return new ZodiacDegree(sunDegree.degree + moonPhaseDegree);
	}

	public static ZodiacSign getMoonSign(ZodiacDegree sunDegree,
		int moonPhaseDegree)
	{
		return new ZodiacDegree(sunDegree.degree + moonPhaseDegree).zodiacSign;
	}

	public static ZodiacDegree toTropicalZodiac(ZodiacDegree deg, int year)
	{
		int dif = year - 1000;
		double difDeg = 0.013865734 * dif;
		return new ZodiacDegree(deg.degree + difDeg + 9.914);
	}

	public static ZodiacDegree toSiderealZodiac(ZodiacDegree deg, int year)
	{
		// zero date is 285
		int dif = year - 1000;
		double difDeg = 0.013865734 * dif;
		return new ZodiacDegree(deg.degree - difDeg - 9.914);
	}

	public static ZodiacDegree getTropicalSunDegree(int day, int month, int year,
		double GMThour)
	{
		double offset = 0;
		if (DateEssentials.isLeapYear(year - 2)) offset = -0.234;
		else if (DateEssentials.isLeapYear(year - 3)) offset = -0.267;
		else if (DateEssentials.isLeapYear(year))
		{
			if (month <= 2) offset = -0.767;
			else offset = 0.234;
		}
		switch (month)
		{
			case (1):
				return new ZodiacDegree(ZodiacSign.CAPRICORN, day
					+ (day <= 15 ? 9.74 : 11.3) + offset + (GMThour / 24.0));
			case (2):
				return new ZodiacDegree(ZodiacSign.AQUARIUS, day
					+ (day <= 15 ? 11.3 : 9.5834) + offset + (GMThour / 24.0));
			case (3):
				return new ZodiacDegree(ZodiacSign.PISCES, day
					+ (day <= 15 ? 9.5834 : 10.45) + offset + (GMThour / 24.0));
			case (4):
				return new ZodiacDegree(ZodiacSign.ARIES, day
					+ (day <= 15 ? 10.45 : 9.8167) + offset + (GMThour / 24.0));
			case (5):
				return new ZodiacDegree(ZodiacSign.TAURUS, day
					+ (day <= 15 ? 9.8167 : 9.7) + offset + (GMThour / 24.0));
			case (6):
				return new ZodiacDegree(ZodiacSign.GEMINI, day
					+ (day <= 15 ? 9.7 : 8.367) + offset + (GMThour / 24.0));
			case (7):
				return new ZodiacDegree(ZodiacSign.CANCER, day
					+ (day <= 15 ? 8.367 : 7.95) + offset + (GMThour / 24.0));
			case (8):
				return new ZodiacDegree(ZodiacSign.LEO, day + (day <= 15 ? 7.95 : 7.75)
					+ offset + (GMThour / 24.0));
			case (9):
				return new ZodiacDegree(ZodiacSign.VIRGO, day
					+ (day <= 15 ? 7.75 : 7.0) + offset + (GMThour / 24.0));
			case (10):
				return new ZodiacDegree(ZodiacSign.LIBRA, day
					+ (day <= 15 ? 7.0 : 7.75) + offset + (GMThour / 24.0));
			case (11):
				return new ZodiacDegree(ZodiacSign.SCORPIO, day
					+ (day <= 15 ? 7.75 : 7.967) + offset + (GMThour / 24.0));
			case (12):
				return new ZodiacDegree(ZodiacSign.SAGITTARIUS, day
					+ (day <= 15 ? 7.967 : 9.74) + offset + (GMThour / 24.0));
			default:
				return null;
		}
	}

	public static ZodiacSign getTropicalSunSign(int day, int month, int year,
		int gmtHour)
	{
		return getTropicalSunDegree(day, month, year, gmtHour).zodiacSign;
	}

	public static Word asDivineWord(long number)
	{
		final ArrayList<Integer> arrayList = MathEssentials
			.toNumberBase(28, number);
		final StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arrayList.size(); i++)
		{
			int index = arrayList.get(i);
			index = index == 0 ? 27 : index - 1;
			sb.append(Letter.values()[index].toString());
		}
		return new Word(sb.toString());
	}

	public static ZodiacMansion asZodiacMansion(Letter letter)
	{
		return ZodiacMansion.values()[letter.ordinal()];
	}

	public static ZodiacMansion asZodiacMansion(long number)
	{
		return getZodiacMansion(asZodiacDegree(number));
	}

	public static ZodiacMansion asZodiacMansion(ZodiacDegree deg)
	{
		return getZodiacMansion(deg);
	}

	public static Letter asLetter(long number)
	{
		return Letter.values()[getZodiacMansion(asZodiacDegree(number)).ordinal()];
	}

	public static Letter asLetter(ZodiacDegree deg)
	{
		return Letter.values()[getZodiacMansion(deg).ordinal()];
	}

	public static ZodiacMansion asZodiacMansion(String phrase)
	{
		return getZodiacMansion(asZodiacDegree(phrase));
	}

	public static long asNumber(Word word)
	{
		final int[] values = new int[word.length()];
		for (int i = 0; i < word.length(); i++)
		{
			final char c = word.charAt(i);
			values[i] = Letter.valueOf(c).ordinal() + 1;
			if (values[i] == 28)
			{
				values[i] = 0;
			}
		}
		return MathEssentials.fromNumberBase(28, values);
	}

	public static long asNumber(String s)
	{
		String[] st = s.split(" ");
		long res = 0;
		for (int j = 0; j < st.length; j++)
		{
			long temp = res + asNumber(new Word(st[j]));
			if (temp < 0)
			{
				res = 0;
				break;
			}
			else res = temp;
		}
		return res;
	}

	public static boolean aspects(double point1, double point2,
		ZodiacAspect aspect, double orb)
	{
		point1 = EsotericEssentials.normalizeZodiacDegree(point1);
		point2 = EsotericEssentials.normalizeZodiacDegree(point2);
		final double raw = point1 >= point2 ? point1 - point2 : point2 - point1;
		final double min = raw - orb + 360;
		final double max = raw + orb + 360;
		final NumberRange pr = new NumberRange(min, max);
		final NumberRange pr2 = new NumberRange(min - 360, max - 360);
		if (pr.isInRange(aspect.angle + 360) || pr2.isInRange(360 - aspect.angle)) return true;
		else return false;
	}

	public static boolean aspects(ZodiacDegree deg1, ZodiacDegree deg2,
		ZodiacAspect aspect, double orb)
	{
		return aspects(deg1.degree, deg2.degree, aspect, orb);
	}

	public static ZodiacDegree asZodiacDegree(long number)
	{
		int n = normalizeZodiacDegree((int) number);
		double t = n / 12.0;
		n = n % 12;
		n += 5;
		if (n >= 12) n -= 12;
		return new ZodiacDegree(ZodiacSign.values()[n], t % 30.0);
	}

	public static ZodiacDegree asZodiacDegree(String phrase)
	{
		return asZodiacDegree(phrase.split(" "));
	}

	public static ZodiacSign asZodiacSign(long number)
	{
		return asZodiacDegree(number).zodiacSign;
	}

	public static ZodiacSign asZodiacSign(String phrase)
	{
		return asZodiacDegree(phrase).zodiacSign;
	}

	public static ZodiacAspect getMajorAspect(double degree, double orb)
	{
		for (final ZodiacAspect element : ZodiacAspect.ASPECTS)
		{
			if (element.isMajorAspect() && aspects(degree, 0, element, orb)) { return element; }
		}
		return null;
	}

	public static List<ZodiacAspect> getAspects(double point1, double point2,
		double orb)
	{
		point1 = EsotericEssentials.normalizeZodiacDegree(point1);
		point2 = EsotericEssentials.normalizeZodiacDegree(point2);
		final List<ZodiacAspect> result = new ArrayList<ZodiacAspect>();
		for (final ZodiacAspect element : ZodiacAspect.ASPECTS)
		{
			if (aspects(point1, point2, element, orb))
			{
				result.add(element);
			}
		}
		return result;
	}

	public static List<ZodiacAspect> getAspects(double point1, double point2,
		ZodiacAspect[] aspects, double orb)
	{
		point1 = EsotericEssentials.normalizeZodiacDegree(point1);
		point2 = EsotericEssentials.normalizeZodiacDegree(point2);
		final List<ZodiacAspect> result = new ArrayList<ZodiacAspect>();
		for (final ZodiacAspect element : aspects)
		{
			if (aspects(point1, point2, element, orb))
			{
				result.add(element);
			}
		}
		return result;
	}

	public static List<ZodiacAspect> getAspects(ZodiacDegree deg1,
		ZodiacDegree deg2, double orb)
	{
		return getAspects(deg1.degree, deg2.degree, orb);
	}

	public static List<ZodiacAspect> getAspects(ZodiacDegree deg1,
		ZodiacDegree deg2, ZodiacAspect[] aspects, double orb)
	{
		return getAspects(deg1.degree, deg2.degree, aspects, orb);
	}

	public static final CircularArray<Planet> getChaldeanPlanetOrder()
	{
		return new CircularArray<Planet>(new Planet[] { Planet.SATURN,
			Planet.JUPITER, Planet.MARS, Planet.SUN, Planet.VENUS, Planet.MERCURY,
			Planet.MOON });
	}

	public static double getDegree(ZodiacSign baseSign, double offset)
	{
		return baseSign.degreeSpan.start + offset;
	}

	public static Element getElement(boolean isMoist, boolean isHot)
	{
		if (isMoist && isHot) return Element.AIR;
		else if (isMoist && !isHot) return Element.WATER;
		else if (!isMoist && isHot) return Element.FIRE;
		else if (!isMoist && !isHot) return Element.EARTH;
		else return null; // impossible
	}

	public static Element getElement(Temperament t)
	{
		return getElement(t.isMoist(), t.isHot());
	}

	public static double[] getElementDistribution(String phrase)
	{
		return getElementDistribution(toWordArray(phrase));
	}

	public static double[] getElementDistribution(Word[] words)
	{
		final int[] result = new int[4];
		for (int j = 0; j < words.length; j++)
		{
			result[asZodiacSign(asNumber(words[j])).element.ordinal()]++;
		}
		return MathEssentials.toPercentage(result);
	}

	public static ZodiacMansion getZodiacMansion(double degree)
	{
		degree = normalizeZodiacDegree(degree);
		for (final ZodiacMansion m : ZodiacMansion.values())
		{
			if (m.degreeSpan.isInRange(Double.valueOf(degree))) return m;
		}
		return null;
	}

	public static ZodiacMansion getZodiacMansion(int order)
	{
		return ZodiacMansion.values()[order - 1];
	}

	public static ZodiacMansion getZodiacMansion(ZodiacDegree deg)
	{
		return getZodiacMansion(deg.degree);
	}

	public static double[] getZodiacMansionDistribution(String phrase)
	{
		return getZodiacMansionDistribution(toWordArray(phrase));
	}

	public static double[] getZodiacMansionDistribution(Word[] words)
	{
		final int[] result = new int[28];
		for (int j = 0; j < words.length; j++)
		{
			result[asZodiacMansion(asNumber(words[j])).ordinal()]++;
		}
		return MathEssentials.toPercentage(result);
	}

	public static Planet getPlanet(String planetName, String lang)
	{
		for (int i = 0; i < Planet.values().length; i++)
		{
			Planet p = Planet.values()[i];
			if (p.getName(lang).equals(planetName)) return p;
		}
		return null;
	}

	public static ZodiacSign getZodiacSign(double degree)
	{
		degree = normalizeZodiacDegree(degree);
		for (final ZodiacSign zs : ZodiacSign.values())
		{
			if (zs.degreeSpan.isInRange(Double.valueOf(degree))) return zs;
		}
		return null;
	}

	public static ZodiacSign getZodiacSign(Element element, Modality modality)
	{
		final CircularArray<ZodiacSign> zs = getZodiacSignOrder();
		while (true)
		{
			if (zs.current().element == element && zs.current().modality == modality) return zs
				.current();
			zs.next();
		}
	}

	public static ZodiacSign getZodiacSign(ZodiacDegree deg)
	{
		return getZodiacSign(deg.degree);
	}

	public static double[] getZodiacSignDistribution(String phrase)
	{
		return getZodiacSignDistribution(toWordArray(phrase));
	}

	public static double[] getZodiacSignDistribution(Word[] words)
	{
		final int[] result = new int[12];
		for (int j = 0; j < words.length; j++)
		{
			result[asZodiacSign(asNumber(words[j])).ordinal()]++;
		}
		return MathEssentials.toPercentage(result);
	}

	public static final CircularArray<ZodiacSign> getZodiacSignOrder()
	{
		return new CircularArray<ZodiacSign>(ZodiacSign.values());
	}

	public static List<ZodiacSign> getZodiacSignSpan(ZodiacMansion m)
	{
		final List<ZodiacSign> result = new ArrayList<ZodiacSign>();
		result.add(getZodiacSign(m.degreeSpan.start));
		final ZodiacSign tmp = getZodiacSign(m.degreeSpan.end);
		if (!result.contains(tmp))
		{
			result.add(tmp);
		}
		return result;
	}

	public static List<Double> getZodiacSignDegreeSpan(ZodiacMansion m)
	{
		final List<Double> result = new ArrayList<Double>();
		ZodiacSign zs = getZodiacSign(m.degreeSpan.start);
		double deg = m.degreeSpan.start;
		do
		{
			deg += 0.1;
		}
		while (zs == getZodiacSign(deg) && deg < m.degreeSpan.start);
		if (zs == getZodiacSign(deg)) result
			.add(ZodiacMansion.DEGREE_SPAN_PER_MANSION);
		else
		{
			result.add(deg - m.degreeSpan.start);
			result.add(m.degreeSpan.end - deg);
		}
		return result;
	}

	public static boolean isTransitionalZodiacMansion(ZodiacMansion m)
	{
		if (getZodiacSignSpan(m).size() > 1) return true;
		return false;
	}

	public static Element marry(Element e1, Element e2)
	{
		if (e1 == Element.FIRE && e2 == Element.EARTH) return Element.FIRE;
		else if (e1 == Element.FIRE && e2 == Element.AIR) return Element.EARTH;
		else if (e1 == Element.FIRE && e2 == Element.WATER) return Element.AIR;
		else if (e1 == Element.EARTH && e2 == Element.AIR) return Element.AIR;
		else if (e1 == Element.EARTH && e2 == Element.WATER) return Element.WATER;
		else if (e1 == Element.AIR && e2 == Element.WATER) return Element.FIRE;
		else if (e1 == Element.AIR && e2 == Element.AIR) return Element.WATER;
		else if (e1 == Element.WATER && e2 == Element.WATER) return Element.EARTH;
		else if (e1 == Element.EARTH && e2 == Element.EARTH) return Element.EARTH;
		else if (e1 == Element.FIRE && e2 == Element.FIRE) return Element.WATER;
		else return marry(e2, e1);
	}

	public static Gender marry(Gender g1, Gender g2)
	{
		if (g1 == Gender.MALE && g2 == Gender.FEMALE) return Gender.MALE;
		else if (g1 == Gender.FEMALE && g2 == Gender.FEMALE) return Gender.FEMALE;
		else if (g1 == Gender.MALE && g2 == Gender.MALE) return Gender.FEMALE;
		else return marry(g2, g1);
	}

	public static Modality marry(Modality m1, Modality m2)
	{
		if (m1 == Modality.CARDINAL && m2 == Modality.FIXED) return Modality.MUTABLE;
		else if (m1 == Modality.CARDINAL && m2 == Modality.MUTABLE) return Modality.CARDINAL;
		else if (m1 == Modality.FIXED && m2 == Modality.MUTABLE) return Modality.FIXED;
		else if (m1 == Modality.MUTABLE && m2 == Modality.MUTABLE) return Modality.MUTABLE;
		else if (m1 == Modality.CARDINAL && m2 == Modality.CARDINAL) return Modality.FIXED;
		else if (m1 == Modality.FIXED && m2 == Modality.FIXED) return Modality.CARDINAL;
		else return marry(m2, m1);
	}

	public static ZodiacDegree marry(ZodiacDegree d1, ZodiacDegree d2)
	{
		return new ZodiacDegree(marry(d1.zodiacSign, d2.zodiacSign),
			(d1.zodiacSignOffset + d2.zodiacSignOffset) % 30);
	}

	public static ZodiacSign marry(ZodiacSign z1, ZodiacSign z2)
	{
		return getZodiacSign(marry(z1.element, z2.element),
			marry(z1.modality, z2.modality));
	}

	/**
	 * @return a degree between 0 (inclusive) and 360 (exclusive)
	 */
	public static double normalizeZodiacDegree(double degree)
	{
		while (degree < 0.0)
			degree += 360.0;
		if (degree < 360) return degree;
		else return degree % 360;
	}

	/**
	 * @return a degree between 0 (inclusive) and 360 (exclusive)
	 */
	public static int normalizeZodiacDegree(int degree)
	{
		while (degree < 0)
			degree += 360;
		if (degree < 360) return degree;
		else return degree % 360;
	}

	public static Word[] toWordArray(String phrase)
	{
		return toWordArray(phrase.split(" "));
	}

	public static Word[] toWordArray(String[] words)
	{
		final Word[] result = new Word[words.length];
		for (int i = 0; i < result.length; i++)
		{
			result[i] = new Word(words[i]);
		}
		return result;
	}

	// public static double toSidereal(double tropicalDegree, int year, Ayanamsa
	// ayanamsa)
	// {
	// return normalizeZodiacDegree(tropicalDegree - (50 * (year -
	// ayanamsa.zeroDate) / 3600));
	// }
	//
	// public static double toTropical(double siderealDegree, int year, Ayanamsa
	// ayanamsa)
	// {
	// return normalizeZodiacDegree(siderealDegree + (50 * (year -
	// ayanamsa.zeroDate) / 3600));
	// }
	public static ZodiacDegree asZodiacDegree(String[] words)
	{
		return asZodiacDegree(toWordArray(words));
	}

	public static ZodiacDegree asZodiacDegree(Word[] words)
	{
		ZodiacDegree result = asZodiacDegree(asNumber(words[0]));
		for (int i = 1; i < words.length; i++)
		{
			result = marry(result, asZodiacDegree(asNumber(words[i])));
		}
		return result;
	}

	public static ZodiacSign asZodiacSign(String[] words)
	{
		return asZodiacDegree(words).zodiacSign;
	}

	public static ZodiacSign asZodiacSign(Word[] words)
	{
		return asZodiacDegree(words).zodiacSign;
	}
}
