package linhtruong.com.mershop.home.timeline.sell;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import linhtruong.com.commons.widgets.MSToolbar;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.AppComponent;
import linhtruong.com.mershop.app.navigator.ActivityScreen;
import linhtruong.com.mershop.base.BaseActionActivity;

/**
 * @TODO under development
 *
 * @author linhtruong
 * @date 10/8/18 - 16:48.
 * @organization VED
 */
public class MSTimelineSellActivity extends BaseActionActivity {
    private MSTimelineSellPresenter mPresenter;

    @Override
    protected BaseActionView onCreateContentView(Bundle savedInstanceState) {
        MSTimelineSellView view = new MSTimelineSellView(this);
        mPresenter.takeView(view);

        return view;
    }

    @Override
    protected void onConfigToolbar(MSToolbar toolbar) {
        toolbar.setTitle(R.string.ms_label_camera);
    }

    @Override
    protected void onCreateComponent(AppComponent appComponent) {
        mPresenter = new MSTimelineSellPresenter(this);
    }

    public class Screen extends ActivityScreen {
        @Override
        protected void configureIntent(Intent intent) {

        }

        @Override
        protected Class<? extends Activity> activityClass() {
            return null;
        }
    }
}
