package info.thereisonlywe.core.search;

public class SearchResult implements Comparable<SearchResult> {

	public static final int EXACT_MATCH = 100;
	public static final int CONTAINS_MATCH = 90;
	public static final int SIMILAR_EXACT_MATCH = 80;
	public static final int SIMILAR_CONTAINS_MATCH = 70;
	public static final int UNMATCHED = 0;

	private final String value;
	private final int strength;
	private final int index;

	public SearchResult(int index, String value, int strength)
	{
		this.index = index;
		this.value = value;
		this.strength = strength;
	}

	public int getStrength()
	{
		return this.strength;
	}

	public String getValue()
	{
		return this.value;
	}

	public int getIndex()
	{
		return this.index;
	}

	@Override
	public int compareTo(SearchResult other) {
		return Integer.compare(this.getStrength(), other.getStrength());
	}

}
