package info.thereisonlywe.core.objects;

public class CircularArray<T> {

	private final T[]		array;

	private volatile int	index	= 0;

	// Use this constructor to copy a CircularArray object
	public CircularArray(CircularArray<T> another)
	{
		this.array = another.array;
		this.index = another.index;
	}

	public CircularArray(T[] array)
	{
		this.array = array;
	}

	public synchronized T current()
	{
		return this.array[this.index];
	}

	public synchronized int currentIndex()
	{
		return this.index;
	}

	public synchronized T get(int index)
	{
		return this.array[index];
	}

	public synchronized void goTo(int index)
	{
		if(index >= this.array.length)
		{
			index = this.array.length - index;
		}
		this.index = index;
	}

	public synchronized void goTo(T t)
	{
		for(int i = 0; i < this.array.length; i++)
		{
			if(t == this.array[i] || t.equals(this.array[i]))
			{
				this.index = i;
				break;
			}
		}
	}

	public synchronized int indexOf(T t)
	{
		for(int i = 0; i < this.array.length; i++)
		{
			if(t == this.array[i] || t.equals(this.array[i])) return i;
		}
		return -1;
	}

	public synchronized T next()
	{
		this.index++;
		if(this.index >= this.array.length)
		{
			this.index = this.array.length - this.index;
		}
		return this.array[this.index];
	}

	public synchronized T previous()
	{
		this.index--;
		if(this.index < 0)
		{
			this.index = this.array.length - 1;
		}
		return this.array[this.index];
	}

	public int size()
	{
		return this.array.length;
	}
}
