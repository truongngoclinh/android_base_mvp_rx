package linhtruong.com.mershop.di;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.mock.MockRetrofit;
import retrofit2.mock.NetworkBehavior;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/4/18 - 11:45.
 * @organization VED
 */
public class MSMockModule {
    protected final MockRetrofit mMockRetrofit;
    private final NetworkBehavior mBehavior;

    public MSMockModule() {
        Retrofit retrofit = new Retrofit.Builder()
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("http://mock").build();
        mBehavior = NetworkBehavior.create();
        mMockRetrofit = new MockRetrofit.Builder(retrofit).networkBehavior(mBehavior).build();
    }
}
