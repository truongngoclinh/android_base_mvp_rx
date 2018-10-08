package linhtruong.com.mershop.home.timeline.detail;

import android.content.Context;
import android.view.View;
import android.widget.RelativeLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import linhtruong.com.commons.widgets.MSImageView;
import linhtruong.com.commons.widgets.MSTextView;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;

/**
 * Product detail
 *
 * @author linhtruong
 * @date 10/8/18 - 01:12.
 * @organization VED
 */
public class MSTimelineCategoryDetailView extends BaseActionView {
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

    public MSTimelineCategoryDetailView(Context context) {
        super(context);
    }

    @Override
    protected View createContentView(Context context) {
        View contentView = inflate(context, R.layout.com_linhtruong_mershop_timeline_detail_activity, null);
        ButterKnife.bind(this, contentView);
        return contentView;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }

}
