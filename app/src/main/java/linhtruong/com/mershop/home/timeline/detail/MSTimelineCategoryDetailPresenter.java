package linhtruong.com.mershop.home.timeline.detail;

import android.graphics.Bitmap;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.helpers.MSUIHelper;
import linhtruong.com.commons.utils.MSCameraUtil;
import linhtruong.com.commons.utils.MSThemeUtil;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.base.BaseActivity;
import linhtruong.com.mershop.base.BasePresenter;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;

/**
 * Product detail
 *
 * @TODO: support like, comment, uireactive update timeline page, db update
 *
 * @author linhtruong
 * @date 10/8/18 - 01:12.
 * @organization VED
 */
public class MSTimelineCategoryDetailPresenter extends BasePresenter<MSTimelineCategoryDetailView> {
    private MSTimelineCategoryDetailActivity mActivity;
    private TimelineCategoryResponse.CategoryItem mData;

    public MSTimelineCategoryDetailPresenter(MSTimelineCategoryDetailActivity activity, TimelineCategoryResponse.CategoryItem data) {
        mData = data;
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

    @Override
    protected void onLoad() {
        super.onLoad();

        int size = (MSUIHelper.SCREEN_WIDTH_PX - MSUIHelper.dp1 * 16);
        ViewGroup.LayoutParams layoutParams = getView().mPhoto.getLayoutParams();
        layoutParams.width = size;
        layoutParams.height = size;
        getView().mPhoto.setLayoutParams(layoutParams);

        getView().mLikeCount.setText(mData.numLikes + "");
        getView().mCommentCount.setText(mData.numComments + "");
        getView().mPrice.setText(mData.price + "");

        if (mData.photoUrl.contains("http")) {
            Picasso.get()
                    .load(mData.photoUrl)
                    .resize(size, size)
                    .onlyScaleDown()
                    .placeholder(MSThemeUtil.resolveRes(mActivity, R.attr.msColorBgImagePlaceholder))
                    .into(getView().mPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            getView().mDetailLayout.setVisibility(View.VISIBLE);
                            getView().mBadge.setVisibility(mData.status.equalsIgnoreCase(MSConstValue.TIMELINE_STATUS.SOLD_OUT) ? View.VISIBLE : View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            getView().mBadge.setVisibility(View.GONE);
                        }
                    });
        } else {
            Bitmap bitmap = MSCameraUtil.optimizeBitmap(MSConstValue.BITMAP.SAMPLE_SIZE, mData.photoUrl);
            getView().mPhoto.setImageBitmap(bitmap);
        }
    }
}
