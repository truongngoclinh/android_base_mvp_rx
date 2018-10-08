package linhtruong.com.mershop.home;

import linhtruong.com.mershop.base.pager.MSPagerAdapter;
import linhtruong.com.mershop.base.pager.MSPagerPage;

import java.util.ArrayList;
import java.util.List;

/**
 * Main adapter with fixed page
 *
 * @author linhtruong
 * @date 10/6/18 - 15:32.
 * @organization VED
 */
public class MSHomeAdapter extends MSPagerAdapter {

    private final List<MSHomeBasePage> mPages = new ArrayList<>();
    private final List<String> mTitles = new ArrayList<>();

    public void reset() {
        mPages.clear();
        mTitles.clear();
    }

    public void addPage(MSHomeBasePage page, String title) {
        mPages.add(page);
        mTitles.add(title);
    }

    @Override
    public MSPagerPage getPage(int position) {
        return mPages.get(position);
    }

    @Override
    public int getCount() {
        return mPages.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mTitles.get(position);
    }

    public String getTabKey(int position) {
        if (position < 0 || position >= mPages.size()) return "";

        MSHomeBasePage page = mPages.get(position);
        return page.getTabKey();
    }

    @Override
    public int getItemPosition(Object object) {
        // make sure we recreate all the pages when notifyDataSetChanged() is called
        return POSITION_NONE;
    }
}
