package linhtruong.com.commons.widgets;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

/**
 * App pager
 *
 * @author linhtruong
 * @date 10/5/18 - 11:46.
 * @organization VED
 */
public class MSViewPager extends ViewPager {
    private boolean enabled = false;

    public MSViewPager(@NonNull Context context) {
        super(context);
    }

    public MSViewPager(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onTouchEvent(event);
        }

        return false;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent event) {
        if (this.enabled) {
            return super.onInterceptTouchEvent(event);
        }

        return false;
    }

    public void setHorizontalSwipeEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
