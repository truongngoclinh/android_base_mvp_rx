package linhtruong.com.commons.widgets;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.annotation.DrawableRes;
import android.support.annotation.Nullable;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import linhtruong.com.commons.R;

/**
 * App empty view
 *
 * @author linhtruong
 * @date 10/8/18 - 01:05.
 * @organization VED
 */
public class MSEmptyView extends LinearLayout {
    private ImageView mIvPlaceholder;
    private TextView mTvTitle;
    private TextView mTvSubtitle;

    public MSEmptyView(Context context) {
        super(context);
        init(context, null, 0);
    }

    public MSEmptyView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public MSEmptyView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);
    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        inflate(context, R.layout.com_linhtruong_mershop_view_empty, this);
        setOrientation(VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setWeightSum(5f);

        mIvPlaceholder = findViewById(R.id.iv_placeholder);
        mTvTitle = findViewById(R.id.tv_title);
        mTvSubtitle = findViewById(R.id.tv_subtitle);

        if (attrs != null) {
            TypedArray arr = context.obtainStyledAttributes(attrs, R.styleable.MSEmptyView, defStyleAttr, 0);
            Drawable d = arr.getDrawable(R.styleable.MSEmptyView_ms_ev_src);
            if (d != null) {
                mIvPlaceholder.setImageDrawable(d);
            }

            String title = arr.getString(R.styleable.MSEmptyView_ms_ev_title);
            mTvTitle.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
            mTvTitle.setText(title);

            String subtitle = arr.getString(R.styleable.MSEmptyView_ms_ev_subtitle);
            mTvSubtitle.setText(subtitle);
            arr.recycle();
        }
    }

    public void setImageDrawable(Drawable drawable) {
        mIvPlaceholder.setImageDrawable(drawable);
    }

    public void setImageResource(@DrawableRes int drawableRes) {
        mIvPlaceholder.setImageResource(drawableRes);
    }

    public void setImageUrl(String url) {
        if (!TextUtils.isEmpty(url)) {
            Picasso.get()
                    .load(url)
                    .fit()
                    .centerInside()
                    .into(mIvPlaceholder);
        } else {
            mIvPlaceholder.setImageDrawable(null);
            Picasso.get().cancelRequest(mIvPlaceholder);
        }
    }

    public void setTitle(String title) {
        mTvTitle.setVisibility(TextUtils.isEmpty(title) ? GONE : VISIBLE);
        mTvTitle.setText(title);
    }

    public void setTitle(@StringRes int titleRes) {
        mTvTitle.setVisibility(VISIBLE);
        mTvTitle.setText(titleRes);
    }

    public void setSubtitle(String title) {
        mTvSubtitle.setText(title);
    }

    public void setSubtitle(@StringRes int titleRes) {
        mTvSubtitle.setText(titleRes);
    }
}
