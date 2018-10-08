package linhtruong.com.network.http;

import android.text.TextUtils;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

import java.io.IOException;

/**
 * User-Agent support
 *
 * @author linhtruong
 * @date 10/1/18 - 15:39.
 * @organization VED
 */
public class HttpUserAgentInterceptor implements Interceptor {
    private final String USER_AGENT = "User-Agent";

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        if (TextUtils.isEmpty(request.header(USER_AGENT))) {
            request = request.newBuilder().header(USER_AGENT, /*MSDeviceUtil.generateUA("")*/"").build();
        }

        return chain.proceed(request);
    }
}
