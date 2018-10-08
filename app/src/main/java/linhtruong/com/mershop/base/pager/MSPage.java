package linhtruong.com.mershop.base.pager;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.commons.utils.MSPermissionUtil;
import linhtruong.com.mershop.BuildConfig;
import linhtruong.com.mershop.base.BaseActivity;
import linhtruong.com.mershop.base.BasePresenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Map;

/**
 * A custom view for adapting complex ui change rules, replace {@link android.app.Fragment}
 *
 * @author linhtruong
 * @date 10/6/18 - 13:44.
 * @organization VED
 */
public abstract class MSPage implements IPageContainer {
    public final String TAG = "[" + getClass().getSimpleName() + "] ";

    protected final IPageContainer mParent;
    private final List<MSPage> mChildPages;
    private View mContentView;

    private final Map<Integer, Runnable> mTasksUponPermissionGranted = new HashMap<>();
    private boolean mIsCreated, mIsStarted, mIsResumed, mIsDestroyed;
    //    private final CompositeSubscription mSubscriptions;

    public MSPage(IPageContainer parent) {
        mParent = parent;
        mChildPages = new ArrayList<>();
    }

    @Override
    public void registerPage(MSPage page) {
        mChildPages.add(page);
        if (mIsStarted) {
            page.onStart();
        }
        if (mIsResumed) {
            page.onResume();
        }
    }

    @Override
    public void deregisterPage(MSPage page) {
        mChildPages.remove(page);
    }

    public Context getContext() {
        return mParent.getActivity();
    }

    public void create(Bundle saveInstanceState) {
        onCreate(saveInstanceState);
        if (!mIsDestroyed) {
            mParent.registerPage(this);
        }
    }

    public void destroy() {
        if (mIsResumed) {
            onPause();
        }

        if (mIsStarted) {
            onStop();
        }

        onDestroy();

        mParent.deregisterPage(this);
    }

    public void setContentView(View contentView) {
        mContentView = contentView;
    }

    public void setContentView(@LayoutRes int layout) {
        setContentView(inflate(layout));
    }

    public View getContentView() {
        return mContentView;
    }

    public void onCreate(Bundle savedInstanceState) {
        if (BuildConfig.DEBUG) {
            if (mIsCreated) {
                toast(getClass().getSimpleName() + "::onCreate() called multiple times");
            }
        }
        log("onCreate");
        mIsCreated = true;
        mIsDestroyed = false;
    }

    public void onResume() {
        log("onResume @%d", hashCode());
        if (BuildConfig.DEBUG) {
            if (mIsResumed) {
                toast(getClass().getSimpleName() + "::onResume() called multiple times");
            }
        }
        mIsResumed = true;
        for (MSPage page : mChildPages) {
            page.onResume();
        }
    }

    public void onPause() {
        log("onPause @%d", hashCode());
        if (BuildConfig.DEBUG) {
            if (!mIsResumed) {
                toast(getClass().getSimpleName() + "::onPause() called multiple times");
            }
        }
        mIsResumed = false;
        for (MSPage page : mChildPages) {
            page.onPause();
        }
    }

    public void onStart() {
        log("onStart @%d", hashCode());
        if (BuildConfig.DEBUG) {
            if (mIsStarted) {
                toast(getClass().getSimpleName() + "::onStart() called multiple times");
            }
        }
        mIsStarted = true;
        for (MSPage page : mChildPages) {
            page.onStart();
        }
    }

    public void onStop() {
        log("onStop @%d", hashCode());
        if (BuildConfig.DEBUG) {
            if (!mIsStarted) {
                toast(getClass().getSimpleName() + "::onStop() called multiple times");
            }
        }
        mIsStarted = false;
        for (MSPage page : mChildPages) {
            page.onStop();
        }
    }

    public void onDestroy() {
        log("onDestroy @%d", hashCode());
        if (mIsDestroyed) {
            if (BuildConfig.DEBUG) {
                toast(getClass().getSimpleName() + "::onDestroy() called multiple times");
            }
            return;
        }

        if (!mChildPages.isEmpty()) {
            List<MSPage> temp = new ArrayList<>(mChildPages);
            mChildPages.clear();

            for (MSPage p : temp) {
                p.onDestroy();
            }
        }

        mIsCreated = false;
        mIsDestroyed = true;
    }

