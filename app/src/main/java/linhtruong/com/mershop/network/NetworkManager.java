package linhtruong.com.mershop.network;

import android.content.Context;
import com.google.gson.Gson;
import linhtruong.com.mershop.network.http.HttpFactory;
import linhtruong.com.network.http.HttpManager;
import linhtruong.com.network.http.HttpServiceDescriptor;
import okhttp3.OkHttpClient;

/**
 * Manage http, tcp, udp service
 *
 * @author linhtruong
 * @date 10/1/18 - 15:55.
 * @organization VED
 */
public class NetworkManager {
    private final Context mContext;
    private final HttpManager mHttp;

    public NetworkManager(Context context, OkHttpClient client, Gson gson) {
        mContext = context;
        mHttp = new HttpManager(new HttpFactory(client, gson));
    }

    public <T> T httpservice(HttpServiceDescriptor<T> descriptor) {
        return mHttp.getService(descriptor);
    }
}
