package linhtruong.com.commons.managers;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.widget.Toast;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/2/18 - 11:59.
 * @organization VED
 */
public class MSToastManager {
    private static final Handler mUIHandler = new Handler(Looper.getMainLooper());
    private static Toast mLastToast; // thread safe

    private static Context mContext;

    public static void init(Context context) {
        mContext = context.getApplicationContext();
    }

    public static void show(@StringRes int res) {
        show(res, Toast.LENGTH_SHORT);
    }

    public static void show(@StringRes int res, int length) {
        show(mContext.getString(res), length);
    }

    public static void show(String toastMessage) {
        show(toastMessage, Toast.LENGTH_SHORT);
    }

    public static void show(final String toastMessage, final int length) {
        if (TextUtils.isEmpty(toastMessage)) {
            return;
        }

        if (Looper.getMainLooper() == Looper.myLooper()) {
            showToast(toastMessage, length);
        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    showToast(toastMessage, length);
                }
            });
        }
    }

    public static void dismiss() {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            if (mLastToast != null) {
                mLastToast.cancel();
            }
        } else {
            mUIHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mLastToast != null) {
                        mLastToast.cancel();
                    }
                }
            });
        }
    }

    private static void showToast(String toastMessage, int length) {
        if (mLastToast != null) {
            mLastToast.cancel();
        }
        mLastToast = Toast.makeText(mContext, toastMessage, length);
        mLastToast.show();
    }

    private MSToastManager() {
    }
}
