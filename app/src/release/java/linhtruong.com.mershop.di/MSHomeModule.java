package linhtruong.com.mershop.di;

import dagger.Module;
import dagger.Provides;
import linhtruong.com.mershop.network.NetworkManager;
import linhtruong.com.mershop.network.http.service.MSTimelineService;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/3/18 - 13:27.
 * @organization VED
 */
@Module
public class MSHomeModule {
    private NetworkManager mNetworkManager;

    public MSHomeModule(NetworkManager networkManager) {
        mNetworkManager = networkManager;
    }

    @Provides
    @MockMode("prod")
    MSTimelineService getTimelineService() {
        return mNetworkManager.httpservice(MSTimelineService.DESCRIPTOR);
    }
}
