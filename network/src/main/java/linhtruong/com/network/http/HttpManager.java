package linhtruong.com.network.http;

import android.text.TextUtils;
import retrofit2.Retrofit;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Manage http service cache
 *
 * @author linhtruong
 * @date 10/1/18 - 15:36.
 * @organization VED
 */
public class HttpManager {
    private final RetrofitFactory mFactory;
    private final Map<String, Retrofit> mRetroCache;
    private final Map<HttpServiceDescriptor, Object> mServiceCache;

    public HttpManager(RetrofitFactory factory) {
        mFactory = factory;
        mRetroCache = new ConcurrentHashMap<>();
        mServiceCache = new ConcurrentHashMap<>();
    }

    @SuppressWarnings("unchecked")
    public <T> T getService(HttpServiceDescriptor<T> descriptor) {
        if (descriptor == null) {
            throw new IllegalArgumentException("Descriptor cannot be null");
        }

        Object service = mServiceCache.get(descriptor);
        if (service == null) {
            synchronized (this) {
                service = mServiceCache.get(descriptor);
                if (service == null) {
                    service = createService(descriptor);
                    mServiceCache.put(descriptor, service);
                }
            }
        }

        return (T) service;
    }

    private <T> T createService(HttpServiceDescriptor<T> descriptor) {
        String url = descriptor.getBaseUrl();
        if (TextUtils.isEmpty(url)) {
            throw new IllegalArgumentException("Undefined base url in descriptor");
        }

        Retrofit retrofit = mRetroCache.get(url);
        if (retrofit == null) {
            retrofit = mFactory.createRetrofit(url);
            mRetroCache.put(url, retrofit);
        }

        return retrofit.create(descriptor.getServiceClass());
    }
}
