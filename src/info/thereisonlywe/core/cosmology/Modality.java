package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum Modality
{
	CARDINAL, FIXED, MUTABLE;
	public String getName(String lang)
	{
		if (lang.equals("tr"))
		{
			if (this == CARDINAL) return "Öncü";
			else if (this == FIXED) return "Sabit";
			else if (this == MUTABLE) return "Değişebilir";
		}
		else
		{
			if (this == CARDINAL) return "Cardinal";
			else if (this == FIXED) return "Fixed";
			else if (this == MUTABLE) return "Mutable";
		}
		return null;
	}

	public String getAssociation()
	{
		return getAssociation(LocaleEssentials.LANGUAGE_DEFAULT);
	}

	public String getAssociation(String lang)
	{
		if (lang.equals("tr"))
		{
			if (this == CARDINAL) return "Zihin";
			else if (this == FIXED) return "Ruh";
			else if (this == MUTABLE) return "Beden";
		}
		else
		{
			if (this == CARDINAL) return "Mind";
			else if (this == FIXED) return "Spirit";
			else if (this == MUTABLE) return "Body";
		}
		return null;
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}
}