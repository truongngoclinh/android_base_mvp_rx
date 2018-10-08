package linhtruong.com.mershop.home.timeline.sell;

import android.support.annotation.NonNull;
import linhtruong.com.mershop.base.BaseActivity;
import linhtruong.com.mershop.base.BasePresenter;

/**
 * @TODO under development
 *
 * @author linhtruong
 * @date 10/8/18 - 16:49.
 * @organization VED
 */
public class MSTimelineSellPresenter extends BasePresenter<MSTimelineSellView> {
    private final MSTimelineSellActivity mActivity;

    public MSTimelineSellPresenter(MSTimelineSellActivity activity) {
        mActivity = activity;
    }

    @NonNull
    @Override
    public BaseActivity getActivity() {
        return mActivity;
    }

    @NonNull
    @Override
    public BasePresenter getPresenter() {
        return this;
    }
}
