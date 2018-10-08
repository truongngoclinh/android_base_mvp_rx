package linhtruong.com.commons.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.support.annotation.AttrRes;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.RequiresApi;
import android.util.TypedValue;
import android.view.View;
import android.view.Window;
import linhtruong.com.commons.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Theme utilities
 *
 * @author linhtruong
 * @date 10/7/18 - 23:57.
 * @organization VED
 */
public class MSThemeUtil {
    // to prevent unnecessary reflection calls
    private static boolean sIsMiui = true;
    private static Method sMiuiMethodSetExtraFlags;
    private static int sMiuiDarkModeFlag;

    private static final TypedValue sRecycledValue = new TypedValue();

    public static int resolveRes(Context context, @AttrRes int attrRes) {
        Resources.Theme theme = context.getTheme();
        theme.resolveAttribute(attrRes, sRecycledValue, true);
        return sRecycledValue.resourceId;
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColor(Activity activity, @ColorInt int color) {
        activity.getWindow().setStatusBarColor(color);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public static void setStatusBarColorRes(Activity activity, @ColorRes int colorRes) {
        int color = activity.getResources().getColor(colorRes);
        activity.getWindow().setStatusBarColor(color);
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public static void setStatusBarTextColorLight(Activity activity, boolean isLightTextColor) {
        final int flags = activity.getWindow().getDecorView().getSystemUiVisibility();
        activity.getWindow().getDecorView()
                .setSystemUiVisibility(isLightTextColor ? (flags & ~View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR) :
                        (flags | View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR));

        setStatusBarTextColorLightMiui(activity, isLightTextColor);
    }

    public static void setStatusBarTextColorLightMiui(Activity activity, boolean isLightTextColor) {
        if (!sIsMiui) {
            return;
        }

        if (sMiuiMethodSetExtraFlags == null) {
            Class<? extends Window> clazz = activity.getWindow().getClass();
            try {
                Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
                Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
                sMiuiDarkModeFlag = field.getInt(layoutParams);
                sMiuiMethodSetExtraFlags = clazz.getMethod("setExtraFlags", int.class, int.class);
            } catch (Exception e) {
                MSLogUtil.i(e.getMessage());
                sIsMiui = false;
                return;
            }
        }

        try {
            sMiuiMethodSetExtraFlags.invoke(activity.getWindow(), isLightTextColor ? 0 : sMiuiDarkModeFlag,
                    sMiuiDarkModeFlag);
            MSLogUtil.d("MIUI set status bar dark theme success: isDark=%s", isLightTextColor);
        } catch (Exception e) {
            MSLogUtil.e(e.getMessage());
            sIsMiui = false;
        }
    }

    public static void setDimStatusBar(Activity activity, boolean dimStatusBar) {
        View decorView = activity.getWindow().getDecorView();
        int systemUiFlags = decorView.getSystemUiVisibility();
        if (dimStatusBar) {
            systemUiFlags |= View.SYSTEM_UI_FLAG_LOW_PROFILE;
        } else {
            systemUiFlags &= ~View.SYSTEM_UI_FLAG_LOW_PROFILE;
        }
        decorView.setSystemUiVisibility(systemUiFlags);
    }

    private MSThemeUtil() {
    }
}
