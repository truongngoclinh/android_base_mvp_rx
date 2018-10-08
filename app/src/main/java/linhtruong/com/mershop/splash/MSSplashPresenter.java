package linhtruong.com.mershop.splash;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Base64;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.mershop.BuildConfig;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.app.navigator.ActivityScreenSwitcher;
import linhtruong.com.mershop.app.navigator.Navigator;
import linhtruong.com.mershop.base.BasePresenter;

import javax.inject.Inject;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Splash, check app signature
 *
 * @author linhtruong
 * @date 10/3/18 - 11:30.
 * @organization VED
 */
public class MSSplashPresenter extends BasePresenter<MSSplashView> {
    private final int DELAY = 2000;
    private final Handler mUI = new Handler(Looper.getMainLooper());
    private MSSplashActivity mActivity;

    @Inject
    ActivityScreenSwitcher mScreenSwitcher;

    public MSSplashPresenter(MSSplashActivity activity) {
        mActivity = activity;
    }

    @Override
    protected void onLoad() {
        if (isPirateVersion()) {
            showWarning();
            return;
        }

        new Handler().postDelayed(() -> enterAppHome(), DELAY);
    }

    private void enterAppHome() {
        Navigator.onNavigationHomeActivity(mScreenSwitcher);
    }

    private boolean isPirateVersion() {
        try {
            PackageInfo info = mActivity.getApplicationContext().getPackageManager().getPackageInfo(
                    BuildConfig.APPLICATION_ID, PackageManager.GET_SIGNATURES);
            MessageDigest md = MessageDigest.getInstance("SHA");
            md.update(info.signatures[0].toByteArray());
            String sign = Base64.encodeToString(md.digest(), Base64.URL_SAFE).trim();
            MSLogUtil.i("signature %s", sign);
            return (BuildConfig.DEBUG && !sign.equals(MSConstValue.SIGN.DEBUG))
                    || (!BuildConfig.DEBUG && !(sign.equals(MSConstValue.SIGN.RELEASE)));

        } catch (PackageManager.NameNotFoundException | NoSuchAlgorithmException e) {
            MSLogUtil.e(e);
        }

        return false;
    }

    private void showWarning() {
        new MaterialDialog.Builder(getActivity())
                .title(R.string.ms_label_warning_unofficial)
                .titleColor(Color.RED)
                .iconRes(R.drawable.topup_nav_ic_cautious)
                .content(R.string.ms_label_warning_content)
                .cancelable(false)
                .positiveText(R.string.ms_label_ok)
                .onPositive((dialog, which) -> {
                    getActivity().startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store?hl=en")));
                    mUI.postDelayed(() -> {
                        String packageName = getActivity().getPackageName();
                        Uri packageURI = Uri.parse("package:" + packageName);
                        getActivity().startActivity(new Intent(Intent.ACTION_DELETE, packageURI));
                        getActivity().finish();
                        System.exit(1);
                    }, 2000);
                })
                .show();
    }


    @NonNull
    @Override
    public BasePresenter getPresenter() {
        return this;
    }

    @NonNull
    @Override
    public MSSplashActivity getActivity() {
        return mActivity;
    }
}

