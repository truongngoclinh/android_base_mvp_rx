package linhtruong.com.mershop.app.navigator;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityOptionsCompat;
import android.view.View;
import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/2/18 - 15:29.
 * @oganization VED
 */
public abstract class ActivityScreen implements IScreen {
    @Nullable
    private View mTransitionView;
    private String mTransitionName;

    public void attachTransitionView(@Nullable View view, String name) {
        mTransitionView = view;
        mTransitionName = name;
    }

    @Nullable
    protected View detachTransitionView() {
        View view = mTransitionView;
        mTransitionView = null;
        return view;
    }

    @NonNull
    public final Intent intent(Context context) {
        Intent intent = new Intent(context, activityClass());
        configureIntent(intent);
        return intent;
    }

    protected final Bundle activityOptions(Activity activity) {
        View transitionView = detachTransitionView();
        if (transitionView == null) return null;

        return ActivityOptionsCompat.makeSceneTransitionAnimation(activity, transitionView, mTransitionName).toBundle();
    }

    protected abstract void configureIntent(@NonNull Intent intent);

    protected abstract Class<? extends Activity> activityClass();
}
