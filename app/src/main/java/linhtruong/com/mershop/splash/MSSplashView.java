package linhtruong.com.mershop.splash;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;

/**
 * Splash
 *
 * @author linhtruong
 * @date 10/3/18 - 11:31.
 * @organization VED
 */
public class MSSplashView extends BaseActionView {
    @BindView(R.id.ms_splash_logo)
    ImageView mSplashLogo;

    @Override
    protected View createContentView(Context context) {
        return inflate(context, R.layout.com_linhtruong_mershop_splash_activity, null);
    }

    public MSSplashView(Context context) {
        super(context);
    }

    @Override
    protected void bindView() {
       ButterKnife.bind(this);
    }
}

