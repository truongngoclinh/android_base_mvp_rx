package linhtruong.com.mershop.home;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.support.annotation.ColorInt;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.StateSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;
import linhtruong.com.commons.helpers.MSUIHelper;
import linhtruong.com.commons.widgets.MSImageView;
import linhtruong.com.commons.widgets.MSTextView;
import linhtruong.com.mershop.R;

/**
 * Main tab item view
 *
 * @author linhtruong
 * @date 10/6/18 - 15:44.
 * @organization VED
 */
public class MSHomeTabItemView extends LinearLayout {
    private MSTextView mTvTitle;
    private MSImageView mIvIcon;
    private View mRedDot;
    private Drawable mIcon, mIconPressed;

    public MSHomeTabItemView(Context context) {
        this(context, null);
    }

    public MSHomeTabItemView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public MSHomeTabItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private final Target ICON_TARGET = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mIcon = new BitmapDrawable(getResources(), bitmap);
            refreshIcons();
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private final Target ICON_PRESSED_TARGET = new Target() {
        @Override
        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
            mIconPressed = new BitmapDrawable(getResources(), bitmap);
            refreshIcons();
        }

        @Override
        public void onBitmapFailed(Exception e, Drawable errorDrawable) {

        }

        @Override
        public void onPrepareLoad(Drawable placeHolderDrawable) {
        }
    };

    private void init(Context context) {
        inflate(context, R.layout.com_linhtruong_mershop_home_tab_item_view, this);
        setOrientation(LinearLayout.VERTICAL);
        setGravity(Gravity.CENTER_HORIZONTAL);
        setMinimumHeight(getResources().getDimensionPixelSize(R.dimen.ms_home_bottom_tab_height));
        setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

        mTvTitle = findViewById(R.id.ms_tab_title);
        mIvIcon = findViewById(R.id.ms_tab_icon);
        mRedDot = findViewById(R.id.ms_tab_item_red_dot);
    }

    private static ColorStateList createColorStateList(int defaultColor, int selectedColor) {
        final int[][] states = new int[2][];
        final int[] colors = new int[2];
        int i = 0;

        states[i] = SELECTED_STATE_SET;
        colors[i] = selectedColor;
        i++;

        states[i] = EMPTY_STATE_SET;
        colors[i] = defaultColor;

        return new ColorStateList(states, colors);
    }

    public void setIconTintList(@ColorInt int tabFontColorNormal, @ColorInt int tabFontColorSelected) {
        int[][] states = new int[][]{
                new int[]{android.R.attr.state_selected},
                new int[]{android.R.attr.state_activated},
                new int[]{android.R.attr.state_checked},
                StateSet.WILD_CARD
        };
        int[] colors = new int[]{
                tabFontColorSelected,
                tabFontColorSelected,
                tabFontColorSelected,
                tabFontColorNormal
        };

        ColorStateList list = new ColorStateList(states, colors);
        mIvIcon.setTintColorList(list);
    }

    public void setIcon(int iconRes, int iconPressedRes) {
        mIcon = getResources().getDrawable(iconRes);
        mIconPressed = getResources().getDrawable(iconPressedRes);
        refreshIcons();
    }

    public void setTitle(int titleRes) {
        mTvTitle.setText(getResources().getString(titleRes));
    }

    public void setRemoteIcon(String iconUrl, String iconPressedUrl) {
        if (TextUtils.isEmpty(iconUrl) && TextUtils.isEmpty(iconPressedUrl)) {
            return;
        }
        int size = MSUIHelper.dp10 * 3;
        Picasso picasso = Picasso.get();
        if (!TextUtils.isEmpty(iconUrl)) {
            picasso.load(iconUrl).resize(size, size).onlyScaleDown().into(ICON_TARGET);
        }
        if (!TextUtils.isEmpty(iconPressedUrl)) {
            picasso.load(iconPressedUrl).resize(size, size).onlyScaleDown().into(ICON_PRESSED_TARGET);
        }
    }

    private void refreshIcons() {
        if (mIcon == null && mIconPressed == null) {
            mIvIcon.setImageDrawable(null);
            return;
        }

        StateListDrawable stateListDrawable = new StateListDrawable();
        stateListDrawable.addState(new int[]{android.R.attr.state_selected}, mIconPressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_activated}, mIconPressed);
        stateListDrawable.addState(new int[]{android.R.attr.state_checked}, mIconPressed);
        stateListDrawable.addState(StateSet.WILD_CARD, mIcon);
        mIvIcon.setImageDrawable(stateListDrawable);
    }

    public void setRedDotVisibility(boolean visible) {
        mRedDot.setVisibility(visible ? View.VISIBLE : View.GONE);
    }
}
