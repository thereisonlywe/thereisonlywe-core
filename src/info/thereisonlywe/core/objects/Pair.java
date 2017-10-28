package info.thereisonlywe.core.objects;

public class Pair<T, X>
{
	// used for interchangeable pairs
	private T a;
	private X b;

	public Pair(T a, X b)
	{
		this.a = a;
		this.b = b;
	}

	public T getFirst()
	{
		return a;
	}

	public X getSecond()
	{
		return b;
	}

	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((a == null) ? 0 : a.hashCode());
		result = prime * result + ((b == null) ? 0 : b.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj)
	{
		if (this == obj) return true;
		if (obj == null) return false;
		if (getClass() != obj.getClass()) return false;
		return obj instanceof Pair && isEquivalentTo((Pair) obj);
	}

	public boolean isEqualTo(Pair t)
	{
		return this.a.equals(t.a) && this.b.equals(t.b);
	}

	public boolean isEquivalentTo(Pair t)
	{
		return (this.a.equals(t.a) && this.b.equals(t.b))
			|| (this.a.equals(t.b) && this.b.equals(t.a));
	}
}
