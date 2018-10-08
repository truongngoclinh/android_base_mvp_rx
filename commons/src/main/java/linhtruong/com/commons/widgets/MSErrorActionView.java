package linhtruong.com.commons.widgets;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.DrawableRes;
import android.support.annotation.StringRes;
import android.util.AttributeSet;
import android.view.Gravity;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import linhtruong.com.commons.R;
import linhtruong.com.commons.helpers.MSUIHelper;

/**
 * App error action
 *
 * @author linhtruong
 * @date 10/5/18 - 11:23.
 * @organization VED
 */
public class MSErrorActionView extends LinearLayout {
    private TextView tvText, tvBtn;
    private ImageView ivIcon;

    public MSErrorActionView(Context context) {
        super(context);
        init(null, 0);
    }

    public MSErrorActionView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public MSErrorActionView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs, defStyleAttr);
    }

    private void init(AttributeSet attrs, int defStyleAttr) {
        inflate(getContext(), R.layout.com_linhtruong_mershop_view_error_action, this);
        setGravity(Gravity.CENTER);
        setOrientation(VERTICAL);

        tvText = findViewById(R.id.ms_base_text_tips);
        ivIcon = findViewById(R.id.ms_base_icon);
        tvBtn = findViewById(R.id.ms_base_text_action);

        if (attrs != null) {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.MSErrorActionView, defStyleAttr, 0);
            try {
                String text = a.getString(R.styleable.MSErrorActionView_ms_eav_text);
                if (text != null) {
                    tvText.setText(text);
                }
                String btnText = a.getString(R.styleable.MSErrorActionView_ms_eav_btn_text);
                if (btnText != null) {
                    tvBtn.setText(btnText);
                }
                Drawable drawable = a.getDrawable(R.styleable.MSErrorActionView_ms_eav_icon);
                if (drawable != null) {
                    ivIcon.setImageDrawable(drawable);
                }
            } finally {
                a.recycle();
            }
        }
    }

    public void setText(String text) {
        tvText.setText(text);
    }

    public void setText(@StringRes int res) {
        tvText.setText(res);
    }

    public void setTextColor(@ColorInt int color) {
        tvText.setTextColor(color);
    }

    public void setButtonText(String text) {
        tvBtn.setText(text);
    }

    public void setButtonText(@StringRes int res) {
        tvBtn.setText(res);
    }

    public void setButtonTextColor(ColorStateList colorStateList) {
        tvBtn.setTextColor(colorStateList);
    }

    public void setButtonBackground(Drawable drawable) {
        tvBtn.setBackground(drawable);
    }

    public void setButtonVisibility(int visibility) {
        tvBtn.setVisibility(visibility);
    }

    public void setImageDrawable(Drawable drawable) {
        ivIcon.setImageDrawable(drawable);
    }

    public void setImageBitmap(Bitmap bitmap) {
        ivIcon.setImageBitmap(bitmap);
    }

    public void setImageResource(@DrawableRes int res) {
        ivIcon.setImageResource(res);
    }

    public void setImageUrl(String url, @DrawableRes int errorDrawable) {
        Picasso.get()
                .load(url)
                .resize(0, MSUIHelper.dp1 * 128)
                .onlyScaleDown()
                .error(errorDrawable)
                .into(ivIcon);
    }

    public void setButtonOnClickListener(OnClickListener listener) {
        tvBtn.setOnClickListener(listener);
    }
}
