package info.thereisonlywe.core.objects;

public class NumberRange implements SequentialRange<Double>
{
	public final Double start; // first value (inclusive)
	public final Double end; // last value (inclusive)
	public final Double mid;

	/**
	 * @param start
	 *          value (inclusive)
	 * @param end
	 *          value (inclusive)
	 */
	public NumberRange(Double start, Double end)
	{
		this.start = start;
		this.end = end;
		this.mid = (start + end) / 2;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.thereisonlywe.core.objects.SequentialRange#isInRange(java.lang.Object
	 * )
	 */
	@Override
	public boolean isInRange(Double k)
	{
		final Double start = Math.min(this.start, this.end);
		final Double end = Math.max(this.start, this.end);
		if (k >= start && k <= end) return true;
		return false;
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ", " + this.end + "]";
	}

	public Double getRange()
	{
		final Double start = Math.min(this.start, this.end);
		final Double end = Math.max(this.start, this.end);
		return end - start;
	}
}
