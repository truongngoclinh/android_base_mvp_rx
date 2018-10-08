package linhtruong.com.mershop.home;

import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.view.ViewCompat;
import android.support.v4.view.ViewPager;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.helpers.MSUIHelper;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.commons.utils.MSThemeUtil;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.preferences.AppConfigPref;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.base.pager.MSPage;
import linhtruong.com.mershop.di.MSHomeComponent;
import linhtruong.com.mershop.home.me.MSHomeMePage;
import linhtruong.com.mershop.home.chat.MSHomeChatPage;
import linhtruong.com.mershop.home.timeline.MSTimelinePage;

import java.util.ArrayList;
import java.util.List;

/**
 * Main presenter
 *
 * @author linhtruong
 * @date 10/2/18 - 11:41.
 * @organization VED
 */
public class MSHomePresenter extends BasePresenter<MSHomeView> {
    private final MSHomeActivity mActivity;
    private final MSHomeComponent mComponent;
    private MSHomeAdapter mAdapter;
    private IHomePageActionListener mListener;

    public MSHomePresenter(MSHomeActivity activity, MSHomeComponent homeComponent) {
        mActivity = activity;
        mComponent = homeComponent;
    }

    @Override
    protected void onLoad() {
        super.onLoad();

        applyTheme(AppConfigPref.getTheme());
        initViews();
        initActions();
        initTabs();
    }

    private void initViews() {
        mAdapter = new MSHomeAdapter();
//        getView().mTabLayout.setAnimateSelection(false);
        getView().mViewPager.setHorizontalSwipeEnabled(false);
        getView().mViewPager.setOffscreenPageLimit(5);
        getView().mViewPager.setAdapter(mAdapter);
        getView().mTabLayout.setSelectedTabIndicatorColor(Color.TRANSPARENT);
        getView().mTabLayout.setupWithViewPager(getView().mViewPager);
    }

    private void initTabs() {
        List<MSHomeBasePage> pages = new ArrayList<>();
        List<String> titles = new ArrayList<>();
        List<View> tabs = new ArrayList<>();

        int tabFontColorNormal = mActivity.getResources().getColor(R.color.ms_home_bottom_tab_text);
        int tabFontColorSelected = mActivity.getResources().getColor(R.color.ms_home_bottom_tab_text_selected);

        // Timeline page
        MSHomeTabItemView timelineTabItemView = new MSHomeTabItemView(mActivity);
        timelineTabItemView.setIconTintList(tabFontColorNormal, tabFontColorSelected);
        timelineTabItemView.setIcon(R.drawable.tabicon_discover, R.drawable.tabicon_discover_press);
        timelineTabItemView.setTitle(R.string.ms_home_page_title);
        tabs.add(timelineTabItemView);

        MSTimelinePage homePage = new MSTimelinePage(mComponent, this, null);
        homePage.setActionListener(mListener);
        String homeTitle = mActivity.getString(R.string.ms_home_page_title);
        pages.add(homePage);
        titles.add(homeTitle);

        // Chat page
        MSHomeTabItemView chatTabItemView = new MSHomeTabItemView(mActivity);
        chatTabItemView.setIconTintList(tabFontColorNormal, tabFontColorSelected);
        chatTabItemView.setIcon(R.drawable.tabicon_chat, R.drawable.tabicon_chat_press);
        chatTabItemView.setTitle(R.string.ms_chat_page_title);
        tabs.add(chatTabItemView);

        MSHomeChatPage chatPage = new MSHomeChatPage(this, null);
        chatPage.setActionListener(mListener);
        String notificationTitle = mActivity.getString(R.string.ms_chat_page_title);
        pages.add(chatPage);
        titles.add(notificationTitle);

        // Me page
        MSHomeTabItemView meTabItemView = new MSHomeTabItemView(mActivity);
        meTabItemView.setIconTintList(tabFontColorNormal, tabFontColorSelected);
        meTabItemView.setIcon(R.drawable.tabicon_me, R.drawable.tabicon_me_press);
        meTabItemView.setTitle(R.string.ms_me_page_title);
        tabs.add(meTabItemView);

        MSHomeMePage mePage = new MSHomeMePage(this, null);
        mePage.setActionListener(mListener);
        String meTitle = mActivity.getString(R.string.ms_me_page_title);
        pages.add(mePage);
        titles.add(meTitle);

        for (int i = 0; i < pages.size(); i++) {
            mAdapter.addPage(pages.get(i), titles.get(i));
        }
        mAdapter.notifyDataSetChanged();

        for (int i = 0; i < pages.size(); i++) {
            View view = tabs.get(i);
            view.setMinimumWidth(MSUIHelper.SCREEN_WIDTH_PX / pages.size());
            getView().mTabLayout.getTabAt(i).setCustomView(view);
        }
    }

