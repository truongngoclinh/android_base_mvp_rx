package linhtruong.com.network.http;

import retrofit2.Retrofit;

/**
 * Retrofit factory
 *
 * @author linhtruong
 * @date 10/1/18 - 15:49.
 * @organization VED
 */
public interface RetrofitFactory {
    Retrofit createRetrofit(String baseUrl);
}
