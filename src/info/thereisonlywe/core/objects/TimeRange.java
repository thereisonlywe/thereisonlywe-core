package info.thereisonlywe.core.objects;

import info.thereisonlywe.core.essentials.TimeEssentials;

public class TimeRange implements SequentialRange<String> {

	public final String	start;	// first value
	public final String	end;		// last value (inclusive)

	/**
	 * @param start
	 *           value (inclusive)
	 * @param end
	 *           value (inclusive)
	 */
	public TimeRange(String start, String end)
	{
		this.start = start;
		this.end = end;
	}

	public TimeRange(String fromToString)
	{
		int index = fromToString.indexOf(",");
		this.start = new String(fromToString.substring(1, index));
		this.end = new String(fromToString.substring(index + 2, fromToString.indexOf("]")));
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ", " + this.end + "]";
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.thereisonlywe.core.objects.SequentialRange#isInRange(java.lang.Object
	 * )
	 */
	@Override
	public boolean isInRange(String k)
	{
		if(TimeEssentials.getTimeDifference(this.start, k) >= 0 && TimeEssentials.getTimeDifference(k, this.end) >= 0)
			return true;
		return false;
	}

	public String getRange()
	{
		return TimeEssentials.formatTime(0, 0, TimeEssentials.getTimeDifference(this.start, this.end));
	}
}
