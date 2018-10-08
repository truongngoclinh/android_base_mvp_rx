package linhtruong.com.mershop.base.pager;

import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

/**
 * PageAdapter custom support {@link MSPage}
 *
 * @author linhtruong
 * @date 10/6/18 - 13:44.
 * @organization VED
 */
public abstract class MSPagerAdapter extends PagerAdapter {
    protected MSPagerPage mCurPrimaryItem;

    public abstract MSPagerPage getPage(int position);

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        MSPagerPage page = getPage(position);

        /**
         * Set the hidden status to be true, then OnCreate() won't call the firstLoad() immediately
         */
        page.onHidden();

        page.create(null);
        View content = page.getContentView();
        if (content != null) {
            container.addView(content);
        }
        return page;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return ((MSPagerPage) object).getContentView() == view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        MSPagerPage page = (MSPagerPage) object;
        container.removeView(page.getContentView());
        page.destroy();
    }

    @Override
    public void setPrimaryItem(ViewGroup container, int position, Object object) {
        super.setPrimaryItem(container, position, object);
        MSPagerPage page = (MSPagerPage) object;
        if (mCurPrimaryItem == page) {
            return;
        }

        MSPagerPage old = mCurPrimaryItem;
        mCurPrimaryItem = page;
        onCurrentPageChanged(old, page);
    }

    protected void onCurrentPageChanged(MSPagerPage old, MSPagerPage current) {
        if (old != null) {
            old.onHidden();
        }
        if (current != null) {
            current.onShown();
        }
    }
}
