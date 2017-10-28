package info.thereisonlywe.core.objects;

import info.thereisonlywe.core.cosmology.ZodiacDegree;

public class ZodiacDegreeRange implements SequentialRange<ZodiacDegree> {

	public final ZodiacDegree start; // first value (inclusive)
	public final ZodiacDegree end; // last value (inclusive)

	/**
	 * @param start
	 *           value (inclusive)
	 * @param end
	 *           value (inclusive)
	 */
	public ZodiacDegreeRange(ZodiacDegree start, ZodiacDegree end)
	{
		this.start = start;
		this.end = end;
	}

	/**
	 * @param start
	 *           value (inclusive)
	 * @param end
	 *           value (inclusive)
	 */
	public ZodiacDegreeRange(Double start, Double end)
	{
		this.start = new ZodiacDegree(start);
		this.end = new ZodiacDegree(end);
	}

	public ZodiacDegreeRange(NumberRange range)
	{
		this.start = new ZodiacDegree(range.start);
		this.end = new ZodiacDegree(range.end);
	}

	@Override
	public String toString()
	{
		return "[" + this.start + ", " + this.end + "]";
	}

	public Double getRange()
	{
		final Double start = Math.min(this.start.degree, this.end.degree);
		final Double end = Math.max(this.start.degree, this.end.degree);
		return end - start;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * info.thereisonlywe.core.objects.SequentialRange#isInRange(java.lang.Object
	 * )
	 */
	@Override
	public boolean isInRange(ZodiacDegree k)
	{
		final Double start = Math.min(this.start.degree, this.end.degree);
		final Double end = Math.max(this.start.degree, this.end.degree);
		if(k.degree >= start && k.degree <= end) return true;
		return false;
	}
}
