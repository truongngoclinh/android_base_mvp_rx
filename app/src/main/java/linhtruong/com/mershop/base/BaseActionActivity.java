package linhtruong.com.mershop.base;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.View;
import linhtruong.com.commons.utils.MSKeyboardUtil;
import linhtruong.com.commons.utils.MSThemeUtil;
import linhtruong.com.commons.widgets.MSToolbar;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;

/**
 * Add toolbar
 *
 * @author linhtruong
 * @date 10/2/18 - 16:00.
 * @organization VED
 */
public abstract class BaseActionActivity extends BaseActivity {
    protected BaseActionActivity THIS = this;
    private BaseActionView mContentView;

    protected abstract BaseActionView onCreateContentView(Bundle savedInstanceState);

    protected abstract void onConfigToolbar(MSToolbar toolbar);

    @Override
    protected void onCreateUI(Bundle bundle) {
        mContentView = onCreateContentView(bundle);
        setContentView(mContentView);
        setSupportActionBar(mContentView.getToolbar());
        mContentView.getToolbar().setNavigationOnClickListener(v -> {
            if (!navigationOnClick(v)) {
                MSKeyboardUtil.dismiss(THIS);
                finish();
            }
        });

        onConfigToolbar(mContentView.getToolbar());
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Toolbar toolbar = mContentView.getToolbar();
        if (toolbar instanceof MSToolbar) {
            ((MSToolbar) toolbar).applyMenuIconTint(
                    getResources().getColor(MSThemeUtil.resolveRes(this, R.attr.msColorImageTintToolBarIcon)));
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected boolean isValid() {
        return true;
    }

    protected boolean navigationOnClick(View v) {
        return false;
    }

    protected Toolbar getToolbar() {
        return mContentView.getToolbar();
    }

    protected boolean supportTheme() {
        return mContentView == null || !mContentView.isToolbarDarkTheme();
    }
}
