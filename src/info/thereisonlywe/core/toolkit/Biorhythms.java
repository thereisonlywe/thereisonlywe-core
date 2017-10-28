package info.thereisonlywe.core.toolkit;

import info.thereisonlywe.core.essentials.LocaleEssentials;
import info.thereisonlywe.core.essentials.StringEssentials;
import info.thereisonlywe.core.essentials.SystemEssentials;
import info.thereisonlywe.core.essentials.TimeEssentials;

/**
 * Created by ahmadnoyan @ thereisonlywe on 03.04.2017 for the PlanetaryTimes project.
 */

public class Biorhythms {

    private int physical = 0;
    private int emotional = 0;
    private int intellectual = 0;
    private int spiritual = 0;
    private boolean physicalAscending = false;
    private boolean emotionalAscending = false;
    private boolean intellectualAscending = false;
    private boolean spiritualAscending = false;

    public int getPhysical() {
        return physical;
    }

    public int getEmotional() {
        return emotional;
    }

    public int getIntellectual() {
        return intellectual;
    }

    public int getSpiritual() {
        return spiritual;
    }

    public boolean isPhysicalStrong() {
        return physical + (isPhysicalAscending() ? 15 : -15) >= 50;
    }

    public boolean isEmotionalStrong() {
        return emotional + (isEmotionalAscending() ? 15 : -15) >= 50;
    }

    public boolean isIntellectualStrong() {
        return intellectual + (isIntellectualAscending() ? 15 : -15) >= 50;
    }

    public boolean isSpiritualStrong() {
        return spiritual + (isSpiritualAscending() ? 15 : -15) >= 50;
    }

    public boolean isPhysicalWeak()
    {
        return !isPhysicalStrong();
    }

    public boolean isEmotionalWeak()
    {
        return !isEmotionalStrong();
    }

    public boolean isIntellectualWeak()
    {
        return !isIntellectualStrong();
    }

    public boolean isSpiritualWeak()
    {
        return !isSpiritualStrong();
    }

    public boolean isPhysicalAscending() {
        return physicalAscending;
    }

    public boolean isEmotionalAscending() {
        return emotionalAscending;
    }

    public boolean isIntellectualAscending() {
        return intellectualAscending;
    }

    public boolean isSpiritualAscending() {
        return spiritualAscending;
    }

    public boolean isPhysicalDescending() {
        return !physicalAscending;
    }

    public boolean isEmotionalDescending() {
        return !emotionalAscending;
    }

    public boolean isIntellectualDescending() {
        return !intellectualAscending;
    }

    public boolean isSpiritualDescending() {
        return !spiritualAscending;
    }

    public Biorhythms(double daysSinceBirth)
    {
        if (daysSinceBirth <= 1.0)
        {
            physical = 50;
            emotional = 50;
            intellectual = 50;
            spiritual = 50;
            physicalAscending = true;
            emotionalAscending = true;
            intellectualAscending = true;
            spiritualAscending = true;
            return;
        }
        double _physical = (Math.sin(2*Math.PI*daysSinceBirth/23) * 100 / 2);
        double _emotional = (Math.sin(2*Math.PI*daysSinceBirth/28) * 100 / 2);
        double _intellectual = (Math.sin(2*Math.PI*daysSinceBirth/33) * 100 / 2);
        double _spiritual = (Math.sin(2*Math.PI*daysSinceBirth/18) * 100 / 2);

        physical = ((int) Math.round(_physical)) + 50;
        emotional = ((int) Math.round(_emotional)) + 50;
        intellectual = ((int) Math.round(_intellectual)) + 50;
        spiritual = ((int) Math.round(_spiritual)) + 50;

        if (_physical > (Math.sin(2*Math.PI*(daysSinceBirth - 0.05)/23) * 100 / 2))
            physicalAscending = true;
        if (_emotional > (Math.sin(2*Math.PI*(daysSinceBirth - 0.05)/28) * 100 / 2))
            emotionalAscending = true;
        if (_intellectual > (Math.sin(2*Math.PI*(daysSinceBirth - 0.05)/33) * 100 / 2))
            intellectualAscending = true;
        if (_spiritual > (Math.sin(2*Math.PI*(daysSinceBirth - 0.05)/18) * 100 / 2))
            spiritualAscending = true;
    }

