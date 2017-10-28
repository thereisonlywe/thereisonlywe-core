package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.LocaleEssentials;

public enum ZodiacAspect
{
	CONJUNCTION(0.0),
	SEXTILE(60.0),
	SQUARE(90.0),
	TRINE(120.0),
	OPPOSITION(180.0),
	QUINCUNX(150.0),
	SEMI_SQUARE(45.0),
	SESQUI_QUADRATE(135.0),
	SEMI_SEXTILE(30.0),
	QUINTILE(72.0),
	BI_QUINTILE(144.0),
	SEPTILE(51.428571),
	BI_SEPTILE(102.857143),
	TRI_SEPTILE(154.285714),
	NOVILE(40.0),
	BI_NOVILE(80.0),
	QUAD_NOVILE(160.0),
	DECILE(36.0),
	TRI_DECILE(108.0),
	VIGINTILE(18.0),
	UN_DECILE(32.727272),
	BI_UN_DECILE(65.454545),
	TRI_UN_DECILE(98.181816),
	QUADRI_UN_DECILE(130.90909),
	QUINQUE_UN_DECILE(163.636363),
	SEMI_SEPTILE(25.714286),
	TRE_SEMI_SEPTILE(77.142858),
	QUIN_SEMI_SEPTILE(128.57143),
	SEMI_OCTILE(22.5),
	SESQUI_OCTILE(67.5),
	QUASQUI_QUADRATE(112.5),
	SEPTEMSE_DECIMAL(157.5),
	QUATTUOR_VIGINTILE(15.0),
	SQUILE(75.0),
	QUIN_DECILE(165.0);

	public final double angle;

	public static final ZodiacAspect[] ASPECTS = new ZodiacAspect[]{CONJUNCTION, SEXTILE, SQUARE, TRINE, OPPOSITION,
			QUINCUNX, SEMI_SQUARE, SESQUI_QUADRATE, SEMI_SEXTILE, QUINTILE, BI_QUINTILE, SEPTILE, BI_SEPTILE, TRI_SEPTILE,
			NOVILE, BI_NOVILE, QUAD_NOVILE, DECILE, TRI_DECILE, VIGINTILE, UN_DECILE, BI_UN_DECILE, TRI_UN_DECILE,
			QUADRI_UN_DECILE, QUINQUE_UN_DECILE, SEMI_SEPTILE, TRE_SEMI_SEPTILE, QUIN_SEMI_SEPTILE, SEMI_OCTILE,
			SESQUI_OCTILE, QUASQUI_QUADRATE, SEPTEMSE_DECIMAL, QUATTUOR_VIGINTILE, SQUILE, QUIN_DECILE};

	public static final ZodiacAspect[] ASPECTS_MAJOR = new ZodiacAspect[]{CONJUNCTION, SEXTILE, SQUARE, TRINE,
			OPPOSITION};

