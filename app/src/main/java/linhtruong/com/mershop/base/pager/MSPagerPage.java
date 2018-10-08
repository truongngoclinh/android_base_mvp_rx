package linhtruong.com.mershop.base.pager;

/**
 * Pager page
 *
 * @author linhtruong
 * @date 10/6/18 - 13:44.
 * @organization VED
 */
public abstract class MSPagerPage extends MSPage {
    private boolean mFirstShown = true;
    private boolean mIsHidden = false;

    public MSPagerPage(IPageContainer container) {
        super(container);
    }

    public void onShown() {
        log("visibility update: shown");
        mIsHidden = false;

        if (mFirstShown) {
            mFirstShown = false;
            onFirstShown();
        }
    }

    protected void onFirstShown() {
    }

    public void onHidden() {
        log("visibility update: hidden");
        mIsHidden = true;
    }

    public boolean isHidden() {
        return mIsHidden;
    }
}
