package linhtruong.com.commons.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.ColorInt;
import android.support.annotation.ColorRes;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import linhtruong.com.commons.R;

/**
 * App imageView
 *
 * @author linhtruong
 * @date 10/6/18 - 22:21.
 * @organization VED
 */
public class MSImageView extends AppCompatImageView {
    private ColorStateList mSrcTint;
    private int mStrokeColor = Color.TRANSPARENT;
    private int mStrokeWidth;
    private Paint mStrokePaint;

    public MSImageView(Context context) {
        super(context);
    }

    public MSImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MSImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    protected void init(Context context, AttributeSet attrs, int defStyleAttr) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.MSImageView, defStyleAttr, 0);
        try {
            mSrcTint = a.getColorStateList(R.styleable.MSImageView_ms_src_tint);
            applyTint();

            mStrokeColor = a.getColor(R.styleable.MSImageView_ms_stroke_color, Color.TRANSPARENT);
            mStrokeWidth = a.getDimensionPixelSize(R.styleable.MSImageView_ms_stroke_width, 0);
        } finally {
            a.recycle();
        }
    }

    public void setTintColorList(@ColorRes int res) {
        if (res == 0) {
            mSrcTint = null;
        } else {
            mSrcTint = ContextCompat.getColorStateList(getContext(), res);
        }
        drawableStateChanged();
    }

    public void setTintColorList(ColorStateList list) {
        mSrcTint = list;
        drawableStateChanged();
    }

    public void setStrokeColorRes(@ColorRes int res) {
        if (res == 0) {
            mStrokeColor = Color.TRANSPARENT;
        } else {
            mStrokeColor = ContextCompat.getColor(getContext(), res);
        }
        if (mStrokePaint != null) {
            mStrokePaint.setColor(mStrokeColor);
        }
        invalidate();
    }

    public void setStrokeColor(@ColorInt int color) {
        mStrokeColor = color;
        if (mStrokePaint != null) {
            mStrokePaint.setColor(mStrokeColor);
        }
        invalidate();
    }

    public void setStrokeWidth(int widthPx) {
        mStrokeWidth = widthPx;
        if (mStrokePaint != null) {
            mStrokePaint.setStrokeWidth(mStrokeWidth);
        }
        invalidate();
    }

    @Override
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        applyTint();
    }

    private void applyTint() {
        if (mSrcTint == null) {
            setColorFilter(null);
            return;
        }
        int color = mSrcTint.getColorForState(getDrawableState(), Color.TRANSPARENT);
        setColorFilter(color);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        int w = getWidth(), h = getHeight();
        if (drawStroke()) {
            canvas.drawRect(0, 0, w, h, mStrokePaint);
        }
    }

    private boolean drawStroke() {
        if (mStrokeColor == Color.TRANSPARENT && mStrokeWidth <= 0) {
            return false;
        }

        if (mStrokePaint == null) {
            mStrokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mStrokePaint.setColor(mStrokeColor);
            mStrokePaint.setStyle(Paint.Style.STROKE);
            mStrokePaint.setStrokeWidth(mStrokeWidth);
        }
        return true;
    }
}
