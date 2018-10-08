package linhtruong.com.commons.helpers;

import android.content.Context;
import android.util.DisplayMetrics;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/6/18 - 22:52.
 * @organization VED
 */
public class MSUIHelper {
    public static int SCREEN_WIDTH_PX;
    public static int SCREEN_HEIGHT_PX;
    public static float DENSITY, SCALE_DENSITY;

    public static int dp1;
    public static int dp4;
    public static int dp8;
    public static int dp10;
    public static int dp16;

    public static void init(Context context) {
        DisplayMetrics metrics = context.getResources().getDisplayMetrics();
        SCREEN_WIDTH_PX = Math.min(metrics.widthPixels, metrics.heightPixels);
        SCREEN_HEIGHT_PX = Math.max(metrics.widthPixels, metrics.heightPixels);

        DENSITY = metrics.density;
        SCALE_DENSITY = metrics.scaledDensity;

        dp1 = getPx(1);
        dp4 = getPx(4);
        dp8 = getPx(8);
        dp10 = getPx(10);
        dp16 = getPx(16);
    }

    public static int getPx(int dp) {
        return (int) (dp * DENSITY + 0.5);
    }

    public static int getSp(int sp) {
        return (int) (sp * SCALE_DENSITY + 0.5);
    }

    public static int getStatusBarHeight(Context context) {
        int result = 0;
        int resourceId = context.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = context.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