	public static final ZodiacAspect[] ASPECTS_MINOR = new ZodiacAspect[]{QUINCUNX, SEMI_SQUARE, SESQUI_QUADRATE,
			SEMI_SEXTILE, QUINTILE, BI_QUINTILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC = new ZodiacAspect[]{SEPTILE, BI_SEPTILE, TRI_SEPTILE, NOVILE,
			BI_NOVILE, QUAD_NOVILE, DECILE, TRI_DECILE, VIGINTILE, UN_DECILE, BI_UN_DECILE, TRI_UN_DECILE,
			QUADRI_UN_DECILE, QUINQUE_UN_DECILE, SEMI_SEPTILE, TRE_SEMI_SEPTILE, QUIN_SEMI_SEPTILE, SEMI_OCTILE,
			SESQUI_OCTILE, QUASQUI_QUADRATE, SEPTEMSE_DECIMAL, QUATTUOR_VIGINTILE, SQUILE, QUIN_DECILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_7TH = new ZodiacAspect[]{SEPTILE, BI_SEPTILE, TRI_SEPTILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_9TH = new ZodiacAspect[]{NOVILE, BI_NOVILE, QUAD_NOVILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_10TH = new ZodiacAspect[]{DECILE, TRI_DECILE, VIGINTILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_11TH = new ZodiacAspect[]{UN_DECILE, BI_UN_DECILE,
			TRI_UN_DECILE, QUADRI_UN_DECILE, QUINQUE_UN_DECILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_14TH = new ZodiacAspect[]{SEMI_SEPTILE, TRE_SEMI_SEPTILE,
			QUIN_SEMI_SEPTILE};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_16TH = new ZodiacAspect[]{SEMI_OCTILE, SESQUI_OCTILE,
			QUASQUI_QUADRATE, SEPTEMSE_DECIMAL};

	public static final ZodiacAspect[] ASPECTS_HARMONIC_24TH = new ZodiacAspect[]{QUATTUOR_VIGINTILE, SQUILE,
			QUIN_DECILE};

	private ZodiacAspect(double angle)
	{
		this.angle = angle;
	}

	public String getName(String lang)
	{
		if(lang.equals("tr"))
		{
			if(this == CONJUNCTION) return "Kavuşum";
			else if(this == OPPOSITION) return "Karşıt";
			else if(this == TRINE) return "Üçgen";
			else if(this == SQUARE) return "Kare";
			else if(this == SEXTILE) return "Sekstil";
			else if(this == SEMI_SQUARE) return "Yarım Kare";
			else if(this == SEMI_SEXTILE) return "Yarım Sekstil";
			else return this.angle + " derece";
		}
		else
		{
			if (this == CONJUNCTION) return "Conjunction";
			else if(this == OPPOSITION) return "Opposition";
			else if(this == TRINE) return "Trine";
			else if(this == SQUARE) return "Square";
			else if(this == SEXTILE) return "Sextile";
			else if(this == QUINCUNX) return "Quincunx";
			else if(this == SEMI_SQUARE) return "Semi-square";
			else if(this == SEMI_SEXTILE) return "Semi-sextile";
			else if(this == SESQUI_QUADRATE) return "Sesqui-quadrate";
			else if(this == QUINTILE) return "Quintule";
			else if(this == BI_QUINTILE) return "Bi-quintile";
			else return this.angle + " degrees";
		}
	}

	public String toSymbolicString()
	{
		if(this == CONJUNCTION) return "\u260C";
		else if(this == OPPOSITION) return "\u260D";
		else if(this == TRINE) return "\u25B3";
		else if(this == SQUARE) return "\u25A1";
		else if(this == SEXTILE) return "\u2731";
		else return "?";
	}

	public boolean is10thHarmonicAspect()
	{
		if(this == DECILE || this == TRI_DECILE || this == VIGINTILE) return true;
		return false;
	}

	public boolean is11thHarmonicAspect()
	{
		if(this == UN_DECILE || this == BI_UN_DECILE || this == TRI_UN_DECILE || this == QUADRI_UN_DECILE
				|| this == QUINQUE_UN_DECILE) return true;
		return false;
	}

	public boolean is14thHarmonicAspect()
	{
		if(this == SEMI_SEPTILE || this == TRE_SEMI_SEPTILE || this == QUIN_SEMI_SEPTILE) return true;
		return false;
	}

	public boolean is16thHarmonicAspect()
	{
		if(this == SEMI_OCTILE || this == SESQUI_OCTILE || this == QUASQUI_QUADRATE || this == SEPTEMSE_DECIMAL)
			return true;
		return false;
	}

	public boolean is24thHarmonicAspect()
	{
		if(this == QUATTUOR_VIGINTILE || this == SQUILE || this == QUIN_DECILE) return true;
		return false;
	}

	public boolean is7thHarmonicAspect()
	{
		if(this == SEPTILE || this == BI_SEPTILE || this == TRI_SEPTILE) return true;
		return false;
	}

	public boolean is9thHarmonicAspect()
	{
		if(this == NOVILE || this == BI_NOVILE || this == QUAD_NOVILE) return true;
		return false;
	}

	public boolean isHarmonicAspect()
	{
		if(!isMajorAspect() && !isMinorAspect()) return true;
		return false;
	}

	public boolean isMajorAspect()
	{
		if(this == CONJUNCTION || this == OPPOSITION || this == SQUARE || this == TRINE || this == SEXTILE) return true;
		return false;
	}

	public boolean isMinorAspect()
	{
		if(this == QUINCUNX || this == SEMI_SQUARE || this == SESQUI_QUADRATE || this == SEMI_SEXTILE || this == QUINTILE
				|| this == BI_QUINTILE) return true;
		return false;
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}
}
