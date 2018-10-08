package linhtruong.com.protocol.gson.response;

import com.google.gson.annotations.SerializedName;

import java.math.BigInteger;
import java.util.List;
import java.util.Random;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/7/18 - 14:19.
 * @organization VED
 */
public class TimelineCategoryResponse extends CommonResponse {
    public List<CategoryItem> data;

    public static class CategoryItem {
        @SerializedName("id")
        public String id;

        @SerializedName("name")
        public String name;

        @SerializedName("status")
        public String status;

        @SerializedName("num_likes")
        public int numLikes;

        @SerializedName("num_comments")
        public int numComments;

        @SerializedName("price")
        public float price;

        @SerializedName("photo")
        public String photoUrl;

        public CategoryItem() {
            this.id = "";
            this.name = "";
            this.status = "";
            numLikes = 0;
            numComments = 0;
            price = 0;
            photoUrl = "";
        }

        public CategoryItem(String id, String name, String status, int numLikes, int numComments, float price, String photoUrl) {
           this.id = id;
           this.name = name;
           this.status = status;
           this.numLikes = numLikes;
           this.numComments = numComments;
           this.price = price;
           this.photoUrl = photoUrl;
        }
    }

    public void create(String id, String name, String status, int numLikes, int numComments, int price, String photoUrl) {
        data.add(new CategoryItem(id, name, status, numLikes, numComments, price, photoUrl));
    }
}