    public void onSaveInstanceState(Bundle outState) {
        log("onSaveInstanceState @%d", hashCode());
        for (MSPage page : mChildPages) {
            page.onSaveInstanceState(outState);
        }
    }

    public boolean onBackPressed() {
        return false;
    }

    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        log("onActivityResult @%d", hashCode());
        for (MSPage page : mChildPages) {
            page.onActivityResult(requestCode, resultCode, data);
        }

        return false;
    }

    public boolean onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                              @NonNull int[] grantResults) {
        log("onRequestPermissionsResult @%d", hashCode());
        if (MSPermissionUtil.verifyPermissions(getActivity(), permissions, grantResults)) {
            Runnable task = mTasksUponPermissionGranted.remove(requestCode);
            if (task != null) {
                task.run();
            }
        }

        for (MSPage page : mChildPages) {
            page.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        return false;
    }

    public void onConfigurationChanged(Configuration newConfig) {
        log("onConfigurationChanged @%d", hashCode());
        for (MSPage page : mChildPages) {
            page.onConfigurationChanged(newConfig);
        }
    }

    public View inflate(@LayoutRes int res) {
        return LayoutInflater.from(getContext()).inflate(res, null);
    }

    public <T extends View> T findViewById(@IdRes int id) {
        if (getContentView() == null) {
            return null;
        }
        return getContentView().findViewById(id);
    }

    public void toast(String msg) {
        getActivity().toast(msg);
    }

    public void toast(@StringRes int res) {
        getActivity().toast(res);
    }

    public void showLoading(boolean cancelable) {
        getActivity().showLoading(cancelable);
    }

    public void hideLoading() {
        getActivity().hideLoading();
    }

    public void log(String content, Object... args) {
        if (TextUtils.isEmpty(content)) {
            return;
        }

        String log;
        try {
            log = args == null || args.length == 0 ? content : String.format(content, args);
        } catch (IllegalFormatException e) {
            MSLogUtil.e(e);
            return;
        }

        MSLogUtil.d(TAG + log);
    }

    public boolean isDestroyed() {
        return mIsDestroyed;
    }

    public void recreate() {
        if (!mIsCreated) {
            return;
        }

        Bundle bundle = new Bundle();
        ViewGroup parent = null;
        ViewGroup.LayoutParams params = null;
        boolean wasStarted = mIsStarted;
        boolean wasResumed = mIsResumed;

        List<MSPage> childList = new ArrayList<>(mChildPages);
        mChildPages.clear();

        if (!mIsDestroyed) {
            onSaveInstanceState(bundle);

            if (mContentView != null) {
                params = mContentView.getLayoutParams();
                ViewParent vp = mContentView.getParent();
                if (vp instanceof ViewGroup) {
                    parent = (ViewGroup) vp;
                    parent.removeView(mContentView);
                }
            }

            if (wasResumed) {
                onPause();
            }

            if (wasStarted) {
                onStop();
            }

            onDestroy();
            mIsDestroyed = false;
        }

        onCreate(bundle);
        if (parent != null && mContentView != null) {
            parent.addView(mContentView, params);
        }

        if (wasStarted) {
            onStart();
        }

        if (wasResumed) {
            onResume();
        }

        for (MSPage p : childList) {
            p.recreate();
            mChildPages.add(p);
        }
    }

    public void runWithPermission(MSPermissionUtil.Permissions permission, Runnable task) {
        if (MSPermissionUtil.isPermissionGranted(getContext(), permission)) {
            task.run();
        } else {
            mTasksUponPermissionGranted.put(permission.getRequestCode(), task);
            MSPermissionUtil.requestPermission(getActivity(), permission);
        }
    }

    @Override
    public BaseActivity getActivity() {
        return mParent.getActivity();
    }

    @Override
    public BasePresenter getPresenter() {
        return mParent.getPresenter();
    }
}


