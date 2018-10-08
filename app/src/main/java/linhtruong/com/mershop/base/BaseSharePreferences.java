package linhtruong.com.mershop.base;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Base share preferences
 *
 * @TODO support RxSharedPreferences
 *
 * @author linhtruong
 * @date 10/1/18 - 15:32.
 * @organization VED
 */
public abstract class BaseSharePreferences {
    protected SharedPreferences mPref;

    public BaseSharePreferences(Context context) {
        mPref = context.getSharedPreferences(getPrefKey(), Context.MODE_PRIVATE);
    }

    protected abstract String getPrefKey();

    public boolean putString(String key, String value) {
        if (mPref == null) return false;

        mPref.edit().putString(key, value).commit();
        return true;
    }

    public boolean putInt(String key, int value) {
        if (mPref == null) return false;

        mPref.edit().putInt(key, value).commit();
        return true;
    }

    public boolean putBoolean(String key, boolean value) {
        if (mPref == null) return false;

        mPref.edit().putBoolean(key, value).commit();
        return true;
    }

    public String getString(String key) {
        if (mPref == null) return "";

        return mPref.getString(key, "");
    }

    public int getInt(String key) {
        if (mPref == null) return -1;

        return mPref.getInt(key, -1);
    }

    public int getInt(String key, int defValue) {
        if (mPref == null) return -1;

        return mPref.getInt(key, defValue);
    }

    public boolean getBoolean(String key) {
        if (mPref == null) return false;

        return mPref.getBoolean(key, false);
    }
}
