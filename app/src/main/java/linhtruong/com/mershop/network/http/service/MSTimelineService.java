package linhtruong.com.mershop.network.http.service;

import io.reactivex.Observable;
import linhtruong.com.network.http.HttpServiceDescriptor;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;
import linhtruong.com.protocol.gson.response.TimelineUrlsResponse;
import retrofit2.http.GET;
import retrofit2.http.Url;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/2/18 - 22:54.
 * @organization VED
 */
public interface MSTimelineService {
    HttpServiceDescriptor<MSTimelineService> DESCRIPTOR = new HttpServiceDescriptor<MSTimelineService>() {
        @Override
        public String getBaseUrl() {
            return "https://s3-ap-northeast-1.amazonaws.com";
        }

        @Override
        public Class<MSTimelineService> getServiceClass() {
            return MSTimelineService.class;
        }
    };

    @GET("/m-et/Android/json/master.json")
    Observable<TimelineUrlsResponse> getTimelineConfig();

    @GET
    Observable<TimelineCategoryResponse> getCategoryData(@Url String url);
}
