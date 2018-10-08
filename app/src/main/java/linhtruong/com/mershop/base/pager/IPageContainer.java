package linhtruong.com.mershop.base.pager;

import android.support.annotation.NonNull;
import linhtruong.com.mershop.base.BaseActivity;
import linhtruong.com.mershop.base.BasePresenter;

/**
 * Page interactor
 *
 * @author linhtruong
 * @date 10/6/18 - 14:08.
 * @organization VED
 */
public interface IPageContainer {
    void registerPage(MSPage page);

    void deregisterPage(MSPage page);

    @NonNull
    BaseActivity getActivity();

    @NonNull
    BasePresenter getPresenter();
}
