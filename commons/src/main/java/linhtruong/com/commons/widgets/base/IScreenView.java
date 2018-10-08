package linhtruong.com.commons.widgets.base;

/**
 *
 * Callback to perform view-based customization
 *
 * We shall NOT put the logic here and all application logic shall go to the
 * presenter instead of view
 *
 * @author linhtruong
 * @date 10/2/18 - 11:45.
 * @organization VED
 */
public interface IScreenView {
    void onShowView();
    void onHideView();
    void onDestroy();
}
