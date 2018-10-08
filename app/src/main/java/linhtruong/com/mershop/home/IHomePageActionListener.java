package linhtruong.com.mershop.home;

/**
 * Action for home page
 *
 * @author linhtruong
 * @date 10/6/18 - 14:36.
 * @organization VED
 */
public interface IHomePageActionListener {
    void setTabBarVisible(boolean visible, String tabKey, String title);
    void setHomeMenuVisible(boolean visible, String tabKey);
    void setFloatingButtonVisible(boolean visible, String tabKey, String type);
    void updateTheme(final int newTheme);
}
