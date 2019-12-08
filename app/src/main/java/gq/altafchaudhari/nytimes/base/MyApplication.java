package gq.altafchaudhari.nytimes.base;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import gq.altafchaudhari.nytimes.utils.Utils;
import okhttp3.OkHttpClient;

public class MyApplication extends MultiDexApplication {

    public static String SECTION = "all-section", PERIOD = "1";

    @Override
    public void onCreate() {
        super.onCreate();
        MultiDex.install(this);

        if (Utils.isDebug()) {
            Stetho.initializeWithDefaults(this);
            new OkHttpClient.Builder()
                    .addNetworkInterceptor(new StethoInterceptor())
                    .build();
        }
    }
}
