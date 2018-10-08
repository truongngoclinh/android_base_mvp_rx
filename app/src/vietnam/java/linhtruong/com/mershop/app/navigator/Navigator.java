package linhtruong.com.mershop.app.navigator;

import linhtruong.com.mershop.home.MSHomeActivity;
import linhtruong.com.mershop.home.timeline.detail.MSTimelineCategoryDetailActivity;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/3/18 - 11:26.
 * @organization VED
 */
public class Navigator {
    public static void onNavigationHomeActivity(ActivityScreenSwitcher screenSwitcher) {
        screenSwitcher.openAndFinish(new MSHomeActivity.Screen());
    }

    public static void onNavigationTimelineDetailActivity(ActivityScreenSwitcher screenSwitcher, String item) {
        screenSwitcher.open(new MSTimelineCategoryDetailActivity.Screen(item));
    }
}
