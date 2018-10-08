package linhtruong.com.commons.utils;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.NinePatchDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.annotation.DrawableRes;
import android.support.v4.graphics.drawable.DrawableCompat;

/**
 * Drawable utilities
 *
 * @author linhtruong
 * @date 10/2/18 - 16:28.
 * @organization VED
 */
public class MSDrawableUtil {
    public static Drawable tint(Context context, @DrawableRes int id, @ColorRes int colorRes) {
        Drawable d = context.getResources().getDrawable(id);
        if (d == null) {
            return null;
        }

        return tint(context, d, colorRes);
    }

    public static Drawable tint(Context context, Drawable orig, @ColorRes int colorRes) {
        return tintDrawable(orig, context.getResources().getColor(colorRes));
    }

    public static Drawable tintDrawable(Drawable drawable, @ColorInt int color) {
        if (drawable instanceof NinePatchDrawable) {
            drawable.mutate();
            drawable.setColorFilter(new PorterDuffColorFilter(color, PorterDuff.Mode.MULTIPLY));
            return drawable;
        }
        Drawable wrap = DrawableCompat.wrap(drawable);
        DrawableCompat.setTint(wrap.mutate(), color);
        return wrap;
    }

    public static Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }
}
