package linhtruong.com.mershop.network.http;

import com.google.gson.Gson;
import linhtruong.com.mershop.app.AppSchedulers;
import linhtruong.com.network.http.RetrofitFactory;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Retrofit
 *
 * @author linhtruong
 * @date 10/1/18 - 16:00.
 * @organization VED
 */
public class HttpFactory implements RetrofitFactory {
    private volatile GsonConverterFactory mGsonFactory;
    private volatile RxJava2CallAdapterFactory mRxFactory;

    private final OkHttpClient mClient;
    private final Gson mGson;

    public HttpFactory(OkHttpClient client, Gson gson) {
        mClient = client;
        mGson = gson;
    }

    @Override
    public Retrofit createRetrofit(String baseUrl) {
        if (mGsonFactory == null) {
            synchronized (this) {
                if (mGsonFactory == null) {
                    mGsonFactory = createGsonFactory();
                }
            }
        }

        if (mRxFactory == null) {
            synchronized (this) {
                if (mRxFactory == null) {
                    mRxFactory = createRxFactory();
                }
            }
        }

        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(mClient)
                .addConverterFactory(mGsonFactory)
                .addCallAdapterFactory(mRxFactory)
                .build();
    }

    private GsonConverterFactory createGsonFactory() {
        return GsonConverterFactory.create(mGson);
    }

    private RxJava2CallAdapterFactory createRxFactory() {
        return RxJava2CallAdapterFactory.createWithScheduler(AppSchedulers.IO);
    }
}
