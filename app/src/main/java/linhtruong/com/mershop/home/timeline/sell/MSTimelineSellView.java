package linhtruong.com.mershop.home.timeline.sell;

import android.content.Context;
import android.view.View;
import butterknife.ButterKnife;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;

/**
 * @TODO under development
 *
 * @author linhtruong
 * @date 10/8/18 - 16:49.
 * @organization VED
 */
public class MSTimelineSellView extends BaseActionView {
    public MSTimelineSellView(Context context) {
        super(context);
    }

    @Override
    protected View createContentView(Context context) {
        View contentView = inflate(context, R.layout.com_linhtruong_mershop_camera_activity, null);

        return contentView;
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }
}
