package linhtruong.com.protocol.gson.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import linhtruong.com.protocol.gson.response.TimelineUrlsResponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/4/18 - 13:44.
 * @oganization VED
 */
public class TimelineDeserializer extends BaseDeserializer<TimelineUrlsResponse> {
    @Override
    public TimelineUrlsResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TimelineUrlsResponse response = new TimelineUrlsResponse();
        response.data = new Gson().fromJson(json, new TypeToken<List<TimelineUrlsResponse.Category>>(){}.getType());

        return response;
    }
}
