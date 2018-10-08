package linhtruong.com.mershop.network.http;

import linhtruong.com.commons.managers.MSFileManager;
import linhtruong.com.network.BuildConfig;
import linhtruong.com.network.NetworkConst;
import linhtruong.com.network.http.HttpImageCacheInterceptor;
import linhtruong.com.network.http.HttpTimeStatisticInterceptor;
import linhtruong.com.network.http.HttpUserAgentInterceptor;
import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;

import java.io.File;
import java.util.concurrent.TimeUnit;

/**
 * Http
 *
 * @author linhtruong
 * @date 10/1/18 - 16:32.
 * @organization VED
 */
public class OkHttpFactory {
    public static OkHttpClient buildCommon() {
        return new OkHttpClient.Builder()
                .addInterceptor(new HttpUserAgentInterceptor())
                .addInterceptor(new HttpLoggingInterceptor().setLevel(BuildConfig.DEBUG
                        ? HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE))
                .addNetworkInterceptor(new HttpTimeStatisticInterceptor())
                .connectTimeout(NetworkConst.HTTP_CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(NetworkConst.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(NetworkConst.HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    public static OkHttpClient buildImage(File cacheDir) {
        return new OkHttpClient.Builder()
                .cache(new Cache(cacheDir, MSFileManager.CACHE_DIR_SIZE))
                .addNetworkInterceptor(new HttpImageCacheInterceptor())
                .connectTimeout(NetworkConst.HTTP_CONN_TIMEOUT, TimeUnit.MILLISECONDS)
                .readTimeout(NetworkConst.HTTP_READ_TIMEOUT, TimeUnit.MILLISECONDS)
                .writeTimeout(NetworkConst.HTTP_WRITE_TIMEOUT, TimeUnit.MILLISECONDS)
                .build();
    }

    private OkHttpFactory() {
    }
}
