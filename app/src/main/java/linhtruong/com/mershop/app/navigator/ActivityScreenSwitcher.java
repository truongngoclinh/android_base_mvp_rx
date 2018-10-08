package linhtruong.com.mershop.app.navigator;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;

import java.lang.ref.WeakReference;
import java.security.InvalidParameterException;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/2/18 - 11:48.
 * @organization VED
 */
public class ActivityScreenSwitcher implements ISwitcher {
    private WeakReference<Activity> mActivityWeakReference;

    public void attach(Activity activity) {
        mActivityWeakReference = new WeakReference<>(activity);
    }

    @Override
    public void open(IScreen screen) {
        final Activity activity = getAttachedActivity();
        if (activity == null) {
            return;
        }
        if (screen instanceof ActivityScreen) {
            ActivityScreen activityScreen = ((ActivityScreen) screen);
            Intent intent = activityScreen.intent(activity);
            ActivityCompat
                    .startActivity(activity, intent, activityScreen.activityOptions(activity));
        } else {
            throw new InvalidParameterException("Only ActivityScreen objects allowed");
        }
    }

    @Override
    public void goBack() {
        final Activity activity = getAttachedActivity();
        if (activity != null) {
            activity.onBackPressed();
        }
    }

    @Override
    public void openAndFinish(IScreen screen) {
        final Activity activity = getAttachedActivity();
        open(screen);
        if (activity != null) {
            activity.finish();
        }
    }

    public void startForResult(int requestCode, IScreen screen) {
        final Activity activity = getAttachedActivity();
        if (activity != null && screen instanceof ActivityScreen) {
            ActivityScreen activityScreen = ((ActivityScreen) screen);
            Intent intent = activityScreen.intent(activity);
            activity.startActivityForResult(intent, requestCode);
        } else {
            throw new InvalidParameterException("Only ActivityScreen objects are allowed");
        }
    }

    @Override
    public void closeWithResult(int resultCode, Intent result) {
        final Activity activity = getAttachedActivity();
        if (activity != null) {
            activity.setResult(resultCode, result);
            activity.finish();
        }
    }

    public Activity getAttachedActivity() {
        return mActivityWeakReference.get();
    }
}
