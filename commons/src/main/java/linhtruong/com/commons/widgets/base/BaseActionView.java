package linhtruong.com.commons.widgets.base;

import android.content.Context;
import android.support.v7.widget.Toolbar;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.FrameLayout;
import linhtruong.com.commons.R;
import linhtruong.com.commons.widgets.MSToolbar;

import java.lang.ref.WeakReference;

/**
 * Base action view, support toolbar with mode
 *
 * @author linhtruong
 * @date 10/2/18 - 16:01.
 * @organization VED
 */
public abstract class BaseActionView extends BaseView {
    private final MSToolbar mToolbar;
    private final FrameLayout mContainer;
    private final View mContentView;
    private WeakReference<IScreenView> mScreen;

    protected enum ActionBarMode {
        Normal, Floating, Spinner
    }

    protected abstract View createContentView(Context context);
    protected abstract void bindView();

    public BaseActionView(Context context) {
        super(context);
        int layoutRes = getLayoutRes();
        Context ctx = context;
        if (isToolbarDarkTheme()) {
            ctx = new ContextThemeWrapper(context, R.style.BaseToolBarDark);
        }

        inflate(ctx, layoutRes, this);
        mToolbar = findViewById(R.id.ms_toolbar);
        if (isMenuPopupDarkTheme()) {
            mToolbar.setPopupTheme(R.style.PopupThemeDark);
        }

        mContainer = findViewById(R.id.ms_content_layout);
        mContentView = createContentView(ctx);
        mContainer.addView(mContentView);

        if (mContentView instanceof IScreenView) {
            mScreen = new WeakReference<>((IScreenView) mContentView);
        } else {
            mScreen = new WeakReference<>(null);
        }

        bindView();
    }

    public MSToolbar getToolbar() {
        return mToolbar;
    }

    public FrameLayout getContentContainer() {
        return mContainer;
    }

    public View getContentView() {
        return mContentView;
    }

    protected ActionBarMode getActionBarMode() {
        return ActionBarMode.Normal;
    }

    private int getLayoutRes() {
        switch (getActionBarMode()) {
            case Floating:
                return R.layout.com_linhtruong_mershop_layout_float_toolbar;
            case Spinner:
                return R.layout.com_linhtruong_mershop_layout_spinner_toolbar;
            default:
                return R.layout.com_linhtruong_mershop_layout_base_toolbar;
        }
    }

    public boolean isToolbarDarkTheme() {
        return false;
    }

    protected boolean isMenuPopupDarkTheme() {
        return isToolbarDarkTheme();
    }

    @Override
    public void onShowView() {
        IScreenView screenView = mScreen.get();
        if (screenView != null) {
            screenView.onShowView();
        }
    }

    @Override
    public void onHideView() {
        IScreenView screenView = mScreen.get();
        if (screenView != null) {
            screenView.onHideView();
        }
    }

    @Override
    public void onDestroy() {
        IScreenView screenView = mScreen.get();
        if (screenView != null) {
            screenView.onDestroy();
        }
    }
}
