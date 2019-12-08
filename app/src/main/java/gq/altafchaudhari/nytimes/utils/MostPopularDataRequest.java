package gq.altafchaudhari.nytimes.utils;

import java.util.HashMap;
import java.util.Map;

import gq.altafchaudhari.nytimes.BuildConfig;
import gq.altafchaudhari.nytimes.base.MyApplication;
import gq.altafchaudhari.nytimes.models.Response;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public final class MostPopularDataRequest {

    private static final String TAG = MostPopularDataRequest.class.getSimpleName();
    private ApiEndpoints apiEndpoints;

    public MostPopularDataRequest() {
        this.apiEndpoints = RestClient.createService(ApiEndpoints.class);
    }

    public Observable<Response> getMostPopularArticles() {

        Map<String, String> data = new HashMap<>();
        data.put("api-key", BuildConfig.API_KEY);

      /*  return apiEndpoints.getMostPopularArticles(data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/
        return apiEndpoints.getMostPopularArticles(MyApplication.PERIOD,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
       /* return apiEndpoints.getMostPopularArticles(MyApplication.SECTION,MyApplication.PERIOD,data)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());*/
    }

}