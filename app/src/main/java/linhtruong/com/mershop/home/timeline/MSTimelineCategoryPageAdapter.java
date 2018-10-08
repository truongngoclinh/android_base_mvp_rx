package linhtruong.com.mershop.home.timeline;

import android.content.Context;
import android.content.res.Configuration;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import linhtruong.com.commons.MSConstValue;
import linhtruong.com.commons.helpers.MSUIHelper;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.commons.utils.MSThemeUtil;
import linhtruong.com.commons.widgets.MSImageView;
import linhtruong.com.commons.widgets.MSTextView;
import linhtruong.com.mershop.R;
import linhtruong.com.mershop.base.BaseRecyclerAdapter;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;

/**
 * Timeline page item list adapter
 *
 * @author linhtruong
 * @date 10/7/18 - 15:00.
 * @organization VED
 */
public class MSTimelineCategoryPageAdapter extends BaseRecyclerAdapter<TimelineCategoryResponse.CategoryItem, MSTimelineCategoryPageAdapter.MSTimelineCategoryViewHolder> {
    private final static int TYPE_CONTENT = 0x01;
    private final int mOrientation;
    private MSTimelineCategoryPage.ICategoryItemInteractor mListener;

    public MSTimelineCategoryPageAdapter(MSTimelineCategoryPage.ICategoryItemInteractor listener, int orientation) {
        mListener = listener;
        mOrientation = orientation;
    }

    @Override
    public int getItemViewType(int position) {
        return TYPE_CONTENT;
    }

    @Override
    public MSTimelineCategoryViewHolder createHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_CONTENT:
                return MSTimelineCategoryViewHolder.create(mListener, parent.getContext(), mOrientation);
            default:
                return null;
        }
    }

    static class MSTimelineCategoryViewHolder extends BaseRecyclerAdapter.ViewHolder<TimelineCategoryResponse.CategoryItem> {
        @BindView(R.id.ms_product_photo)
        MSImageView mPhoto;
        @BindView(R.id.ms_like_count)
        MSTextView mLikeCount;
        @BindView(R.id.ms_comment_count)
        MSTextView mCommentCount;
        @BindView(R.id.ms_price)
        MSTextView mPrice;
        @BindView(R.id.ms_badge_sold_out)
        MSImageView mBadge;
        @BindView(R.id.ms_detail_layout)
        RelativeLayout mDetailLayout;

        private final int mOrientation;

        private MSTimelineCategoryViewHolder(MSTimelineCategoryPage.ICategoryItemInteractor listener, View itemView, int orientation) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mOrientation = orientation;

            if (listener != null) {
                itemView.setOnClickListener(v -> {
                    if (v.getTag() instanceof TimelineCategoryResponse.CategoryItem) {
                        listener.onItemClicked((TimelineCategoryResponse.CategoryItem) v.getTag());
                    }
                });
            }
        }

        static MSTimelineCategoryViewHolder create(MSTimelineCategoryPage.ICategoryItemInteractor listener, Context context, int orientation) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.com_linhtruong_mershop_home_timeline_category_item, null, false);
            return new MSTimelineCategoryViewHolder(listener, itemView, orientation);
        }

        @Override
        public void bindData(TimelineCategoryResponse.CategoryItem data) {
            itemView.setTag(data);
            int size = 0;
            if (mOrientation == Configuration.ORIENTATION_PORTRAIT) {
                size = (MSUIHelper.SCREEN_WIDTH_PX - MSUIHelper.dp10 * 2) / 2;
            } else {
                size = (MSUIHelper.SCREEN_HEIGHT_PX - MSUIHelper.dp10 * 2) / 2;
            }
            ViewGroup.LayoutParams layoutParams = mPhoto.getLayoutParams();
            layoutParams.width = size;
            layoutParams.height = size;
            mPhoto.setLayoutParams(layoutParams);

            mLikeCount.setText(data.numLikes + "");
            mCommentCount.setText(data.numComments + "");
            mPrice.setText(data.price + "");

            Picasso.get()
                    .load(data.photoUrl)
                    .resize(size, size)
                    .onlyScaleDown()
                    .placeholder(MSThemeUtil.resolveRes(itemView.getContext(), R.attr.msColorBgImagePlaceholder))
                    .into(mPhoto, new Callback() {
                        @Override
                        public void onSuccess() {
                            mDetailLayout.setVisibility(View.VISIBLE);
                            mBadge.setVisibility(data.status.equalsIgnoreCase(MSConstValue.TIMELINE_STATUS.SOLD_OUT) ? View.VISIBLE : View.GONE);
                        }

                        @Override
                        public void onError(Exception e) {
                            mDetailLayout.setVisibility(View.GONE);
                            mBadge.setVisibility(View.GONE);
                        }
                    });
        }
    }
}
