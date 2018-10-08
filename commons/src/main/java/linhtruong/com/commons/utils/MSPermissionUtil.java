package linhtruong.com.commons.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import linhtruong.com.commons.R;

/**
 * Android M+ permission utilities
 *
 * @author linhtruong
 * @date 10/4/18 - 10:06.
 * @organization VED
 */
public class MSPermissionUtil {
    public static final int REQUEST_PERMISSION_SETTING = 0x1739;
    public static boolean mShownRationalDialogOnRequest = false;

    public static boolean isPermissionGranted(Context context, Permissions permission) {
        return ContextCompat.checkSelfPermission(context, permission.getPermission())
                == PackageManager.PERMISSION_GRANTED;
    }

    /**
     * Requests the permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     *
     * @param activity
     */
    public static void requestPermission(final Activity activity, final Permissions permission) {
        MSLogUtil.d("Permission has NOT been granted. Requesting permission.");
        if (ActivityCompat.shouldShowRequestPermissionRationale(activity, permission.getPermission())) {
            mShownRationalDialogOnRequest = true;
            showRationaleDialog(activity, R.string.ms_label_ok,
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            ActivityCompat.requestPermissions(activity, new String[]{permission.getPermission()},
                                    permission.getRequestCode());
                        }
                    });
        } else {
            // Permission has not been granted yet. Request it directly.
            mShownRationalDialogOnRequest = false;
            ActivityCompat.requestPermissions(activity, new String[]{permission.getPermission()},
                    permission.getRequestCode());
        }
    }

    /**
     * Requests the permission.
     * If the permission has been denied previously, a dialog will prompt the user to grant the
     * permission, otherwise it is requested directly.
     *
     * @param fragment
     */
    public static void requestPermission(final Fragment fragment, final Permissions permission) {
        MSLogUtil.d("Permission has NOT been granted. Requesting permission.");
        if (fragment.shouldShowRequestPermissionRationale(permission.getPermission())) {
            mShownRationalDialogOnRequest = true;
            showRationaleDialog(fragment.getContext(), R.string.ms_label_ok,
                    new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            fragment.requestPermissions(new String[]{permission.getPermission()},
                                    permission.getRequestCode());
                        }
                    });
        } else {
            // Permission has not been granted yet. Request it directly.
            mShownRationalDialogOnRequest = false;
            fragment.requestPermissions(new String[]{permission.getPermission()},
                    permission.getRequestCode());
        }
    }

    private static void showRationaleDialog(Context context, @StringRes int posTextRes,
                                            MaterialDialog.SingleButtonCallback callback) {
        new MaterialDialog.Builder(context).content(R.string.ms_permission_rationale)
                .positiveText(posTextRes)
                .negativeText(R.string.ms_label_later)
                .onPositive(callback)
                .show();
    }

    /**
     * Check that all given permissions have been granted by verifying that each entry in the
     * given array is of the value {@link PackageManager#PERMISSION_GRANTED}.
     */
    public static boolean verifyPermissions(final Activity activity, String[] permissions, int[] grantResults) {
        // At least one result must be checked.
        if (grantResults.length < 1) {
            return false;
        }

        // Verify that each required permission has been granted, otherwise return false.
        for (int i = 0; i < permissions.length; i++) {
            int result = grantResults[i];
            if (result != PackageManager.PERMISSION_GRANTED) {
                boolean shouldShowRationale = ActivityCompat.shouldShowRequestPermissionRationale(activity,
                        permissions[i]);
                if (!shouldShowRationale) {
                    // it means user has selected "never ask again"
                    if (mShownRationalDialogOnRequest) {
                        return false;
                    }
                    mShownRationalDialogOnRequest = false;
                    showRationaleDialog(activity, R.string.ms_label_settings, new MaterialDialog.SingleButtonCallback() {
                        @Override
                        public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
                            Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
                            intent.setData(uri);
                            activity.startActivityForResult(intent, REQUEST_PERMISSION_SETTING);
                        }
                    });
                }
                return false;
            }
        }
        return true;
    }


    // Dangerous permission enum
    public enum Permissions {

        // CAMERA
        CAMERA(Manifest.permission.CAMERA, 1),

        // AUDIO
        AUDIO_RECORD(Manifest.permission.RECORD_AUDIO, 2),

        // STORAGE
        READ_STORAGE(Manifest.permission.READ_EXTERNAL_STORAGE, 3),
        WRITE_STORAGE(Manifest.permission.WRITE_EXTERNAL_STORAGE, 4),;

        private final String permission;
        private final int requestCode;

        Permissions(String value, int requestCode) {
            this.permission = value;
            this.requestCode = requestCode;
        }

        public String getPermission() {
            return permission.toString();
        }

        public int getRequestCode() {
            return requestCode;
        }
    }
}
