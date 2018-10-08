package linhtruong.com.commons.widgets;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.annotation.StyleRes;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import linhtruong.com.commons.R;
import linhtruong.com.commons.utils.MSDrawableUtil;

/**
 * Main purpose of creating this class is lying within the flexibility of controlling out title/subtitle text views.
 * Although the original toolbar allows modifying text appearance, which surely covers vast majority of use cases,
 * it doesn't allow us to change others and some, such as the font type, is quite critical.
 *
 * @author linhtruong
 * @date 10/2/18 - 16:19.
 * @organization VED
 */
public class MSToolbar extends Toolbar {
    private MSTextView mTvTitle, mTvSubTitle;
    private MSImageView mIcon;
    private CharSequence mTitleText;
    private CharSequence mSubtitleText;

    public MSToolbar(Context context) {
        this(context, null);
    }

    public MSToolbar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, android.support.v7.appcompat.R.attr.toolbarStyle);
    }

    public MSToolbar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        inflateLayout(getDefaultLayoutRes());
    }

    @Override
    public void setTitle(CharSequence title) {
        if (TextUtils.isEmpty(title)) {
            mTvTitle.setVisibility(GONE);
        } else {
            mTvTitle.setVisibility(VISIBLE);
            mTvTitle.setText(title);
        }
        mTitleText = title;

        adjustTitle();
    }

    public void adjustTitle() {
        if (mTvSubTitle.getVisibility() == View.GONE) {
            RelativeLayout.LayoutParams lp = (RelativeLayout.LayoutParams) mTvTitle.getLayoutParams();
            lp.addRule(RelativeLayout.CENTER_IN_PARENT, RelativeLayout.TRUE);
            mTvTitle.setLayoutParams(lp);
        }
    }

    @LayoutRes
    protected int getDefaultLayoutRes() {
        return R.layout.com_linhtruong_mershop_toolbar_title;
    }

    protected void inflateLayout(@LayoutRes int layoutRes) {
        inflate(getContext(), layoutRes, this);
        mTvTitle = findViewById(R.id.ms_toolbar_title);
        mTvSubTitle = findViewById(R.id.ms_toolbar_subtitle);
        mIcon = findViewById(R.id.ms_toolbar_ic);
    }

    public void setContentView(@LayoutRes int layoutRes) {
        View existing = findViewById(R.id.ms_toolbar_root);
        if (existing != null) {
            removeView(existing);
        }

        inflateLayout(layoutRes);
        if (mTvTitle == null) {
            throw new IllegalArgumentException(
                    "layout must contain TextView with id R.id.com_garena_gamecenter_tv_title");
        }
    }

    public void setIcon(int res) {
        mIcon.setImageResource(res);
        mIcon.setVisibility(View.VISIBLE);
    }

    @Override
    public CharSequence getTitle() {
        return mTitleText;
    }

    @Override
    public void setSubtitle(CharSequence subtitle) {
        if (mTvSubTitle == null) {
            return;
        }

        if (TextUtils.isEmpty(subtitle)) {
            mTvSubTitle.setVisibility(GONE);
        } else {
            mTvSubTitle.setVisibility(VISIBLE);
            mTvSubTitle.setText(subtitle);
        }

        mSubtitleText = subtitle;
    }

    @Override
    public CharSequence getSubtitle() {
        return mSubtitleText;
    }

    @Override
    public void setTitleTextColor(@ColorInt int color) {
        mTvTitle.setTextColor(color);
    }

    @Override
    public void setTitleTextAppearance(Context context, @StyleRes int resId) {
        mTvTitle.setTextAppearance(context, resId);
    }

    public void setTitleAlpha(float alpha) {
        mTvTitle.setAlpha(alpha);
    }

    @Override
    public void setSubtitleTextColor(@ColorInt int color) {
        if (mTvSubTitle != null) {
            mTvSubTitle.setTextColor(color);
        }
    }

    @Override
    public void setSubtitleTextAppearance(Context context, @StyleRes int resId) {
        if (mTvSubTitle != null) {
            mTvSubTitle.setTextAppearance(context, resId);
        }
    }

    public void applyMenuIconTint(@ColorInt int color) {
        if (color == Color.TRANSPARENT) {
            return;
        }

        Drawable backIcon = getNavigationIcon();
        if (backIcon != null) {
            setNavigationIcon(MSDrawableUtil.tintDrawable(backIcon, color));
        }

        Drawable drawable = getOverflowIcon();
        if (drawable != null) {
            setOverflowIcon(MSDrawableUtil.tintDrawable(drawable, color));
        }

        Menu menu = getMenu();
        if (menu == null) {
            return;
        }

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            Drawable icon = item.getIcon();
            if (icon != null) {
                item.setIcon(MSDrawableUtil.tintDrawable(icon, color));
            }
        }
    }
}
