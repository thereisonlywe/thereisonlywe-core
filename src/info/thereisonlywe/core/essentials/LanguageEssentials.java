package info.thereisonlywe.core.essentials;

public class LanguageEssentials
{
    public static class Character
    {
        public static String SHADDA = "\u0651";
        public static String KASRA = "\uFE7A";
        public static String DAMMA = "\uFE78";
        public static String FATHA = "\uFE76";
        public static String SUKUN = "\u0652";
        public static String KASRATAN = "\uFE74";
        public static String DAMMATAN = "\uFE72";
        public static String FATHATAN = "\uFE70";
        public static String MADDAH = "\u0653";
        public static String ALEPH_WAVY_HAMZA_ABOVE = "\u0672";
        public static String ALEPH_WAVY_HAMZA_BELOW = "\u0673";
        public static String ALEPH_HIGH_HAMZA = "\u0675";
        public static String ALEPH_SUPERSCRIPT = "\u0670";
        public static String ALEPH_WASLA = "\u0671";
        public static String ALEPH_HAMZA_ABOVE = "\u0623";
        public static String ALEPH_HAMZA_BELOW = "\u0625";
        public static String ALEPH_MADDA = "\u0622";
        public static String ALEPH_WTF = "أْ";
        public static String ALEPH = "\u0627";
        public static String HAMZA_ABOVE = "\u0654";
        public static String HAMZA_BELOW = "\u0655";
        public static String HAMZA = "\u0621";
        public static final String COMPULSORY_PAUSE_QURAN_PAUSE_MARK = "\u06D8";
        public static final String IMPERMISSIBLE_PAUSE_QURAN_PAUSE_MARK = "\u06D9";
        public static final String PERMISSIBLE_PAUSE_QURAN_PAUSE_MARK = "\u06DA";
        public static final String UNPREFERABLE_PAUSE_QURAN_PAUSE_MARK = "\u06D6";
        public static final String PREFERABLE_PAUSE_QURAN_PAUSE_MARK = "\u06D7";
        public static final String INTERCHANGEABLE_PAUSE_QURAN_PAUSE_MARK = "\u06DB";
        public static final String DOT_DECIMAL = ".";
        public static final String UNDERSCORE = "_";
        public static final String DASH = "—";
        public static final String SCORE_MINUS = "-";
        public static final String SLASH = "/";
        public static final String BACKSLASH = "\\";

        public static boolean isDamma(char c)
        {
            if (c == DAMMA.charAt(0)) return true;
            return false;
        }

        public static boolean isDammatan(char c)
        {
            if (c == DAMMATAN.charAt(0)) return true;
            return false;
        }

        public static boolean isDiacriticMark(char c)
        {
            if (isMaddah(c) || isFatha(c) || isKasra(c) || isDamma(c) || isSukun(c)
                    || isShadda(c) || isFathatan(c) || isKasratan(c) || isDammatan(c)) return true;
            return false;
        }

        public static boolean isFatha(char c)
        {
            if (c == FATHA.charAt(0)) return true;
            return false;
        }

        public static boolean isFathatan(char c)
        {
            if (c == FATHATAN.charAt(0)) return true;
            return false;
        }

        public static boolean isKasra(char c)
        {
            if (c == KASRA.charAt(0)) return true;
            return false;
        }

        public static boolean isKasratan(char c)
        {
            if (c == KASRATAN.charAt(0)) return true;
            return false;
        }

        public static boolean isMaddah(char c)
        {
            if (c == MADDAH.charAt(0)) return true;
            return false;
        }

        public static boolean isMoonLetter(char c)
        {
            return !isSunLetter(c);
        }

        public static boolean isQuranicPauseMark(char c)
        {
            if (c == COMPULSORY_PAUSE_QURAN_PAUSE_MARK.charAt(0)
                    || c == IMPERMISSIBLE_PAUSE_QURAN_PAUSE_MARK.charAt(0)
                    || c == UNPREFERABLE_PAUSE_QURAN_PAUSE_MARK.charAt(0)
                    || c == PREFERABLE_PAUSE_QURAN_PAUSE_MARK.charAt(0)
                    || c == PERMISSIBLE_PAUSE_QURAN_PAUSE_MARK.charAt(0)
                    || c == INTERCHANGEABLE_PAUSE_QURAN_PAUSE_MARK.charAt(0)) return true;
            else return false;
        }

        public static boolean isShadda(char c)
        {
            if (c == SHADDA.charAt(0)) return true;
            return false;
        }

        public static boolean isSukun(char c)
        {
            if (c == SUKUN.charAt(0)) return true;
            return false;
        }

