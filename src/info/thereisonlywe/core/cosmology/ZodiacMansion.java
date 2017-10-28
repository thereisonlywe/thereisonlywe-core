package info.thereisonlywe.core.cosmology;

import info.thereisonlywe.core.essentials.LocaleEssentials;
import info.thereisonlywe.core.objects.NumberRange;

public enum ZodiacMansion
{
	_1(new NumberRange(360 / 28.0 * (1 - 1),
		360 / 28.0 * (2 - 1) - 0.0000000000001)), _2(new NumberRange(
		360 / 28.0 * (2 - 1), 360 / 28.0 * (3 - 1) - 0.0000000000001)), _3(
		new NumberRange(360 / 28.0 * (3 - 1),
			360 / 28.0 * (4 - 1) - 0.0000000000001)), _4(new NumberRange(
		360 / 28.0 * (4 - 1), 360 / 28.0 * (5 - 1) - 0.0000000000001)), _5(
		new NumberRange(360 / 28.0 * (5 - 1),
			360 / 28.0 * (6 - 1) - 0.0000000000001)), _6(new NumberRange(
		360 / 28.0 * (6 - 1), 360 / 28.0 * (7 - 1) - 0.0000000000001)), _7(
		new NumberRange(360 / 28.0 * (7 - 1),
			360 / 28.0 * (8 - 1) - 0.0000000000001)), _8(new NumberRange(
		360 / 28.0 * (8 - 1), 360 / 28.0 * (9 - 1) - 0.0000000000001)), _9(
		new NumberRange(360 / 28.0 * (9 - 1),
			360 / 28.0 * (10 - 1) - 0.0000000000001)), _10(new NumberRange(
		360 / 28.0 * (10 - 1), 360 / 28.0 * (11 - 1) - 0.0000000000001)), _11(
		new NumberRange(360 / 28.0 * (11 - 1),
			360 / 28.0 * (12 - 1) - 0.0000000000001)), _12(new NumberRange(
		360 / 28.0 * (12 - 1), 360 / 28.0 * (13 - 1) - 0.0000000000001)), _13(
		new NumberRange(360 / 28.0 * (13 - 1),
			360 / 28.0 * (14 - 1) - 0.0000000000001)), _14(new NumberRange(
		360 / 28.0 * (14 - 1), 360 / 28.0 * (15 - 1) - 0.0000000000001)), _15(
		new NumberRange(360 / 28.0 * (15 - 1),
			360 / 28.0 * (16 - 1) - 0.0000000000001)), _16(new NumberRange(
		360 / 28.0 * (16 - 1), 360 / 28.0 * (17 - 1) - 0.0000000000001)), _17(
		new NumberRange(360 / 28.0 * (17 - 1),
			360 / 28.0 * (18 - 1) - 0.0000000000001)), _18(new NumberRange(
		360 / 28.0 * (18 - 1), 360 / 28.0 * (19 - 1) - 0.0000000000001)), _19(
		new NumberRange(360 / 28.0 * (19 - 1),
			360 / 28.0 * (20 - 1) - 0.0000000000001)), _20(new NumberRange(
		360 / 28.0 * (20 - 1), 360 / 28.0 * (21 - 1) - 0.0000000000001)), _21(
		new NumberRange(360 / 28.0 * (21 - 1),
			360 / 28.0 * (22 - 1) - 0.0000000000001)), _22(new NumberRange(
		360 / 28.0 * (22 - 1), 360 / 28.0 * (23 - 1) - 0.0000000000001)), _23(
		new NumberRange(360 / 28.0 * (23 - 1),
			360 / 28.0 * (24 - 1) - 0.0000000000001)), _24(new NumberRange(
		360 / 28.0 * (24 - 1), 360 / 28.0 * (25 - 1) - 0.0000000000001)), _25(
		new NumberRange(360 / 28.0 * (25 - 1),
			360 / 28.0 * (26 - 1) - 0.0000000000001)), _26(new NumberRange(
		360 / 28.0 * (26 - 1), 360 / 28.0 * (27 - 1) - 0.0000000000001)), _27(
		new NumberRange(360 / 28.0 * (27 - 1),
			360 / 28.0 * (28 - 1) - 0.0000000000001)), _28(new NumberRange(
		360 / 28.0 * (28 - 1), 360 / 28.0 * (29 - 1) - 0.0000000000001));
	public final NumberRange degreeSpan;
	public static final double DEGREE_SPAN_PER_MANSION = 360 / 28.0 * (2 - 1) - 0.0000000000001;

