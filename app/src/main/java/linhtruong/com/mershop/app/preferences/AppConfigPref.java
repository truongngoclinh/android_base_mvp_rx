package linhtruong.com.mershop.app.preferences;

import android.content.Context;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.mershop.base.BaseSharePreferences;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/8/18 - 14:11.
 * @organization VED
 */
public class AppConfigPref extends BaseSharePreferences {
    private static final String PREF_NAME = "app_config";
    private static final String KEY_THEME = "key_theme";

    private static volatile AppConfigPref mInstance;

    public static void init(Context context) {
        mInstance = new AppConfigPref(context);
    }

    private AppConfigPref(Context context) {
        super(context);
    }

    public static int getTheme() {
        return mInstance.getInt(KEY_THEME, MSConstValue.THEME.LIGHT);
    }

    public static void setTheme(int theme) {
        mInstance.putInt(KEY_THEME, theme);
    }

    @Override
    protected String getPrefKey() {
        return PREF_NAME;
    }
}
