package info.thereisonlywe.core.essentials;

import java.awt.Color;

/**
 * 
 * @author thereisonlywe
 */
public class UIEssentials {

	public static class Colors {

		public static final Color	ROYAL_MAROON		= new Color(90, 56, 57);
		public static final Color	SWEDISH_AZURE		= new Color(0, 91, 153);
		public static final Color	APPLE_GRAY			= new Color(214, 217, 223);
		public static final Color	FOREST_GREEN		= new Color(34, 139, 34);
		public static final Color	DARTHMOUTH_GREEN	= new Color(0, 112, 60);

		public static String toHex(int i, int j, int k)
		{
			final Color c = new Color(i, j, k);
			final String tmp = Integer.toHexString(c.getRGB());
			return new String(tmp.substring(2, tmp.length()));
		}
	}

}