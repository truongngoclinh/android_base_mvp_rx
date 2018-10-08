package linhtruong.com.mershop.home;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import linhtruong.com.commons.widgets.MSToolbar;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.AppComponent;
import linhtruong.com.mershop.app.navigator.ActivityScreen;
import linhtruong.com.mershop.base.BaseActionActivity;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.mershop.di.MSHomeComponent;

/**
 * Main activity
 *
 * @author linhtruong
 * @date 10/1/18 - 17:13.
 * @organization VED
 */
public class MSHomeActivity extends BaseActionActivity {
    private MSHomePresenter mPresenter;

    @Override
    protected BaseActionView onCreateContentView(Bundle savedInstanceState) {
        MSHomeView view = new MSHomeView(this);
        mPresenter.takeView(view);
        return view;
    }

    @Override
    protected void onConfigToolbar(MSToolbar toolbar) {
        toolbar.setNavigationIcon(null);
        toolbar.setIcon(R.drawable.icon_launcher);
        toolbar.setNavigationOnClickListener(null);
        toolbar.setTitle(R.string.ms_mershop);
    }

    @Override
    protected void onCreateComponent(AppComponent appComponent) {
        MSHomeComponent homeComponent = MSHomeComponent.Initializer.init(appComponent);
        mPresenter = new MSHomePresenter(this, homeComponent);

        homeComponent.inject(this);
        homeComponent.inject(mPresenter);
    }

    @Override
    protected BasePresenter<? extends View> presenter() {
        return mPresenter;
    }

    public static class Screen extends ActivityScreen {
        public Screen() {
        }

        @Override
        protected void configureIntent(Intent intent) {

        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return MSHomeActivity.class;
        }
    }
}
