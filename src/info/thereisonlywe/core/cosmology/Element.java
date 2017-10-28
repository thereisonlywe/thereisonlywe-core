package info.thereisonlywe.core.cosmology;


import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum Element
{
	AIR(Temperament.MOIST_HOT), WATER(Temperament.MOIST_COLD), FIRE(
		Temperament.DRY_HOT), EARTH(Temperament.DRY_COLD);
	public final Temperament temperament;

	private Element(Temperament t)
	{
		this.temperament = t;
	}

	public String getName(String lang)
	{
		if (lang.equals("tr"))
		{
			switch (ordinal())
			{
				case 0:
					return "Hava";
				case 1:
					return "Su";
				case 2:
					return "Ateş";
				case 3:
					return "Toprak";
			}
		}
		else
		{
			switch (ordinal())
			{
				case 0:
					return "Air";
				case 1:
					return "Water";
				case 2:
					return "Fire";
				case 3:
					return "Earth";
			}
		}
		return null;
	}

	public Element getOpposite()
	{
		switch (ordinal())
		{
			case 0:
				return EARTH;
			case 1:
				return FIRE;
			case 2:
				return WATER;
			case 3:
				return AIR;
			default:
				return null;
		}
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}
}