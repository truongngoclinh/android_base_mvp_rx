package linhtruong.com.protocol.gson.deserializers;

import android.util.Log;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import linhtruong.com.protocol.BuildConfig;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/4/18 - 13:44.
 * @organization VED
 */
public abstract class BaseDeserializer<T> implements JsonDeserializer<T> {
    private final String TAG = getClass().getSimpleName();

    protected long optLong(JsonObject jsonObject, String key) {
        return optLong(jsonObject, key, 0);
    }

    protected long optLong(JsonObject jsonObject, String key, long defaultValue) {
        if (jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.get(key).getAsLong();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected String optString(JsonObject jsonObject, String key) {
        return optString(jsonObject, key, "");
    }

    protected String optString(JsonObject jsonObject, String key, String defaultValue) {
        if (jsonObject == null || !jsonObject.has(key)) return defaultValue;
        return jsonObject.get(key).getAsString();
    }

    protected boolean optBoolean(JsonObject jsonObject, String key) {
        return optBoolean(jsonObject, key, false);
    }

    protected boolean optBoolean(JsonObject jsonObject, String key, boolean defaultValue) {
        if (jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.get(key).getAsBoolean();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected int optInt(JsonObject jsonObject, String key) {
        return optInt(jsonObject, key, 0);
    }

    protected int optInt(JsonObject jsonObject, String key, int defaultValue) {
        if (jsonObject == null || !jsonObject.has(key)) return defaultValue;
        try {
            return jsonObject.get(key).getAsInt();
        } catch (Exception e) {
            return defaultValue;
        }
    }

    protected void log(String fmt, Object... args) {
        if (!BuildConfig.DEBUG) {
            return;
        }
        if (args == null || args.length == 0) {
            Log.d(TAG, fmt);
        } else {
            Log.d(TAG, String.format(fmt, args));
        }
    }
}
