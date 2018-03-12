package io.rachaelnelson.architecturedemo;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;
import io.rachaelnelson.architecturedemo.network_options.ServerEnvironment;
import io.rachaelnelson.coreui.BaseActivity;
import io.rachaelnelson.coreui.BaseApp;
import io.rachaelnelson.networklibrary.BaseServerEnvironment;
import io.realm.Realm;
import io.realm.RealmConfiguration;

public class App extends BaseApp {

    private static RealmConfiguration realmConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        //Add Crash logging with Crashlytics
        Fabric.with(this, new Crashlytics());


        setupRealm();
    }

    @Override
    public BaseApp getApplication() {
        return this;
    }

    @Override
    public Class<? extends BaseActivity> getLaunchActivityClass() {
        return Activity.class;
    }

    @Override
    public BaseServerEnvironment getAppNetworkApiServerEnvironment() {
        return new ServerEnvironment();
    }


    private void setupRealm() {
        Realm.init(this);

        //// TODO: 3/11/18 Add Encryption key here, wipe database if encryption key is missing

        realmConfig = new RealmConfiguration
                .Builder()
                .name("app.realm")
                .build();

    }

    public static RealmConfiguration getRealmConfig() {
        return realmConfig;
    }
}
