package linhtruong.com.commons.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import linhtruong.com.commons.R;

/**
 * App textview
 *
 * @author linhtruong
 * @date 10/2/18 - 22:03.
 * @organization VED
 */
public class MSTextView extends AppCompatTextView {

    public MSTextView(Context context) {
        super(context);
    }

    public MSTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MSTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MSView, defStyleAttr, 0);
        try {
            ColorStateList bgTint = a.getColorStateList(R.styleable.MSView_ms_bg_tint);
            if (bgTint != null) {
                ViewCompat.setBackgroundTintList(this, bgTint);
                ViewCompat.setBackgroundTintMode(this, PorterDuff.Mode.SRC_ATOP);
            }
        } finally {
            a.recycle();
        }
    }
}
