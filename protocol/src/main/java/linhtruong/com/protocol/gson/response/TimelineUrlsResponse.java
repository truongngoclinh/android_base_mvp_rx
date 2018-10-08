package linhtruong.com.protocol.gson.response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/3/18 - 14:34.
 * @organization VED
 */
public class TimelineUrlsResponse extends CommonResponse {
    public List<Category> data;

    public class Category {
        @SerializedName("name")
        public String name;

        @SerializedName("data")
        public String url;

        public Category(String name, String url) {
            this.name = name;
            this.url = url;
        }
    }

    public void createCategory(String name, String url) {
       data.add(new Category(name, url));
    }
}

