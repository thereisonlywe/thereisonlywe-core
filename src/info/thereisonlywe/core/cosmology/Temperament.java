package info.thereisonlywe.core.cosmology;


import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum Temperament
{
	MOIST_HOT,
	MOIST_COLD,
	DRY_HOT,
	DRY_COLD;

	public String getName(String lang)
	{
		if(lang.equals("tr"))
		{
			if(this == MOIST_HOT) return "Nemli Sıcak";
			else if(this == MOIST_COLD) return "Nemli Soğuk";
			else if(this == DRY_HOT) return "Kuru Sıcak";
			else if(this == DRY_COLD) return "Kuru Soğuk";
		}
		else
		{
			if(this == MOIST_HOT) return "Moist & Hot";
			else if(this == MOIST_COLD) return "Moist & Cold";
			else if(this == DRY_HOT) return "Dry & Hot";
			else if(this == DRY_COLD) return "Dry & Cold";
		}
		return null;
	}

	public boolean isCold()
	{
		return !isHot();
	}

	public boolean isDry()
	{
		return !isMoist();
	}

	public boolean isHot()
	{
		if(this == MOIST_HOT || this == DRY_HOT) return true;
		return false;
	}

	public boolean isMoist()
	{
		if(this == MOIST_COLD || this == MOIST_HOT) return true;
		return false;
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}
}