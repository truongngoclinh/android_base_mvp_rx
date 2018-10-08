package linhtruong.com.commons.widgets;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import linhtruong.com.commons.R;

/**
 * App progress dialog
 *
 * @author linhtruong
 * @date 10/2/18 - 21:56.
 * @organization VED
 */
public class MSProgressDialog extends Dialog {
    private final Handler mUI;
    private final Animation mLoadingAnimation;
    private final TextView mTvMessage;
    private final ImageView mIvLoading;
    private OnBackPressListener mOnBackPressedListener;
    private boolean mDetached;

    public MSProgressDialog(Context context) {
        super(context, R.style.DialogTransparent);
        mUI = new Handler(Looper.getMainLooper());
        mLoadingAnimation = AnimationUtils.loadAnimation(context, R.anim.com_linhtruong_mershop_progress_anim);

        setContentView(R.layout.com_linhtruong_mershop_widgets_progress_dialog);

        mTvMessage = findViewById(R.id.ms_loading_title);
        mTvMessage.setVisibility(View.GONE);

        mIvLoading = findViewById(R.id.ms_loading_image);

        ViewCompat.setLayerType(mIvLoading, View.LAYER_TYPE_SOFTWARE, null);
    }

    public void close() {
        try {
            this.dismiss();
        } catch (Exception e) {
            // fix based on http://stackoverflow.com/a/5102572/827110
        }
    }

    public void setOnBackPressedListener(OnBackPressListener listener) {
        mOnBackPressedListener = listener;
    }

    @Override
    protected void onStart() {
        mIvLoading.startAnimation(mLoadingAnimation);
        super.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mIvLoading.clearAnimation();
    }

    public void setOperationCancelable(boolean cancel) {
        setCanceledOnTouchOutside(cancel);
        setCancelable(cancel);
    }

    @Override
    public void onBackPressed() {
        if (mOnBackPressedListener != null && mOnBackPressedListener.onPressBack()) {
            return;
        }
        super.onBackPressed();
    }

    public void setMessage(String msg) {
        if (TextUtils.isEmpty(msg)) {
            mTvMessage.setVisibility(View.GONE);
        } else {
            mTvMessage.setText(msg);
            mTvMessage.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void show() {
        mUI.removeCallbacks(DELAY_SHOW);
        mUI.postDelayed(DELAY_SHOW, 300);
    }

    @Override
    public void dismiss() {
        mUI.removeCallbacks(DELAY_SHOW);
        if (isShowing() && !mDetached) {
            super.dismiss();
        }
    }

    @Override
    public void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        mDetached = true;
        mUI.removeCallbacksAndMessages(null);
    }

    private final Runnable DELAY_SHOW = new Runnable() {
        @Override
        public void run() {
            Context context = getContext();
            if (context instanceof Activity && ((Activity) context).isFinishing()) {
                return;
            }

            MSProgressDialog.super.show();
            mDetached = false;
        }
    };

    public interface OnBackPressListener {
        boolean onPressBack();
    }
}
