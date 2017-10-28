package info.thereisonlywe.core.cosmology;


public enum Letter
{
	ء(Element.FIRE, null, false, true, false, false, true, false), ه(
		Element.EARTH, Element.AIR, true, false, true, true, false, false), ع(
		Element.AIR, null, false, false, false, false, true, false), ح(
		Element.WATER, null, true, false, true, true, false, true), غ(
		Element.WATER, null, true, false, false, false, false, false), خ(
		Element.AIR, Element.EARTH, true, false, false, false, false, true), ق(
		Element.WATER, Element.FIRE, false, false, true, false, false, false), ك(
		Element.FIRE, null, true, false, false, false, true, true), ج(
		Element.EARTH, Element.FIRE, false, false, true, false, false, true), ش(
		Element.WATER, null, true, false, false, false, false, false), ي(
		Element.FIRE, Element.WATER, false, false, true, false, false, false), ض(
		Element.WATER, null, false, false, false, false, false, false), ل(
		Element.FIRE, Element.EARTH, false, false, true, true, false, false), ن(
		Element.EARTH, null, false, false, false, false, false, false), ر(
		Element.FIRE, null, false, true, false, true, false, false), ط(
		Element.WATER, null, false, false, false, true, false, false), د(
		Element.EARTH, null, false, true, false, true, false, false), ت(
		Element.EARTH, null, true, false, false, false, false, false), ز(
		Element.FIRE, null, false, true, true, false, false, false), س(
		Element.FIRE, null, false, false, false, false, true, false), ص(
		Element.AIR, null, true, false, false, true, false, false), ظ(
		Element.WATER, Element.AIR, false, false, false, false, false, false), ث(
		Element.EARTH, null, true, false, false, false, false, false), ذ(
		Element.AIR, null, false, true, false, false, false, false), ف(
		Element.WATER, Element.AIR, true, false, true, false, false, false), ب(
		Element.FIRE, null, false, false, false, false, false, false), م(
		Element.EARTH, null, false, false, false, false, true, false), و(
		Element.AIR, null, false, true, false, true, false, false);
	public final Element majorElement;
	public final Element minorElement;
	public final boolean isComposite;
	public final boolean isHidden;
	public final boolean isHoly;
	public final boolean isObedient;
	public final boolean isHighlyIdentifiedWithOneness;
	public final boolean isMildlyIdentifiedWithOneness;
	public final boolean isDispatched;

	private Letter(Element majorElement, Element minorElement, boolean isHidden,
		boolean isHoly, boolean isObedient, boolean isHighlyIdentifiedWithOneness,
		boolean isMildlyIdentifiedWithOneness, boolean isDispatched)
	{
		this.majorElement = majorElement;
		this.minorElement = null;
		this.isComposite = minorElement != null;
		this.isHidden = isHidden;
		this.isHoly = isHoly;
		this.isObedient = isObedient;
		this.isHighlyIdentifiedWithOneness = isHighlyIdentifiedWithOneness;
		this.isMildlyIdentifiedWithOneness = isMildlyIdentifiedWithOneness;
		this.isDispatched = isDispatched;
	}

	public static Letter valueOf(char c)
	{
		for (Letter l : Letter.values())
		{
			if (l.name().charAt(0) == c)
			{
				return l;
			}
			else if (c == 'ا')
			{
				return Letter.ء;
			}
			else if (c == 'ة')
			{
				return Letter.ه;
			}
			else if (c == 'ى') { return Letter.ي; }
		}
		return null;
	}

	public static boolean isLetter(char c)
	{
		return valueOf(c) != null;
	}

	@Override
	public String toString()
	{
		if (ordinal() == 0) return "ا";
		else return this.name();
	}
}