    private void initActions() {
        getView().mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                MSPage page = mAdapter.getPage(position);
                if (page instanceof MSHomeMePage) {
                    getView().mToolbar.setVisibility(View.GONE);
                } else if (page instanceof MSHomeChatPage) {
                    getView().mToolbar.setVisibility(View.VISIBLE);
                    getView().mToolbar.setTitle(R.string.ms_chat_page_title);
                } else {
                    getView().mToolbar.setVisibility(View.VISIBLE);
                    getView().mToolbar.setTitle(R.string.ms_mershop);
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mListener = new IHomePageActionListener() {
            @Override
            public void setTabBarVisible(boolean visible, String tabKey, String title) {

            }

            @Override
            public void setHomeMenuVisible(boolean visible, String tabKey) {

            }

            @Override
            public void setFloatingButtonVisible(boolean visible, String tabKey, String type) {

            }

            @Override
            public void updateTheme(int newTheme) {
                applyTheme(newTheme);
            }
        };
    }

    private void applyTheme(final int newTheme) {
        // get a snapshot
        Bitmap snapshot = null;
        final View decorView = mActivity.getWindow().getDecorView();
        try {
            decorView.buildDrawingCache(true);
            snapshot = decorView.getDrawingCache(true);
            if (snapshot != null) {
                snapshot = snapshot.copy(snapshot.getConfig(), false);
            }
            decorView.destroyDrawingCache();
        } catch (OutOfMemoryError e) {
            MSLogUtil.e(e);
        }

        if (decorView instanceof ViewGroup) {
            final View view = new View(mActivity);
            view.setOnTouchListener((v, event) -> true);
            if (snapshot != null) {
                view.setBackgroundDrawable(new BitmapDrawable(mActivity.getResources(), snapshot));
            }
            ((ViewGroup) decorView).addView(view, ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            ViewCompat.animate(view)
                    .setStartDelay(500)
                    .setDuration(1000)
                    .alpha(0f)
                    .setInterpolator(new AccelerateDecelerateInterpolator())
                    .withStartAction(() -> {
                        // status bar
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            MSThemeUtil.setStatusBarTextColorLight(mActivity, newTheme == MSConstValue.THEME.DARK);
                        }
                    })
                    .withEndAction(() -> {
                        ((ViewGroup) decorView).removeView(view);
                        decorView.setDrawingCacheEnabled(false);
                    })
                    .start();
        }

        // set theme
        if (newTheme == MSConstValue.THEME.DARK) {
            mActivity.setTheme(R.style.MSAppDarkTheme);
        } else {
            mActivity.setTheme(R.style.MSAppTheme);
        }

        // bg
        int bgColorRes = MSThemeUtil.resolveRes(mActivity, R.attr.msColorBgDefault);
        mActivity.getWindow().setBackgroundDrawableResource(bgColorRes);
        getView().mRoot.setBackgroundResource(bgColorRes);


        mActivity.invalidateOptionsMenu();
    }

    @NonNull
    @Override
    public MSHomeActivity getActivity() {
        return mActivity;
    }

    @NonNull
    @Override
    public BasePresenter getPresenter() {
        return this;
    }
}
