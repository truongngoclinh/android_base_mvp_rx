package linhtruong.com.mershop.network.http.service.mock;

import io.reactivex.Observable;
import linhtruong.com.mershop.network.http.service.MSTimelineService;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;
import linhtruong.com.protocol.gson.response.TimelineUrlsResponse;
import retrofit2.mock.BehaviorDelegate;

import java.util.ArrayList;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/2/18 - 22:54.
 * @organization VED
 */
public class MSMockTimelineService implements MSTimelineService {
    private final BehaviorDelegate<MSTimelineService> mDelegate;

    public MSMockTimelineService(BehaviorDelegate<MSTimelineService> delegate) {
        mDelegate = delegate;
    }

    @Override
    public Observable<TimelineUrlsResponse> getTimelineConfig() {
        return mDelegate.returningResponse(createConfig()).getTimelineConfig();
    }

    @Override
    public Observable<TimelineCategoryResponse> getCategoryData(String url) {
        return mDelegate.returningResponse(createData()).getCategoryData("");
    }

    private TimelineUrlsResponse createConfig() {
        TimelineUrlsResponse response = new TimelineUrlsResponse();
        response.data = new ArrayList<>();
        response.createCategory("all", "url");
        response.createCategory("men", "url");
        response.createCategory("women", "url");
        response.createCategory("ct1", "url");
        response.createCategory("ct2", "url");
        response.createCategory("ct3", "url");

        return response;
    }

    private TimelineCategoryResponse createData() {
        TimelineCategoryResponse response = new TimelineCategoryResponse();
        response.data = new ArrayList<>();
        response.create("mmen1", "men1", "on_sale", 91, 59, 51, "http://dummyimage.com/400x400/000/fff?text=men1");
        response.create("mmen2", "men2", "on_sale", 81, 89, 2, "http://dummyimage.com/400x400/000/fff?text=men2");
        response.create("mmen3", "men3", "sold_out", 17, 58, 38, "http://dummyimage.com/400x400/000/fff?text=men3");

        return response;
    }
}

