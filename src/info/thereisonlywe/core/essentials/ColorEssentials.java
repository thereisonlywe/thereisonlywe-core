package info.thereisonlywe.core.essentials;

/**
 * Created by ahmadnoyan @ thereisonlywe on 19.03.2017
 */

public class ColorEssentials {

    private String getMidpoint(String c1, String c2)
    {
        StringBuilder sb = new StringBuilder("#");
        for(int i=0;i<3;i++) {
            String sub1 = c1.substring(1+2*i,3+2*i);
            String sub2 = c2.substring(1+2*i,3+2*i);
            int v1 = Integer.parseInt(sub1, 16);
            int v2 = Integer.parseInt(sub2, 16);
            int v = (v1 + v2)/2;
            String sub = String.format("%02X", v);
            sb.append(sub);
        }
        return sb.toString();
    }
/*
    public static int getComplimentColor(int color) {
        // get existing colors
        int alpha = Color.alpha(color);
        int red = Color.red(color);
        int blue = Color.blue(color);
        int green = Color.green(color);

        // find compliments
        red = (~red) & 0xff;
        blue = (~blue) & 0xff;
        green = (~green) & 0xff;

        return Color.argb(alpha, red, green, blue);
    }
    */


}
