package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum Planet
{
	SATURN,
	JUPITER,
	MARS,
	SUN,
	VENUS,
	MERCURY,
	MOON,
	NORTH_NODE,
	SOUTH_NODE;

	public static Planet getPlanetFromIndex(int index)
	{
		return values()[index];
	}

	public boolean isDayPlanet()
	{
		return ordinal() == 0 || ordinal() == 3 || ordinal() == 1 || ordinal() == 5;
	}

	public boolean isNightPlanet()
	{
		return ordinal() == 2 || ordinal() == 4 || ordinal() == 6 || ordinal() == 5;
	}

	public Planet getNextPlanet()
	{
		return getNextPlanet(false);
	}

	public Planet getNextPlanet(boolean includeNodes)
	{
		if (!includeNodes) {
			if (ordinal() == MOON.ordinal()) {
				return SATURN;
			} else {
				return Planet.values()[ordinal() + 1];
			}
		}
		else {
			if (ordinal() == SOUTH_NODE.ordinal()) {
				return SATURN;
			} else {
				return Planet.values()[ordinal() + 1];
			}
		}
	}

	public String getName(String lang)
	{
		if(lang.equals("tr"))
		{
			if(this == SATURN) return "Satürn";
			else if(this == JUPITER) return "Jüpiter";
			else if(this == MARS) return "Mars";
			else if(this == SUN) return "Güneş";
			else if(this == VENUS) return "Venüs";
			else if(this == MERCURY) return "Merkür";
			else if(this == MOON) return "Ay";
			else if(this == NORTH_NODE) return "Kuzey Nodu";
			else if(this == SOUTH_NODE) return "Güney Nodu";
		}
		else
		{
			if(this == SATURN) return "Saturn";
			else if(this == JUPITER) return "Jupiter";
			else if(this == MARS) return "Mars";
			else if(this == SUN) return "Sun";
			else if(this == VENUS) return "Venus";
			else if(this == MERCURY) return "Mercury";
			else if(this == MOON) return "Moon";
			else if(this == NORTH_NODE) return "North Node";
			else if(this == SOUTH_NODE) return "South Node";
		}
		return null;
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}

	public String toSymbolString()
	{
		if(this == SATURN) return "\u2644";
		else if(this == JUPITER) return "\u2643";
		else if(this == MARS) return "\u2642";
		else if(this == SUN) return "\u2609";
		else if(this == VENUS) return "\u2640";
		else if(this == MERCURY) return "\u263F";
		else if(this == MOON) return "\u263D";
		else if(this == NORTH_NODE) return "\u260A";
		else if(this == SOUTH_NODE) return "\u260B";
		return null;
	}
}