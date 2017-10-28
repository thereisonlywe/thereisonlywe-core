package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.LocaleEssentials;
import info.thereisonlywe.core.objects.CircularArray;
import info.thereisonlywe.core.objects.NumberRange;

public enum ZodiacSign
{
	ARIES(new NumberRange(0.0, 29.9999999999999), Element.FIRE,
		Modality.CARDINAL, Planet.MARS), TAURUS(new NumberRange(30.0,
		59.9999999999999), Element.EARTH, Modality.FIXED, Planet.VENUS), GEMINI(
		new NumberRange(60.0, 89.9999999999999), Element.AIR, Modality.MUTABLE,
		Planet.MERCURY), CANCER(new NumberRange(90.0, 119.9999999999999),
		Element.WATER, Modality.CARDINAL, Planet.MOON), LEO(new NumberRange(120.0,
		149.9999999999999), Element.FIRE, Modality.FIXED, Planet.SUN), VIRGO(
		new NumberRange(150.0, 179.9999999999999), Element.EARTH, Modality.MUTABLE,
		Planet.MERCURY), LIBRA(new NumberRange(180.0, 209.9999999999999),
		Element.AIR, Modality.CARDINAL, Planet.VENUS), SCORPIO(new NumberRange(
		210.0, 239.9999999999999), Element.WATER, Modality.FIXED, Planet.MARS), SAGITTARIUS(
		new NumberRange(240.0, 269.9999999999999), Element.FIRE, Modality.MUTABLE,
		Planet.JUPITER), CAPRICORN(new NumberRange(270.0, 299.9999999999999),
		Element.EARTH, Modality.CARDINAL, Planet.SATURN), AQUARIUS(new NumberRange(
		300.0, 329.9999999999999), Element.AIR, Modality.FIXED, Planet.SATURN), PISCES(
		new NumberRange(330.0, 359.9999999999999), Element.WATER, Modality.MUTABLE,
		Planet.JUPITER);
	public final NumberRange degreeSpan;
	public final Modality modality;
	public final Element element;
	public final Planet planet;

	private ZodiacSign(NumberRange range, Element element, Modality modality,
		Planet planet)
	{
		this.degreeSpan = range;
		this.modality = modality;
		this.element = element;
		this.planet = planet;
	}

	public String getName(String lang)
	{
		if (lang.equals("tr"))
		{
			if (this == ARIES) return "Koç";
			else if (this == TAURUS) return "Boğa";
			else if (this == GEMINI) return "İkizler";
			else if (this == CANCER) return "Yengeç";
			else if (this == LEO) return "Aslan";
			else if (this == VIRGO) return "Başak";
			else if (this == LIBRA) return "Terazi";
			else if (this == SCORPIO) return "Akrep";
			else if (this == SAGITTARIUS) return "Yay";
			else if (this == CAPRICORN) return "Oğlak";
			else if (this == AQUARIUS) return "Kova";
			else if (this == PISCES) return "Balık";
		}
		else
		{
			if (this == ARIES) return "Aries";
			else if (this == TAURUS) return "Taurus";
			else if (this == GEMINI) return "Gemini";
			else if (this == CANCER) return "Cancer";
			else if (this == LEO) return "Leo";
			else if (this == VIRGO) return "Virgo";
			else if (this == LIBRA) return "Libra";
			else if (this == SCORPIO) return "Scorpio";
			else if (this == SAGITTARIUS) return "Sagittarius";
			else if (this == CAPRICORN) return "Capricorn";
			else if (this == AQUARIUS) return "Aquarius";
			else if (this == PISCES) return "Pisces";
		}
		return null;
	}

	public ZodiacSign getNext()
	{
		final CircularArray<ZodiacSign> zs = new CircularArray<ZodiacSign>(
			ZodiacSign.values());
		zs.goTo(this);
		return zs.next();
	}

	public ZodiacSign getPrevious()
	{
		final CircularArray<ZodiacSign> zs = new CircularArray<ZodiacSign>(
			ZodiacSign.values());
		zs.goTo(this);
		return zs.previous();
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}

	public String toSymbolicZodiacString()
	{
		if (this == ARIES) return "♈";
		else if (this == TAURUS) return "♉";
		else if (this == GEMINI) return "♊";
		else if (this == CANCER) return "♋";
		else if (this == LEO) return "♌";
		else if (this == VIRGO) return "♍";
		else if (this == LIBRA) return "♎";
		else if (this == SCORPIO) return "♏";
		else if (this == SAGITTARIUS) return "♐";
		else if (this == CAPRICORN) return "♑";
		else if (this == AQUARIUS) return "♒";
		else if (this == PISCES) return "♓";
		return null;
	}
}