package linhtruong.com.mershop.app;

import com.squareup.leakcanary.LeakCanary;
import linhtruong.com.commons.helpers.MSUIHelper;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.mershop.BuildConfig;
import linhtruong.com.mershop.app.preferences.AppConfigPref;
import linhtruong.com.mershop.base.BaseApplication;
import com.crashlytics.android.Crashlytics;
import io.fabric.sdk.android.Fabric;

/**
 * Main application instance
 *
 * @author linhtruong
 * @date 10/1/18 - 15:17.
 * @organization VED
 */
public class App extends BaseApplication {
    @Override
    public void onCreate() {
        super.onCreate();

        initLeakCanary();
        initLogger();
        initDependencies();
        initUtils();
        initCrashReport();
        initCommon();
    }

    private void initDependencies() {
        AppComponent component = AppComponent.Initializer.init(new AppModule(this));
        component.inject(component.getTaskManager());
        component.inject(component.getTaskResource());

        setComponent(component);
    }

    private void initLogger() {
        if (!BuildConfig.DEBUG) {
            MSLogUtil.CONFIG_NO_LOG = true;
        }
    }

    private void initCrashReport() {
        if (!BuildConfig.DEBUG) {
            Fabric.with(this, new Crashlytics());
        }
    }

    private void initLeakCanary() {
        if (BuildConfig.DEBUG) {
            if (LeakCanary.isInAnalyzerProcess(this)) {
                // This process is dedicated to LeakCanary for heap analysis.
                // You should not init your app in this process.
                return;
            }

            LeakCanary.install(this);
        }
    }

    private void initCommon() {
        AppConfigPref.init(this);
    }

    private void initUtils() {
        MSUIHelper.init(this);
    }
}
