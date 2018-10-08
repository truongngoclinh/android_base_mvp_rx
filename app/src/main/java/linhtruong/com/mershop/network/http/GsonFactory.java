package linhtruong.com.mershop.network.http;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import linhtruong.com.protocol.gson.deserializers.TimelineCategoryDeserializer;
import linhtruong.com.protocol.gson.deserializers.TimelineDeserializer;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;
import linhtruong.com.protocol.gson.response.TimelineUrlsResponse;

/**
 * Gson
 *
 * @author linhtruong
 * @date 10/1/18 - 16:54.
 * @organization VED
 */
public class GsonFactory {
    public static Gson build() {
        return new GsonBuilder()
                .registerTypeAdapter(TimelineUrlsResponse.class, new TimelineDeserializer())
                .registerTypeAdapter(TimelineCategoryResponse.class, new TimelineCategoryDeserializer())
                .create();
    }

    private GsonFactory() {}
}
