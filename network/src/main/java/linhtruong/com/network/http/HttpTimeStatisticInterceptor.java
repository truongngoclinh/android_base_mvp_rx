package linhtruong.com.network.http;

import linhtruong.com.network.NetworkStatisticsReport;
import okhttp3.Interceptor;
import okhttp3.Response;

import java.io.IOException;

/**
 * Statistic support
 *
 * @author linhtruong
 * @date 10/1/18 - 16:35.
 * @organization VED
 */
public class HttpTimeStatisticInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        long start = System.currentTimeMillis();
        Response response = chain.proceed(chain.request());
        long elapse = System.currentTimeMillis() - start;
        NetworkStatisticsReport.httpStat(elapse);

        return response;
    }
}
