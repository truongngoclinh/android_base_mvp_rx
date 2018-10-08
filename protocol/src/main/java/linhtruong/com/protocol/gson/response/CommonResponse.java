package linhtruong.com.protocol.gson.response;

import com.google.gson.annotations.SerializedName;

/**
 * CLASS DESCRIPTION
 *
 * @author linhtruong
 * @date 10/7/18 - 14:18.
 * @organization VED
 */
public class CommonResponse {

    @SerializedName("error_code")
    String errorCode;

    @SerializedName("error_message")
    String errorMessage;
}
