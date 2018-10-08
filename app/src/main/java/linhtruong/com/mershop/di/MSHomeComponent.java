package linhtruong.com.mershop.di;

import dagger.Component;
import linhtruong.com.mershop.app.AppComponent;
import linhtruong.com.mershop.base.BaseComponent;
import linhtruong.com.mershop.home.MSHomeActivity;
import linhtruong.com.mershop.home.MSHomePresenter;
import linhtruong.com.mershop.home.timeline.MSTimelineCategoryPage;
import linhtruong.com.mershop.home.timeline.MSTimelinePage;
import linhtruong.com.mershop.task.TaskGetTimeLineConfig;

/**
 * Mock HomeComponent, only in debug source
 *
 * @author linhtruong
 * @date 10/3/18 - 13:26.
 * @organization VED
 */
@MSHomeScope
@Component(
        dependencies = {AppComponent.class},
        modules = {MSHomeModule.class}
)
public interface MSHomeComponent extends BaseComponent {
    final class Initializer {
        public static MSHomeComponent init(AppComponent appComponent) {
            return DaggerMSHomeComponent.builder()
                    .appComponent(appComponent)
                    .mSHomeModule(new MSHomeModule(appComponent.getNetworkManager()))
                    .build();
        }
    }

    void inject(MSHomeActivity activity);
    void inject(MSHomePresenter presenter);
    void inject(MSTimelinePage page);
    void inject(MSTimelineCategoryPage page);

    void inject(TaskGetTimeLineConfig task);
}
