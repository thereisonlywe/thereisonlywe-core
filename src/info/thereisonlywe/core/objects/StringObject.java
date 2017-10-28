package info.thereisonlywe.core.objects;

import info.thereisonlywe.core.essentials.StringEssentials;

public class StringObject
{
	private String string = "";

	public void append(long l)
	{
		string += string.equals("") ? "" : "\n";
		string += "[long]";
		string += l;
	}

	public void append(int l)
	{
		string += string.equals("") ? "" : "\n";
		string += "[int]";
		string += l;
	}

	public void append(double l)
	{
		string += string.equals("") ? "" : "\n";
		string += "[double]";
		string += l;
	}

	public void append(String[] s)
	{
		string += string.equals("") ? "" : "\n";
		string += "[String Array]";
		string += StringEssentials.glue(s,
			StringEssentials.THEREISONLYWE_VALUE_SEPARATOR);
	}

	public void append(int[] s)
	{
		string += string.equals("") ? "" : "\n";
		string += "[Int Array]";
		string += StringEssentials.glue(s,
			StringEssentials.THEREISONLYWE_VALUE_SEPARATOR);
	}

	public void append(Integer[] s)
	{
		string += string.equals("") ? "" : "\n";
		string += "[Integer Array]";
		string += StringEssentials.glue(s,
			StringEssentials.THEREISONLYWE_VALUE_SEPARATOR);
	}

	@Override
	public String toString()
	{
		return string;
	}
}
