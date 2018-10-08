package linhtruong.com.mershop.di;

import dagger.Module;
import dagger.Provides;
import linhtruong.com.mershop.base.task.TaskResource;
import linhtruong.com.mershop.network.NetworkManager;
import linhtruong.com.mershop.network.http.service.MSTimelineService;
import linhtruong.com.mershop.network.http.service.mock.MSMockTimelineService;
import linhtruong.com.network.http.HttpServiceDescriptor;
import retrofit2.Retrofit;

import javax.inject.Inject;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/3/18 - 13:27.
 * @organization VED
 */
@Module
public class MSHomeModule extends MSMockModule {

    private NetworkManager mNetworkManager;

    public MSHomeModule(NetworkManager networkManager) {
        super();
        mNetworkManager = networkManager;
    }

    @Provides
    @MockMode("mock")
    MSTimelineService getMockTimelineService() {
        return new MSMockTimelineService(mMockRetrofit.create(MSTimelineService.class));
    }

    @Provides
    @MockMode("prod")
    MSTimelineService getTimelineService() {
        return mNetworkManager.httpservice(MSTimelineService.DESCRIPTOR);
    }
}