        public static boolean isSunLetter(char c)
        {
            if (c == 'ﺕ' || c == 'ﺙ' || c == 'ﺩ' || c == 'ﺫ' || c == 'ﺭ' || c == 'ﺯ'
                    || c == 'ﺱ' || c == 'ﺵ' || c == 'ﺹ' || c == 'ﺽ' || c == 'ﻁ' || c == 'ﻅ'
                    || c == 'ﻝ' || c == 'ﻥ') return true;
            return false;
        }

        public static boolean isValidArabicChar(char c)
        {
            if (c == ' ') return true;
            else if (java.lang.Character.UnicodeBlock.of(c).equals(
                    java.lang.Character.UnicodeBlock.ARABIC)) return true;
            else return false;
        }

        public static boolean isValidArabicChar(String s)
        {
            for (int i = 0; i < s.length(); i++)
            {
                if (!isValidArabicChar(s.charAt(i))) return false;
            }
            return true;
        }

        public static boolean containsArabicCharacter(String s)
        {
            for (int i = 0; i < s.length(); i++)
            {
                char c = s.charAt(i);
                if (c != ' ' && isValidArabicChar(c)) return true;
            }
            return false;
        }
    }
    public static class Math
    {
        public static String GREATER = ">";
        public static String GREATER_OR_EQUAL = ">=";
        public static String LESS = "<";
        public static String LESS_OR_EQUAL = "<=";
        public static String EQUALS = "=";
        public static String NOT_EQUAL_TO = "\u2260";
    }
    public static class Logic
    {
        public static String NOT2 = "\u00ac";
        public static String NOT = "\u02dc";
        public static String AND = "\u2227";
        public static String AND2 = "\u0026";
        public static String OR = "\u2228";
        public static String XOR = "\u2295";
        public static String XOR2 = "\u22bb";
        public static String NAND = "\u22BC";

        public static boolean isConjunctionChar(char c)
        {
            if (c == AND.charAt(0) || c == AND2.charAt(0)) return true;
            return false;
        }

        public static boolean isConjunctionNegationChar(char c)
        {
            if (c == NAND.charAt(0)) return true;
            return false;
        }

        public static boolean isDisjunctionChar(char c)
        {
            if (c == OR.charAt(0)) return true;
            return false;
        }

        public static boolean isExclusiveDisjunctionChar(char c)
        {
            if (c == XOR.charAt(0) || c == XOR2.charAt(0)) return true;
            return false;
        }

        public static boolean isNegationChar(char c)
        {
            if (c == NOT.charAt(0) || c == NOT2.charAt(0)) return true;
            return false;
        }
    }
    public static class English
    {
        public static String getNumberOrderSuffix(int number)
        {
            final String s = String.valueOf(number);
            if (s.endsWith("1")) return "st";
            else if (s.endsWith("2")) return "nd";
            else if (s.endsWith("3")) return "rd";
            else return "th";
        }

        public static String getNumberOrderSuffix(int number, boolean together)
        {
            if (together) return number + getNumberOrderSuffix(number);
            else return getNumberOrderSuffix(number);
        }

        public static String getPluralForm(String word)
        {
            if (word.endsWith("s")) return word + "'";
            else return word + "s";
        }
    }
    public static class Turkish
    {
        public static String getPluralForm(String word)
        {
            final char c1 = word.charAt(word.length() - 1);
            final char c2 = word.charAt(word.length() - 2);
            if (isVowel(c1))
            {
                if (isBoldVowel(c1)) return word + "lar";
                else return word + "ler";
            }
            else
            {
                if (isBoldVowel(c2)) return word + "lar";
                else return word + "ler";
            }
        }

        public static boolean isBoldVowel(char c)
        {
            if (c == 'a' || c == 'ı' || c == 'o' || c == 'u') return true;
            else return false;
        }

        public static boolean isVowel(char c)
        {
            if (c == 'e' || c == 'i' || c == 'ö' || c == 'ü' || isBoldVowel(c)) return true;
            else return false;
        }
    }

