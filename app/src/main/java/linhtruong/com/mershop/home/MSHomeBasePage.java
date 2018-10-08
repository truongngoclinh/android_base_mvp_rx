package linhtruong.com.mershop.home;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.commons.widgets.MSErrorActionView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.base.pager.MSPagerPage;

/**
 * Main page base
 *
 * @author linhtruong
 * @date 10/6/18 - 14:26.
 * @organization VED
 */
public abstract class MSHomeBasePage extends MSPagerPage {
    private static final long DEFAULT_INITIAL_LOAD_DELAY = 2000;

    protected final Handler mUiHandler;
    protected boolean mShowTabBar = true;
    protected String mBgColor;
    protected String mFloatBtnType;
    protected String mNavTitle;

    protected IHomePageActionListener mListener;

    private final Bundle mParams;
    private String mTabKey;
    private ViewStub mVsErrorAction;
    private MSErrorActionView mErrorActionView;
    private View mContentView;
    private boolean mScheduledInitialLoading, mStartedInitialLoading;

    private final Runnable INITIAL_LOAD_TASK = new Runnable() {
        @Override
        public void run() {
            if (mStartedInitialLoading) {
                return;
            }

            log("kick off initial loading (delayed)");
            mStartedInitialLoading = true;
            onFirstLoad();
        }
    };

    public MSHomeBasePage(BasePresenter presenter, Bundle params) {
        super(presenter);
        mParams = params;
        mUiHandler = new Handler(Looper.getMainLooper());
    }

    protected void parseParams(Bundle params) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (mParams != null) {
            parseParams(mParams);
        }
        FrameLayout rootView = (FrameLayout) LayoutInflater.from(getContext())
                .inflate(R.layout.com_linhtruong_mershop_home_base, null);
        setContentView(rootView);

        mVsErrorAction = rootView.findViewById(R.id.ms_vs_error_action);

        mContentView = onCreateView(savedInstanceState);
        if (mContentView != null) {
            rootView.addView(mContentView, 0);
//            onStartMonitorNetworkStatus();
        }

        if (isHidden()) {
            mScheduledInitialLoading = true;
            mUiHandler.postDelayed(INITIAL_LOAD_TASK, getInitialLoadDelay());
        } else {
            MSLogUtil.d("## run onFirstLoad in onCreate");
            onFirstLoad();
        }
    }

    protected long getInitialLoadDelay() {
        return DEFAULT_INITIAL_LOAD_DELAY;
    }

    /*
    protected void onStartMonitorNetworkStatus() {
        Subscription subscription = connectivityMonitor().getStatus()
                .skip(1)
                .observeOn(TaskSchedulers.UI)
                .subscribe(new DataObserver<Boolean>() {
                    @Override
                    public void onNext(Boolean hasInternet) {
                        if (hasInternet) {
                            if (requiredLogin() && !GGUserSession.isUserLoggedIn()) {
                                showLogin();
                                return;
                            }
                            onReload();
                        } else if (shouldShowNetworkErrorView()) {
                            showNetworkError();
                        }
                    }
                });
        addSubscription(subscription);
    }*/

    protected abstract View onCreateView(Bundle savedInstanceState);

    protected boolean shouldShowNetworkErrorView() {
        return true;
    }

    public void setActionListener(IHomePageActionListener listener) {
        mListener = listener;
    }

    public String getTabKey() {
        return mTabKey;
    }

    public void setTabKey(String tabKey) {
        mTabKey = tabKey;
    }

    public void notifyState() {
        if (mListener != null) {
            mListener.setTabBarVisible(mShowTabBar, mTabKey, mNavTitle);

            if (isHidden() || TextUtils.isEmpty(mFloatBtnType)) {
                mListener.setFloatingButtonVisible(false, mTabKey, null);
            } else {
                mListener.setFloatingButtonVisible(true, mTabKey, mFloatBtnType);
            }
        }
    }

    protected void showNetworkError() {
        hideLoading();
        initErrorAction();
        mErrorActionView.setVisibility(View.VISIBLE);
        mErrorActionView.setImageResource(R.drawable.ic_network_error);
        if (mContentView != null) {
            mContentView.setVisibility(View.GONE);
        }
        mErrorActionView.setText(R.string.ms_network_error);
        mErrorActionView.setButtonText(R.string.ms_label_refresh);
//        mErrorActionView.setButtonOnClickListener(v -> onClickErrorRefresh());
    }
/*
    protected void onClickErrorRefresh() {
        if (NetworkUtil.check().isConnected()) {
            if (requiredLogin() && !GGUserSession.isUserLoggedIn()) {
                showLogin();
                return;
            }
            onReload();
        }
    }*/

    /**
     * Overwrite this method to react to network changes and user initiated reload action. <b>Warning: </b> this method
     * will <b>NOT</b> be automatically called for the first time. Each fragment subclass should determine on their own
     * when to start the first loading.
     */
    protected void onReload() {
        if (mContentView != null) {
            hideErrorAction();
            if (mContentView.getVisibility() != View.VISIBLE) mContentView.setVisibility(View.VISIBLE);
        }
    }

    protected abstract void onFirstLoad();

    protected void initErrorAction() {
        if (mErrorActionView != null) {
            return;
        }

        mErrorActionView = (MSErrorActionView) mVsErrorAction.inflate();
        mVsErrorAction = null;
    }

    protected void hideErrorAction() {
        if (mErrorActionView == null) {
            return;
        }
        if (mErrorActionView.getVisibility() != View.GONE) mErrorActionView.setVisibility(View.GONE);
    }

    @Override
    public void onShown() {
        super.onShown();
        onVisibilityChange();
    }

    @Override
    protected void onFirstShown() {
        super.onFirstShown();

        if (!mScheduledInitialLoading || mStartedInitialLoading) {
            return;
        }

        // first shown tab, kick off loading immediately if ready
        log("kick off initial loading immediately");
        mStartedInitialLoading = true;
        mUiHandler.removeCallbacks(INITIAL_LOAD_TASK);
        onFirstLoad();
    }

    @Override
    public void onHidden() {
        super.onHidden();
        onVisibilityChange();
    }

    private void onVisibilityChange() {
        notifyState();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mUiHandler.removeCallbacksAndMessages(null);
    }
}
