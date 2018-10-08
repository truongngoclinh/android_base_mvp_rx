package linhtruong.com.protocol.gson.deserializers;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;
import linhtruong.com.protocol.gson.response.TimelineCategoryResponse;

import java.lang.reflect.Type;
import java.util.List;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/4/18 - 13:44.
 * @oganization VED
 */
public class TimelineCategoryDeserializer extends BaseDeserializer<TimelineCategoryResponse> {
    @Override
    public TimelineCategoryResponse deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        TimelineCategoryResponse response = new TimelineCategoryResponse();
        response.data = new Gson().fromJson(json, new TypeToken<List<TimelineCategoryResponse.CategoryItem>>(){}.getType());

        return response;
    }
}
