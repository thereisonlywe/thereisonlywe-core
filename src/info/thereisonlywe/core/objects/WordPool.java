package info.thereisonlywe.core.objects;

import java.util.ArrayList;
import java.util.HashSet;

public class WordPool {

	private final ArrayList<HashSet<String>>	pool;
	private final ArrayList<Character>			indices;

	public WordPool()
	{
		this.pool = new ArrayList<HashSet<String>>();
		this.indices = new ArrayList<Character>();
	}

	public synchronized void add(String word)
	{
		final Character c = Character.valueOf(word.charAt(0));
		final int index = this.indices.indexOf(c);
		if(index == -1)
		{
			this.indices.add(c);
			this.pool.add(new HashSet<String>());
			this.pool.get(this.indices.size() - 1).add(word);
		}
		else
		{
			this.pool.get(index).add(word);
		}
	}

	public void add(String[] words)
	{
		for(final String word: words)
		{
			add(word);
		}
	}

	public synchronized boolean contains(String word)
	{
		return this.pool.get(this.indices.indexOf(Character.valueOf(word.charAt(0)))).contains(word);
	}

	public boolean containsWordsLike(String word)
	{
		final Character c = Character.valueOf(word.charAt(0));
		final int index = this.indices.indexOf(c);
		if(index == -1) return false;
		else
		{
			for(final String str: this.pool.get(index))
			{
				if(str.startsWith(word)) return true;
			}
			return false;
		}
	}

	public ArrayList<String> getWordsLike(String word)
	{
		final Character c = Character.valueOf(word.charAt(0));
		final int index = this.indices.indexOf(c);
		if(index == -1) return null;
		else
		{
			final ArrayList<String> result = new ArrayList<String>();
			for(final String str: this.pool.get(index))
			{
				if(str.startsWith(word))
				{
					result.add(str);
				}
			}
			return result;
		}
	}

	public synchronized void remove(String word)
	{
		final Character c = Character.valueOf(word.charAt(0));
		final int index = this.indices.indexOf(c);
		this.pool.get(index).remove(word);
		if(this.pool.get(index).isEmpty())
		{
			this.pool.remove(index);
		}
	}
}
