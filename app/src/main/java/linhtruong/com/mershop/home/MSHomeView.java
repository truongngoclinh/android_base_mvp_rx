package linhtruong.com.mershop.home;

import android.content.Context;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.Toolbar;
import android.view.View;
import butterknife.BindView;
import butterknife.ButterKnife;
import linhtruong.com.commons.widgets.MSViewPager;
import linhtruong.com.commons.widgets.base.BaseActionView;
import linhtruong.com.mershop.R;

/**
 * Main view
 *
 * @author linhtruong
 * @date 10/2/18 - 11:41.
 * @organization VED
 */
public class MSHomeView extends BaseActionView {
    @BindView(R.id.ms_viewpager)
    MSViewPager mViewPager;

    @BindView(R.id.ms_tab_layout)
    TabLayout mTabLayout;

    @BindView(R.id.ms_toolbar)
    Toolbar mToolbar;

    @BindView(R.id.ms_home_root)
    CoordinatorLayout mRoot;

    public MSHomeView(Context context) {
        super(context);
    }

    @Override
    protected View createContentView(Context context) {
        return inflate(context, R.layout.com_linhtruong_mershop_home_acitivity, null);
    }

    @Override
    protected void bindView() {
        ButterKnife.bind(this);
    }
}
