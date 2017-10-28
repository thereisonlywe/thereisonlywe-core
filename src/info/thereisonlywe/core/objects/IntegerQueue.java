package info.thereisonlywe.core.objects;

import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

//FIFO
public class IntegerQueue extends LinkedList<Integer> implements Queue<Integer>
{
	private final int maxSize;

	public IntegerQueue(String representation)
	{
		maxSize = -1;
		if (representation == null || representation.trim().length() == 0) return;
		String temp[] = representation.split(";");
		for (int i = 0; i < temp.length; i++)
		{
			add(Integer.parseInt(temp[i]));
		}
	}

	public IntegerQueue(String representation, int maxSize)
	{
		this.maxSize = maxSize;
		if (representation == null || representation.trim().length() == 0) return;
		String temp[] = representation.split(";");
		for (int i = 0; i < Math.min(maxSize, temp.length); i++)
		{
			add(Integer.parseInt(temp[i]));
		}
	}

	public String asString(int maxSize)
	{
		if (size() == 0) return "";
		String s = "";
		for (int i = 0; i < (maxSize < 0 ? size() : Math.min(maxSize, size())); i++)
		{
			s += String.valueOf(get(i));
			if (i + 1 < size()) s += ";";
		}
		return s;
	}

	public String asString()
	{
		return asString(maxSize);
	}

	@Override
	public boolean add(Integer i)
	{
		add(i, maxSize, true);
		return true;
	}

	public void add(Integer i, boolean allowDuplicates)
	{
		add(i, maxSize, allowDuplicates);
	}

	public void add(Integer i, int maxSize)
	{
		add(i, maxSize, true);
	}

	public void add(Integer i, int maxSize, boolean allowDuplicates)
	{
		if (!allowDuplicates)
		{
			int index = indexOf(i);
			if (index > -1) remove(index);
		}
		while (maxSize >= 1 && size() >= maxSize)
			pollFirst();
		super.add(i);
	}

	@Override
	public boolean addAll(Collection<? extends Integer> arg0)
	{
		return super.addAll(arg0);
	}

	@Override
	public void clear()
	{
		super.clear();
	}

	@Override
	public boolean contains(Object arg0)
	{
		return super.contains(arg0);
	}

	@Override
	public boolean containsAll(Collection<?> arg0)
	{
		return super.containsAll(arg0);
	}

	@Override
	public boolean isEmpty()
	{
		return super.isEmpty();
	}

	@Override
	public Iterator<Integer> iterator()
	{
		return super.iterator();
	}

	@Override
	public boolean remove(Object arg0)
	{
		return super.remove(arg0);
	}

	@Override
	public boolean removeAll(Collection<?> arg0)
	{
		return super.removeAll(arg0);
	}

	@Override
	public boolean retainAll(Collection<?> arg0)
	{
		return super.retainAll(arg0);
	}

	@Override
	public int size()
	{
		return super.size();
	}

	@Override
	public Object[] toArray()
	{
		return super.toArray();
	}

	@Override
	public <T> T[] toArray(T[] arg0)
	{
		return super.toArray(arg0);
	}

	// @Override
	// public boolean add(Integer arg0)
	// {
	// return super.add(arg0);
	// }
	@Override
	public Integer element()
	{
		return super.element();
	}

	@Override
	public boolean offer(Integer arg0)
	{
		return super.offer(arg0);
	}

	@Override
	public Integer peek()
	{
		return super.peek();
	}

	@Override
	public Integer poll()
	{
		return super.poll();
	}

	@Override
	public Integer remove()
	{
		return super.remove();
	}
}
