package linhtruong.com.mershop.base;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.utils.MSPermissionUtil;
import linhtruong.com.commons.widgets.MSProgressDialog;
import linhtruong.com.commons.widgets.base.IScreenView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.App;
import linhtruong.com.mershop.app.AppComponent;
import linhtruong.com.mershop.app.navigator.ActivityScreenSwitcher;
import linhtruong.com.mershop.app.preferences.AppConfigPref;

import javax.inject.Inject;
import java.lang.ref.WeakReference;

/**
 * Provide general methods for a new common activity
 *
 * @author linhtruong
 * @date 10/1/18 - 11:40.
 * @organization VED
 */
public abstract class BaseActivity extends AppCompatActivity {
    private WeakReference<IScreenView> mContentView;
    private boolean mIsActivityVisible;
    private MSProgressDialog mProgressDialog;
    private View mRootView;

    @Inject
    ActivityScreenSwitcher mActivityScreenSwitcher;

    protected abstract void onCreateUI(Bundle bundle);

    /**
     * Implement this method to inject the dependency for activity
     *
     * @param appComponent the app component
     */
    protected abstract void onCreateComponent(AppComponent appComponent);

    /**
     * mark the activity state
     *
     * @return true if the activity instance is still functional
     */
    protected abstract boolean isValid();

    /**
     * Override this to initialize the base view presenter
     *
     * @return {@link BasePresenter}
     */
    protected BasePresenter<? extends View> presenter() {
        return null;
    }

    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        onCreateComponent((AppComponent) App.getComponent());
        if (mActivityScreenSwitcher == null) {
            throw new IllegalStateException("No injection happened. Add component.inject(this) in onCreateComponent() implementation");
        }
        mActivityScreenSwitcher.attach(this);
        onCreateUI(savedInstanceState);
    }

    @Override
    public void setContentView(View view) {
        if (view instanceof IScreenView) {
            IScreenView evt = (IScreenView) view;
            mContentView = new WeakReference<>(evt);
        }

        super.setContentView(view);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (mContentView != null && isValid()) {
            IScreenView evt = mContentView.get();
            if (evt != null) {
                evt.onShowView();
            }
        }

        if (presenter() != null) {
            presenter().onResume();
        }

        mIsActivityVisible = true;
        mActivityScreenSwitcher.attach(this);
//         BBAppStateManager.getInstance().setAppActive(true);
    }

    @Override
    protected void onPause() {
        if (mContentView != null && isValid()) {
            IScreenView evt = mContentView.get();
            if (evt != null) {
                evt.onHideView();
            }
        }

        if (presenter() != null) {
            presenter().onPause();
        }

        super.onPause();
        mIsActivityVisible = false;
//         BBAppStateManager.getInstance().setAppActive(false);
    }

    @Override
    protected void onDestroy() {
        if (mContentView != null) {
            IScreenView evt = mContentView.get();
            if (evt != null) {
                evt.onDestroy();
            }

            mContentView = null;
        }

        if (presenter() != null) {
            presenter().onDestroy();
        }

        if (mProgressDialog != null) {
            mProgressDialog.close();
        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        super.onStop();

        if (presenter() != null) {
            presenter().onStop();
        }
    }

    /**
     * Resolve the error for network requests and this method must be executed in UI loop
     * @TODO implementation
     * @param e the exception which can describe the situation
     * @return true if this exception has been properly handled
     */
    public boolean resolveError(@Nullable Exception e) {
        return false;
    }

    public void onBack() {
        finish();
    }

    @Override
    public void onBackPressed() {
        onBack();
    }

    public boolean isActivityVisible() {
        return mIsActivityVisible;
    }

    public void toast(int contentRes) {
        toast(getString(contentRes));
    }

    public void toast(String content) {
        if (mRootView == null) {
            mRootView = findViewById(android.R.id.content);
            if (mRootView instanceof ViewGroup && ((ViewGroup) mRootView).getChildCount() == 1) {
                mRootView = ((ViewGroup) mRootView).getChildAt(0);
            }
        }
        Snackbar.make(mRootView, content, Snackbar.LENGTH_SHORT).show();
    }

    public void showLoading(boolean cancelable) {
        showLoading("", cancelable);
    }

    public void showLoading(boolean cancelable, MSProgressDialog.OnBackPressListener listener) {
        showLoading(cancelable);
        if (mProgressDialog != null) {
            mProgressDialog.setOnBackPressedListener(listener);
        }
    }

    public void showLoading(String msg, boolean cancelable) {
        if (isFinishing()) {
            return;
        }

        if (mProgressDialog == null) {
            mProgressDialog = new MSProgressDialog(this);
        } else {
            if (mProgressDialog.isShowing()) {
                mProgressDialog.setMessage(msg);
                return;
            }
        }

        mProgressDialog.setMessage(msg);
        mProgressDialog.setOperationCancelable(cancelable);
        mProgressDialog.show();
    }

    public void hideLoading() {
        if (mProgressDialog == null) {
            return;
        }

        mProgressDialog.dismiss();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        if (presenter() != null) {
            presenter().onSave(outState);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        if (presenter() != null) {
            presenter().onRestore(savedInstanceState);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (presenter() != null) {
            presenter().onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (MSPermissionUtil.verifyPermissions(this, permissions, grantResults)) {
            onPermissionGranted(requestCode);
        }

        if (presenter() != null) {
            presenter().onRequestPermissionsResult(requestCode, permissions, grantResults);
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults); // notify to child fragments
    }

    protected void onPermissionGranted(int requestCode) {
        // override
    }
}