	private ZodiacMansion(NumberRange range)
	{
		this.degreeSpan = range;
	}

	public String getName(String lang)
	{
		if (lang.equals("tr"))
		{
			switch (ordinal())
			{
				case 0:
					return "Şerateyn" + " (" + (ordinal() + 1) + ")";
				case 1:
					return "Buteyn" + " (" + (ordinal() + 1) + ")";
				case 2:
					return "Süreyya" + " (" + (ordinal() + 1) + ")";
				case 3:
					return "Deberan" + " (" + (ordinal() + 1) + ")";
				case 4:
					return "Hak'a" + " (" + (ordinal() + 1) + ")";
				case 5:
					return "Hen'a" + " (" + (ordinal() + 1) + ")";
				case 6:
					return "Zir'a" + " (" + (ordinal() + 1) + ")";
				case 7:
					return "Natr'a" + " (" + (ordinal() + 1) + ")";
				case 8:
					return "Tarf" + " (" + (ordinal() + 1) + ")";
				case 9:
					return "Ceb'ha" + " (" + (ordinal() + 1) + ")";
				case 10:
					return "Zubra" + " (" + (ordinal() + 1) + ")";
				case 11:
					return "Sarfa" + " (" + (ordinal() + 1) + ")";
				case 12:
					return "Avva" + " (" + (ordinal() + 1) + ")";
				case 13:
					return "Simak" + " (" + (ordinal() + 1) + ")";
				case 14:
					return "Gafr" + " (" + (ordinal() + 1) + ")";
				case 15:
					return "Zubana" + " (" + (ordinal() + 1) + ")";
				case 16:
					return "İklil Ceb'ha" + " (" + (ordinal() + 1) + ")";
				case 17:
					return "Kalp" + " (" + (ordinal() + 1) + ")";
				case 18:
					return "Şevlâ" + " (" + (ordinal() + 1) + ")";
				case 19:
					return "Neaem" + " (" + (ordinal() + 1) + ")";
				case 20:
					return "Belde" + " (" + (ordinal() + 1) + ")";
				case 21:
					return "Saad-ul Zebh" + " (" + (ordinal() + 1) + ")";
				case 22:
					return "Saad-ul Bula" + " (" + (ordinal() + 1) + ")";
				case 23:
					return "Saad-us Suud" + " (" + (ordinal() + 1) + ")";
				case 24:
					return "Saad-ul Ahbiya" + " (" + (ordinal() + 1) + ")";
				case 25:
					return "Ferğ-ul Mukdim" + " (" + (ordinal() + 1) + ")";
				case 26:
					return "Ferğ-ul Muhar" + " (" + (ordinal() + 1) + ")";
				default:
					return "Batın-ul Hut" + " (" + (ordinal() + 1) + ")";
			}
		}
		else if (lang.equals("ar"))
		{
			switch (ordinal())
			{
				case 0:
					return "أَلْشَّرَطَيْن" + " (" + (ordinal() + 1) + ")";
				case 1:
					return "أَلْبُطَيْن" + " (" + (ordinal() + 1) + ")";
				case 2:
					return "أَلْثُّرَيَّا" + " (" + (ordinal() + 1) + ")";
				case 3:
					return "أَلْدَّبَرَان" + " (" + (ordinal() + 1) + ")";
				case 4:
					return "أَلْهَقْعَة" + " (" + (ordinal() + 1) + ")";
				case 5:
					return "أَلْهَنْعَة" + " (" + (ordinal() + 1) + ")";
				case 6:
					return "أَلْذِّرَاعْ" + " (" + (ordinal() + 1) + ")";
				case 7:
					return "أَلْنَّثْرَة" + " (" + (ordinal() + 1) + ")";
				case 8:
					return "أَلْطَّرْف" + " (" + (ordinal() + 1) + ")";
				case 9:
					return "أَلْجَبْهَة" + " (" + (ordinal() + 1) + ")";
				case 10:
					return "أَلْزُّبْرَة" + " (" + (ordinal() + 1) + ")";
				case 11:
					return "أَلْصَّرْفَة" + " (" + (ordinal() + 1) + ")";
				case 12:
					return "أَلْعَوَّاء" + " (" + (ordinal() + 1) + ")";
				case 13:
					return "أَلْسِّمَاك" + " (" + (ordinal() + 1) + ")";
				case 14:
					return "أَلْغَفْر" + " (" + (ordinal() + 1) + ")";
				case 15:
					return "أَلْزُّبَانَى" + " (" + (ordinal() + 1) + ")";
				case 16:
					return "أَلْإِكْلِيْلُ ﭐلْجَبْهَة" + " (" + (ordinal() + 1) + ")";
				case 17:
					return "أَلْقَلْب" + " (" + (ordinal() + 1) + ")";
				case 18:
					return "أَلْشَّوْلَة" + " (" + (ordinal() + 1) + ")";
				case 19:
					return "أَلْنَّعَائَم" + " (" + (ordinal() + 1) + ")";
				case 20:
					return "أَلْبَلْدَة" + " (" + (ordinal() + 1) + ")";
				case 21:
					return "سَعْدُ ﭐلْذَّابِح" + " (" + (ordinal() + 1) + ")";
				case 22:
					return "سَعْدُ ﭐلْبُلْعَ" + " (" + (ordinal() + 1) + ")";
				case 23:
					return "سَعْدُ ﭐلْسُّعُود" + " (" + (ordinal() + 1) + ")";
				case 24:
					return "سَعْدُ ﭐلْأَخْبِيَّه" + " (" + (ordinal() + 1) + ")";
				case 25:
					return "فَرْغُ ﭐلْدَّلُو ﭐلْمُقْدِم" + " (" + (ordinal() + 1) + ")";
				case 26:
					return "فَرْغُ ﭐلْدَّلُو ﭐلْمُؤْخَر" + " (" + (ordinal() + 1) + ")";
				default:
					return "بَطْنُ ﭐلْحُوت" + " (" + (ordinal() + 1) + ")";
			}
		}
		else
		{
			switch (ordinal())
			{
				case 0:
					return "Sharatain" + " (" + (ordinal() + 1) + ")";
				case 1:
					return "Butain" + " (" + (ordinal() + 1) + ")";
				case 2:
					return "Thurayya" + " (" + (ordinal() + 1) + ")";
				case 3:
					return "Dabaran" + " (" + (ordinal() + 1) + ")";
				case 4:
					return "Hak‘ah" + " (" + (ordinal() + 1) + ")";
				case 5:
					return "Han‘ah" + " (" + (ordinal() + 1) + ")";
				case 6:
					return "Dhira" + " (" + (ordinal() + 1) + ")";
				case 7:
					return "Nathra" + " (" + (ordinal() + 1) + ")";
				case 8:
					return "Tarf" + " (" + (ordinal() + 1) + ")";
				case 9:
					return "Jabhah" + " (" + (ordinal() + 1) + ")";
				case 10:
					return "Zubrah" + " (" + (ordinal() + 1) + ")";
				case 11:
					return "Sarfah" + " (" + (ordinal() + 1) + ")";
				case 12:
					return "Awwa" + " (" + (ordinal() + 1) + ")";
				case 13:
					return "Simak" + " (" + (ordinal() + 1) + ")";
				case 14:
					return "Ghafr" + " (" + (ordinal() + 1) + ")";
				case 15:
					return "Zubana" + " (" + (ordinal() + 1) + ")";
				case 16:
					return "Al Iklil al Jabhah" + " (" + (ordinal() + 1) + ")";
				case 17:
					return "Al Kalb" + " (" + (ordinal() + 1) + ")";
				case 18:
					return "Ash Shawlah" + " (" + (ordinal() + 1) + ")";
				case 19:
					return "An Na’am" + " (" + (ordinal() + 1) + ")";
				case 20:
					return "Al Baldah" + " (" + (ordinal() + 1) + ")";
				case 21:
					return "Sa’d al Dhabih" + " (" + (ordinal() + 1) + ")";
				case 22:
					return "Sa’d al Bula" + " (" + (ordinal() + 1) + ")";
				case 23:
					return "Sa’d al Su’ud" + " (" + (ordinal() + 1) + ")";
				case 24:
					return "Sa’d al Ahbiyah" + " (" + (ordinal() + 1) + ")";
				case 25:
					return "Fargh al Mukdim" + " (" + (ordinal() + 1) + ")";
				case 26:
					return "Fargh al Mukhar" + " (" + (ordinal() + 1) + ")";
				default:
					return "Batn ul Hut" + " (" + (ordinal() + 1) + ")";
			}
		}
	}

	@Override
	public String toString()
	{
		return getName(LocaleEssentials.LANGUAGE_DEFAULT);
	}
}