    public Biorhythms(long millis)
    {
        this((double)millis/TimeEssentials.DAY);
    }

    public Biorhythms(long currentMillis, long birthMillis)
    {
        this(currentMillis - birthMillis);
    }

    public Biorhythms(int physical, int emotional, int intellectual, int spiritual, boolean physicalAscending, boolean emotionalAscending, boolean intellectualAscending, boolean spiritualAscending)
    {
        this.physical = physical;
        this.emotional = emotional;
        this.intellectual = intellectual;
        this.spiritual = spiritual;
        this.physicalAscending = physicalAscending;
        this.emotionalAscending = emotionalAscending;
        this.intellectualAscending = intellectualAscending;
        this.spiritualAscending = spiritualAscending;
    }

    public static Biorhythms average (Biorhythms... bio)
    {
        int p = 0, e = 0, i = 0, s = 0;
        int l = bio.length;
        for (int j = 0; j < l; j++)
        {
            p += bio[j].getPhysical();
            e+= bio[j].getEmotional();
            i+= bio[j].getIntellectual();
            s+= bio[j].getSpiritual();
        }
        return new Biorhythms(p / l, e / l, i / l, s / l, bio[l/2-1].physicalAscending, bio[l/2-1].emotionalAscending, bio[l/2-1].intellectualAscending, bio[l/2-1].spiritualAscending);
    }

    public String toString(String physical, String emotional, String intellectual, String spiritual)
    {
        StringBuilder sb = new StringBuilder();
        sb.append(physicalAscending ? "+" : "-");
        sb.append(new String(physical.substring(0, 3)).toUpperCase(LocaleEssentials.LOCALE_DEFAULT));
        sb.append("(");
        if (LocaleEssentials.LANGUAGE_DEFAULT.equals("tr") || LocaleEssentials.LANGUAGE_DEFAULT.equals("fa")) {
            sb.append("%");
            sb.append(this.physical);
        }
        else
        {
            sb.append(this.physical);
            sb.append("%");
        }
        sb.append(")");
        sb.append(" ");

        sb.append(emotionalAscending ? "+" : "-");
        sb.append(new String(emotional.substring(0, 3)).toUpperCase(LocaleEssentials.LOCALE_DEFAULT));
        sb.append("(");
        if (LocaleEssentials.LANGUAGE_DEFAULT.equals("tr") || LocaleEssentials.LANGUAGE_DEFAULT.equals("fa")) {
            sb.append("%");
            sb.append(this.emotional);
        }
        else
        {
            sb.append(this.emotional);
            sb.append("%");
        }
        sb.append(")");
        sb.append(" ");

        sb.append(intellectualAscending ? "+" : "-");
        sb.append(new String(intellectual.substring(0, 3)).toUpperCase(LocaleEssentials.LOCALE_DEFAULT));
        sb.append("(");
        if (LocaleEssentials.LANGUAGE_DEFAULT.equals("tr") || LocaleEssentials.LANGUAGE_DEFAULT.equals("fa")) {
            sb.append("%");
            sb.append(this.intellectual);
        }
        else
        {
            sb.append(this.intellectual);
            sb.append("%");
        }
        sb.append(")");
        sb.append(" ");

        sb.append(spiritualAscending ? "+" : "-");
        sb.append(new String(spiritual.substring(0, 3)).toUpperCase(LocaleEssentials.LOCALE_DEFAULT));
        sb.append("(");
        if (LocaleEssentials.LANGUAGE_DEFAULT.equals("tr") || LocaleEssentials.LANGUAGE_DEFAULT.equals("fa")) {
            sb.append("%");
            sb.append(this.spiritual);
        }
        else
        {
            sb.append(this.spiritual);
            sb.append("%");
        }
        sb.append(")");

        return sb.toString();
    }

    @Override
    public String toString()
    {
        return toString("PHY", "EMO", "INT", "SPI");
    }

}
