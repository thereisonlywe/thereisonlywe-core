package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum Heaven
{
	_1, _2, _3, _4, _5, _6, _7;
	public String getName(String lang)
	{
		if (lang.equals("tr"))
		{
			if (this == _1) return "1. Gök";
			else if (this == _2) return "2. Gök";
			else if (this == _3) return "3. Gök";
			else if (this == _4) return "4. Gök";
			else if (this == _5) return "5. Gök";
			else if (this == _6) return "6. Gök";
			else if (this == _7) return "7. Gök";
		}
		else
		{
			if (this == _1) return "1st Heaven";
			else if (this == _2) return "2nd Heaven";
			else if (this == _3) return "3rd Heaven";
			else if (this == _4) return "4th Heaven";
			else if (this == _5) return "5th Heaven";
			else if (this == _6) return "6th Heaven";
			else if (this == _7) return "7th Heaven";
		}
		return null;
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}
}