package holmusk.com.holmuskchallenge.Resources.HttpClient;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by thearith on 28/4/16.
 */

public interface HolmuskService {

    @GET("food/search")
    Call<ResponseBody> getListofFood(@Query("q") String query);

    @GET("food/search?q=pizza")
    Call<ResponseBody> test();
}