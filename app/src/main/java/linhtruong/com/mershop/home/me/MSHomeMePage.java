package linhtruong.com.mershop.home.me;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import butterknife.BindView;
import butterknife.ButterKnife;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.utils.MSThemeUtil;
import linhtruong.com.commons.widgets.MSCircleImageView;
import linhtruong.com.commons.widgets.MSImageView;
import linhtruong.com.commons.widgets.MSTextView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.preferences.AppConfigPref;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.home.MSHomeBasePage;

/**
 * Me page: account info, change language, settings, etc.
 *
 * @author linhtruong
 * @date 10/6/18 - 15:15.
 * @organization VED
 */
public class MSHomeMePage extends MSHomeBasePage implements View.OnClickListener {
    @BindView(R.id.ms_me_scrollview)
    ScrollView mScrollView;

    @BindView(R.id.ms_iv_avatar)
    MSCircleImageView mAvatar;

    @BindView(R.id.ms_tv_name)
    MSTextView mTvName;

    @BindView(R.id.ms_tv_details)
    MSTextView mTvDetails;

    @BindView(R.id.ms_layout_switch_theme)
    LinearLayout mSwitchThemeLayout;

    @BindView(R.id.ms_iv_btn_switch_theme)
    MSImageView mSwitchTheme;

    @BindView(R.id.ms_tv_switch_theme)
    MSTextView mTvTheme;

    @BindView(R.id.ms_tv_switch_theme_placeholder)
    MSTextView mTvThemePlaceholder;

    @BindView(R.id.ms_layout_settings)
    LinearLayout mSettings;

    @BindView(R.id.ms_red_dot_settings)
    View mReddot;

    @BindView(R.id.ms_layout_profile)
    LinearLayout mLayoutProfile;

    @BindView(R.id.ms_top_divider)
    View mTopDivider;

    public MSHomeMePage(BasePresenter presenter, Bundle params) {
        super(presenter, params);
    }

    @Override
    protected View onCreateView(Bundle savedInstanceState) {
        View contentView =
                LayoutInflater.from(getContext()).inflate(R.layout.com_linhtruong_mershop_home_me_page, null);
        ButterKnife.bind(this, contentView);
        initData();

        return contentView;
    }

    private void initData() {
        mTvName.setText(R.string.ms_label_guess);
        mTvDetails.setText(R.string.ms_tap_to_login);
        mSwitchThemeLayout.setOnClickListener(this);
        mSettings.setOnClickListener(this);

        adjustSwitchTheme();
    }

    @Override
    protected void onFirstLoad() {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ms_layout_settings:
                toast(R.string.ms_under_development_notice);
                break;
            case R.id.ms_layout_switch_theme:
                switchTheme();
                break;
            default:
                break;
        }
    }

    private void switchTheme() {
        mScrollView.scrollTo(0, 0);
        mScrollView.post(() -> {
            int currentTheme = AppConfigPref.getTheme();
            if (currentTheme == MSConstValue.THEME.DARK) {
                AppConfigPref.setTheme(MSConstValue.THEME.LIGHT);
                if (mListener != null) {
                    mListener.updateTheme(MSConstValue.THEME.LIGHT);
                }
            } else {
                AppConfigPref.setTheme(MSConstValue.THEME.DARK);
                if (mListener != null) {
                    mListener.updateTheme(MSConstValue.THEME.DARK);
                }
            }

            // switch
            adjustSwitchTheme();
        });
    }


    private void adjustSwitchTheme() {
        if (AppConfigPref.getTheme() == MSConstValue.THEME.DARK) {
            mSwitchTheme.setImageResource(R.drawable.me_nav_ic_dark);
            mSwitchTheme.setTintColorList(MSThemeUtil.resolveRes(getContext(), R.attr.msColorSelectorImageTintTransparent));
            mTvTheme.setText(R.string.ms_day_mode);
            mTvThemePlaceholder.setText(R.string.ms_night_mode);

            // TODO: theme autochange to work
            mAvatar.setImageResource(R.drawable.me_blackprofile_user_default);
            mLayoutProfile.setBackgroundResource(R.drawable.com_linhtruong_mershop_dark_item_selector_default_bg);
            mSettings.setBackgroundResource(R.drawable.com_linhtruong_mershop_dark_item_selector_default_bg);
            mTopDivider.setBackgroundResource(R.color.ms_dark_divider);
        } else {
            mSwitchTheme.setImageResource(R.drawable.me_nav_ic_light);
            mSwitchTheme.setTintColorList(MSThemeUtil.resolveRes(getContext(), R.attr.msColorSelectorImageTintTransparent));
            mTvTheme.setText(R.string.ms_night_mode);
            mTvThemePlaceholder.setText(R.string.ms_day_mode);

            // TODO: theme autochange to work
            mAvatar.setImageResource(R.drawable.me_whiteprofile_user_default);
            mLayoutProfile.setBackgroundResource(R.drawable.com_linhtruong_mershop_item_selector_default_bg);
            mSettings.setBackgroundResource(R.drawable.com_linhtruong_mershop_item_selector_default_bg);
            mTopDivider.setBackgroundResource(R.color.ms_color_divider);
        }
    }

}

