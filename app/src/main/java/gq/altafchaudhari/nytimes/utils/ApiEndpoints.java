package gq.altafchaudhari.nytimes.utils;

import java.util.Map;

import gq.altafchaudhari.nytimes.models.Response;
import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

public interface ApiEndpoints {
    //@GET("mostpopular/v2/mostviewed/all-sections/7.json")
    //Observable<Response> getMostPopularArticles(@QueryMap Map<String, String> options);

   /* @GET("mostpopular/v2/mostviewed/{section}/{period}.json")
    Observable<Response> getMostPopularArticles(@Path("section") String section,@Path("period") String period, @QueryMap Map<String, String> options);
*/
    @GET("mostpopular/v2/viewed/{period}.json")
    Observable<Response> getMostPopularArticles(@Path("period") String period, @QueryMap Map<String, String> options);
}