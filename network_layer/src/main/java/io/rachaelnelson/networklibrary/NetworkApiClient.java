package io.rachaelnelson.networklibrary;

import android.content.Context;

import io.reactivex.Scheduler;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkApiClient {

    private static NetworkApiClient networkClient;
    private Retrofit retrofit;
    private BaseServerEnvironment serverEnvironment;

    /**
     * A library for managing network api calls & responses, and for switching server environment
     * used in making those calls.
     */
    private NetworkApiClient() {}

    public static void init(Config config) {

        networkClient = new NetworkApiClient();

        networkClient.serverEnvironment = config.getServerEnvironment();
        String baseUrl = networkClient.serverEnvironment.getEnvironment(config.getContext());

        networkClient.retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .client(config.okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(config.scheduler))
                .build();


//        OkHttpClient httpClient = authHttpClient.newBuilder()
//                //Add refresh interceptor for logging
//                // & authenticator to httpclient for logging and refresh expired auth access tokens using cached refresh token
//                .build();


        // Configure UserAccountManager object for the network here to keep track of refresh & access tokens for
        // making authenticated calls. Set up with retrofit's authenticator for auto-refresh of access token upon expiration, then
        // (will allow retrofit to retry authenticated calls attempted before receiving response indicating refresh token expired/refresh of access token required)
    }

    public static NetworkApiClient getInstance() {
        return networkClient;
    }

    public static final class Config {
        private final Context context;
        private OkHttpClient okHttpClient;
        private Scheduler scheduler = Schedulers.io();
        private BaseServerEnvironment serverEnvironment;


        //pass app configuration params here
        public Config(Context context) {
            this.context = context;
        }

        public Context getContext() {
            return context;
        }

        public Config setOkHttpClient(OkHttpClient okHttpClient) {
            this.okHttpClient = okHttpClient;
            return this;
        }

        public Config setServerEnvironment(BaseServerEnvironment environment) {
            this.serverEnvironment = environment;
            return this;
        }

        public BaseServerEnvironment getServerEnvironment() {
            return serverEnvironment;
        }
    }

    public Retrofit getRetrofit() {
        return networkClient.getRetrofit();
    }
}
