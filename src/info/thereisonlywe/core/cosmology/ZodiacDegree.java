package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.EsotericEssentials;
import info.thereisonlywe.core.essentials.StringEssentials;

public class ZodiacDegree
{
	public final double degree;
	public final ZodiacSign zodiacSign;
	public final double zodiacSignOffset;

	public ZodiacDegree(double deg)
	{
		this.degree = EsotericEssentials.normalizeZodiacDegree(deg);
		this.zodiacSign = EsotericEssentials.getZodiacSign(this.degree);
		this.zodiacSignOffset = this.degree - this.zodiacSign.degreeSpan.start;
	}

	public ZodiacDegree(ZodiacSign zs, double zsOffset)
	{
		this.degree = EsotericEssentials.normalizeZodiacDegree(zs.degreeSpan.start
			+ zsOffset);
		this.zodiacSign = EsotericEssentials.getZodiacSign(this.degree);
		this.zodiacSignOffset = this.degree - this.zodiacSign.degreeSpan.start;
	}

	public ZodiacDegree plus(ZodiacDegree zd)
	{
		return new ZodiacDegree(this.degree + zd.degree);
	}

	public ZodiacDegree plus(double deg)
	{
		return new ZodiacDegree(this.degree + deg);
	}

	public ZodiacDegree minus(ZodiacDegree zd)
	{
		return new ZodiacDegree(this.degree - zd.degree);
	}

	public ZodiacDegree minus(double deg)
	{
		return new ZodiacDegree(this.degree - deg);
	}

	public ZodiacDegree getOppositeZodiacDegree()
	{
		if (degree >= 180) return minus(180);
		else return plus(180);
	}

	public ZodiacDegree roundToNearestDegree()
	{
		return new ZodiacDegree(Math.round(this.degree));
	}

	@Override
	public String toString()
	{
		final int deg = (int) this.degree;
		final int offset = (int) this.zodiacSignOffset;
		String s, b;
		if (this.degree == deg)
		{
			s = deg + "\u00b0";
		}
		else
		{
			s = this.degree + "\u00b0";
		}
		b = offset + "\u00b0";
		return s + " (" + this.zodiacSign.toString() + " " + b + ")";
	}

	public String toZodiacString()
	{
		return this.zodiacSign.toString() + " " + (int) zodiacSignOffset + "\u00b0";
	}

	public String toSymbolicZodiacString()
	{
		return this.zodiacSign.toSymbolicZodiacString() + " "
			+ (int) zodiacSignOffset + "\u00b0";
	}

	public String toSymbolicZodiacStringWithPadding()
	{
		return this.zodiacSign.toSymbolicZodiacString() + " "
			+ StringEssentials.addPaddingToNumber((int) zodiacSignOffset, 2)
			+ "\u00b0";
	}
}
