package info.thereisonlywe.core.objects;

import java.util.HashSet;

public class Pairs<T>
{
	// used for interchangeable pairs
	private HashSet<Pair> pairs;

	public Pairs()
	{
		pairs = new HashSet<Pair>();
	}

	public void add(Pair p)
	{
		pairs.add(p);
	}

	public HashSet<T> getDirect(T t)
	{
		HashSet<T> result = new HashSet<T>();
		for (Pair p : pairs)
		{
			if (p.getFirst().equals(t)) result.add((T) p.getSecond());
			else if (p.getSecond().equals(t)) result.add((T) p.getFirst());
		}
		return result;
	}

	public HashSet<T> getRelatives(T t)
	{
		return getRelatives(t, null);
	}

	private HashSet<T> getRelatives(T t, HashSet<T> result)
	{
		result = (result == null ? new HashSet<T>() : result);
		for (Pair p : pairs)
		{
			if (p.getFirst().equals(t) && !result.contains(p.getSecond()))
			{
				result.add((T) p.getSecond());
				getRelatives((T) p.getSecond(), result);
			}
			else if (p.getSecond().equals(t) && !result.contains(p.getFirst()))
			{
				result.add((T) p.getFirst());
				getRelatives((T) p.getFirst(), result);
			}
		}
		return result;
	}
}