    private static String languages[] = new String[] { "ab;Abkhaz;аҧсуа бызшәа",
            "af;Afrikaans;Afrikaans", "am;Amharic;Amarəñña", "ar;Arabic;العربية",
            "az;Azerbaijani;Azeri", "be;Belorussian;Беларуская",
            "bm;Bambara;Bamanankan", "bn;Bengali;Bangla", "bg;Bulgarian;Български",
            "bs;Bosnian;босански ", "ca;Catalan;Català", "cs;Czech;Čeština",
            "cy;Welsh;Cymraeg", "da;Danish;Dansk", "de;German;Deutsch",
            "dv;Divehi;Divehi", "ee;Ewe;Ɛʋɛ", "el;Greek;Ελληνικά",
            "en;English;English", "es;Spanish;Español", "et;Estonian;Eesti",
            "eu;Basque;Euskera", "fa;Persian;فارسی", "ff;Fula;Fulfulde",
            "fi;Finnish;Suomi", "fr;French;Français",
            "fr-CA;French (Canadian);français canadien", "ga;Irish;Gaeilge",
            "gl;Galician;Galego", "ha;Hausa;Yaren Hausa", "he;Hebrew;עברית",
            "hi;Hindi;हिंदी", "hr;Croatian;Hrvatski", "hu;Hungarian;Magyar",
            "hy;Armenian;Հայերեն", "id;Indonesian;Bahasa indonesia",
            "is;Icelandic;Íslenska", "it;Italian;italiano", "ja;Japanese;日本語",
            "ka;Georgian;ქართული", "kk;Kazakh;Қазақ", "kn;Kannada;ಕನ್ನಡ",
            "ko;Korean;한국어", "ku;Kurdish;کوردی", "ky;Kirghiz;Кыргыз",
            "lt;Lithuanian;Lietuviškai", "lu;Luo;Dholuo", "lv;Latvian;Latviešu",
            "mk;Macedonian;Македонски", "ml;Malayalam;Malayālam ",
            "mr;Maranao;Maranao", "ms;Malaysian;Bahasa melayu", "mt;Maltese;Malti",
            "nl;Dutch;Nederlands", "no;Norwegian;Norsk", "pl;Polish;Polski",
            "ps;Pashto;پښتو", "pt;Portuguese;Português",
            "pt-BR;Portuguese (Brazilian);português brasileiro", "rn;Kirundi;Kirundi",
            "ro;Romanian;Română", "ru;Russian;Pyccĸий", "rw;Kinyarwanda;Kinyarwanda",
            "sd;Sindhi;سنڌي", "sk;Slovak;Slovenčina", "sl;Slovenian;Slovenščina",
            "so;Somali;Af-Soomaali", "sq;Albanian;Shqip", "sr;Serbian;Srpski",
            "sv;Swedish;Svenska", "sw;Swahili;Kiswahili", "ta;Tamil;Tamiḻ",
            "te;Telugu;తెలుగు", "tg;Tajik;Tajiki", "th;Thai;ภาษาไทย",
            "tr;Turkish;Türkçe", "tt;Tatar;татарча", "ug;Uyghur;Uyghurche",
            "uk;Ukrainian;Українська", "ur;Urdu;اردو", "uz;Uzbek;O'zbek",
            "vi;Vietnamese;Tiếng Việt", "wo;Wolof;Wolof", "xs;Xhosa;isiXhosa",
            "yo;Yoruba;Yorùbá", "zh;Chinese;中文",
            "zh-Hans;Chinese (Simplified script);简体中文",
            "zh-Hant;Chinese (Traditional script);繁體中文", "zu;Zulu;isiZulu" };

    public static String getEnglishLanguageName(String s)
    {
        final boolean gotCode = s.length() == 2;
        for (final String language : languages)
        {
            final String temp[] = language.split(";");
            if (gotCode && temp[0].equalsIgnoreCase(s)) return temp[1];
            else if (temp[2].equalsIgnoreCase(s)) return temp[1];
        }
        return null;
    }

    public static String getLanguageCode(String s)
    {
        for (final String language : languages)
        {
            final String temp[] = language.split(";");
            if (temp[1].equalsIgnoreCase(s) || temp[2].equalsIgnoreCase(s)) return temp[0];
        }
        return null;
    }

    public static String getNativeLanguageName(String s)
    {
        final boolean gotCode = s.length() == 2;
        for (final String language : languages)
        {
            final String temp[] = language.split(";");
            if (gotCode && temp[0].equalsIgnoreCase(s)) return temp[2];
            else if (temp[1].equalsIgnoreCase(s)) return temp[2];
        }
        return null;
    }

    public static boolean isRightToLeftLanguage(String language)
    {
        String[] list;
        if (language.length() == 2)
        {
            list = new String[] { "ar", "fa", "ur", "he", "yi", "ku", "dv", "sd" };
        }
        else
        {
            list = new String[] { "Arabic", "Persian", "Urdu", "Hebrew", "Yiddish",
                    "کوردی", "Kurdish", "עברית", "اردو", "العربية", "فارسی", "سنڌي",
                    "Sindhi", "Divehi" };
        }
        for (final String element : list)
        {
            if (language.equals(element)) return true;
        }
        return false;
    }
}

