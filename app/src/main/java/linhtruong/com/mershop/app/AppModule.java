package linhtruong.com.mershop.app;

import android.content.Context;
import com.google.gson.Gson;
import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;
import dagger.Module;
import dagger.Provides;
import linhtruong.com.commons.managers.MSFileManager;
import linhtruong.com.commons.picasso.MSLruCache;
import linhtruong.com.commons.utils.MSLogUtil;
import linhtruong.com.mershop.BuildConfig;
import linhtruong.com.mershop.app.navigator.ActivityScreenSwitcher;
import linhtruong.com.mershop.base.task.TaskManager;
import linhtruong.com.mershop.base.task.TaskResource;
import linhtruong.com.mershop.network.NetworkManager;
import linhtruong.com.mershop.network.http.GsonFactory;
import linhtruong.com.mershop.network.http.OkHttpFactory;

/**
 * Main application module
 *
 * @author linhtruong
 * @date 10/1/18 - 14:09.
 * @organization VED
 */
@Module
public class AppModule {
    private final Context mContext;
    private final Gson mGson;
    private final MSLruCache mMemCache;
    private final MSFileManager mFileManager;

    public AppModule(Context context) {
        mContext = context;
        mGson = GsonFactory.build();
        mMemCache = new MSLruCache(context);
        mFileManager = new MSFileManager(context);
    }

    @Provides
    @AppScope
    TaskManager getTaskManager() {
        return new TaskManager();
    }

    @Provides
    @AppScope
    TaskResource getTaskResource() {
        return new TaskResource();
    }

    @Provides
    @AppScope
    NetworkManager getNetworkManager() {
        return new NetworkManager(mContext, OkHttpFactory.buildCommon(), mGson);
    }

    @Provides
    @AppScope
    ActivityScreenSwitcher getActivityScreenSwitcher() {
        return new ActivityScreenSwitcher();
    }

    @Provides
    @AppScope
    MSLruCache getMemCache() {
        return mMemCache;
    }

    @Provides
    @AppScope
    MSFileManager getFileManager() {
        return mFileManager;
    }

    @Provides
    @AppScope
    Picasso getPicasso() {
        Picasso.Builder builder = new Picasso.Builder(mContext);
        builder.downloader(new OkHttp3Downloader(OkHttpFactory.buildImage(mFileManager.getCacheDir())));
        builder.memoryCache(mMemCache);

        if (BuildConfig.DEBUG) {
            builder.listener((picasso, uri, exception) -> {
                MSLogUtil.e("picasso failed to load %s with exception below", uri);
                MSLogUtil.e(exception);
            });
        }

        Picasso picasso = builder.build();
        Picasso.setSingletonInstance(picasso);
        return picasso;
    }
}
