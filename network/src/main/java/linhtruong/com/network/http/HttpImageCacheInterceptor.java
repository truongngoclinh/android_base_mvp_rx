package linhtruong.com.network.http;

import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Cache support
 *
 * @author linhtruong
 * @date 10/1/18 - 16:46.
 * @organization VED
 */
public class HttpImageCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (response.header("Content-Type", "").startsWith("image")) {
            // image-only, cache as long as possible
            return response.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, max-age=" + Integer.MAX_VALUE)
                    .build();
        }

        return response;
    }
}
