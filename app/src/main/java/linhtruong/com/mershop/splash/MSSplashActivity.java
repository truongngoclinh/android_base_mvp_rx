package linhtruong.com.mershop.splash;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import linhtruong.com.commons.widgets.MSToolbar;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.app.AppComponent;
import linhtruong.com.mershop.base.BaseActionActivity;

/**
 * Splash
 *
 * @author linhtruong
 * @date 10/1/18 - 16:17.
 * @organization VED
 */
public class MSSplashActivity extends BaseActionActivity {
    private MSSplashPresenter mPresenter;

    @Override
    protected BaseActionView onCreateContentView(Bundle savedInstanceState) {
        MSSplashView view = new MSSplashView(this);
        mPresenter.takeView(view);
        return view;
    }

    @Override
    protected void onConfigToolbar(MSToolbar toolbar) {
        toolbar.setVisibility(View.GONE);
    }

    @Override
    protected void onCreateComponent(AppComponent appComponent) {
        mPresenter = new MSSplashPresenter(this);
        appComponent.inject(this);
        appComponent.inject(mPresenter);
    }
}
