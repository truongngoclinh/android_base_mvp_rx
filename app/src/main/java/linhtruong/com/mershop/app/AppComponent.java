package linhtruong.com.mershop.app;

import dagger.Component;
import linhtruong.com.mershop.app.navigator.ActivityScreenSwitcher;
import linhtruong.com.mershop.base.BaseComponent;
import linhtruong.com.mershop.home.timeline.detail.MSTimelineCategoryDetailActivity;
import linhtruong.com.mershop.home.timeline.detail.MSTimelineCategoryDetailPresenter;
import linhtruong.com.mershop.network.NetworkManager;
import linhtruong.com.mershop.splash.MSSplashActivity;
import linhtruong.com.mershop.splash.MSSplashPresenter;
import linhtruong.com.mershop.base.task.TaskManager;
import linhtruong.com.mershop.base.task.TaskResource;

/**
 * Main application component, mostly included @AppScope resources
 *
 * @author linhtruong
 * @date 10/1/18 - 14:09.
 * @organization VED
 */
@AppScope
@Component(
       modules = {AppModule.class}
)
public interface AppComponent extends BaseComponent {
    final class Initializer {
        public static AppComponent init(AppModule appModule) {
            return DaggerAppComponent.builder().appModule(appModule).build();
        }
    }

    void inject(App app);
    void inject(TaskManager taskManager);
    void inject(TaskResource taskResource);

    void inject(MSSplashActivity activity);
    void inject(MSSplashPresenter presenter);

    void inject(MSTimelineCategoryDetailActivity activity);
    void inject(MSTimelineCategoryDetailPresenter presenter);

    ActivityScreenSwitcher getActivitySwitcher();
    TaskResource getTaskResource();
    TaskManager getTaskManager();
    NetworkManager getNetworkManager();
}
