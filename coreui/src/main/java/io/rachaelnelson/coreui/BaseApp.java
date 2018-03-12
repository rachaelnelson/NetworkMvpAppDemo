package io.rachaelnelson.coreui;

import android.app.Application;

import com.squareup.picasso.Picasso;

import java.util.concurrent.TimeUnit;

import io.rachaelnelson.networklibrary.BaseServerEnvironment;
import io.rachaelnelson.networklibrary.NetworkApiClient;
import okhttp3.OkHttpClient;


public abstract class BaseApp extends Application {

    private static BaseApp application;

    public enum BuildMode {
        DEBUG_FULL, DEBUG_NORMAL, PRODUCTION
    }

    public abstract BaseApp getApplication();
    public abstract Class<? extends BaseActivity> getLaunchActivityClass();
    public abstract BaseServerEnvironment getAppNetworkApiServerEnvironment();

    @Override
    public void onCreate() {
        super.onCreate();

        application = getApplication();


        OkHttpClient httpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .build();

        NetworkApiClient.Config config = new NetworkApiClient.Config(this)
                .setOkHttpClient(httpClient)
                .setServerEnvironment(getAppNetworkApiServerEnvironment());

        NetworkApiClient.init(config);

        Picasso.setSingletonInstance(new Picasso.Builder(this)
                .loggingEnabled(BuildConfig.DEBUG)
                .indicatorsEnabled(BuildConfig.DEBUG)
                .build());
    }

    public static BaseApp getInstance() {
        return application;
    }

}
