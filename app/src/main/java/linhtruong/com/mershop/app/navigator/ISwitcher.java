package linhtruong.com.mershop.app.navigator;

import android.content.Intent;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/2/18 - 15:28.
 * @organization VED
 */
public interface ISwitcher {
    void open(IScreen screen);
    void goBack();
    void openAndFinish(IScreen screen);
    void closeWithResult(int resultCode, Intent result);
    // void startForResult(IScreen screen);
}
