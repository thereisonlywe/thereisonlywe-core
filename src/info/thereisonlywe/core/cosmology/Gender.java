package info.thereisonlywe.core.cosmology;


import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum Gender
{
	MALE,
	FEMALE;

	public String getName(String lang)
	{
		if(lang.equals("tr"))
		{
			if(this == MALE) return "Erkek";
			else return "Dişi";
		}
		else
		{
			if(this == MALE) return "Male";
			else return "Female";
		}
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}

}