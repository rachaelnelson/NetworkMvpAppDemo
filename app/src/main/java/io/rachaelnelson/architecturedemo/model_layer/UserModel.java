package io.rachaelnelson.architecturedemo.model_layer;

import java.util.List;
import java.util.Map;

import io.rachaelnelson.architecturedemo.App;
import io.rachaelnelson.networklibrary.CachePolicy;
import io.rachaelnelson.networklibrary.NetworkApiClient;
import io.rachaelnelson.networklibrary.NetworkUtils;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

public class UserModel {

    private UserService service;
    private RealmConfiguration realmConfig;

    public UserModel() {
        NetworkApiClient client = NetworkApiClient.getInstance();
        service = client.getRetrofit().create(UserService.class);
        realmConfig = App.getRealmConfig();
    }

    public Observable<List<User>> getUsers(CachePolicy cachePolicy) {

        return NetworkUtils.resolve(new NetworkUtils.NetworkRepository<List<User>>() {
            @Override
            public Single<List<User>> fetch() {
                return service.getUsers();
            }
        }, new NetworkUtils.CacheRepository<List<User>>() {
            @Override
            public Maybe<List<User>> read() {
                try (Realm realm = Realm.getInstance(realmConfig)) {
                    //// TODO: 3/11/18 Add Expiration
                    RealmResults<User> results = realm.where(User.class).findAll();
                    if (!results.isEmpty()) {
                        return Maybe.just(realm.copyFromRealm(results));
                    } else {
                        return Maybe.empty();
                    }
                }
            }

            @Override
            public void write(List<User> data) {
                RealmConfiguration realmConfig = App.getRealmConfig();
                //// TODO: 3/11/18 set expiration on realm objects
                try (Realm realm = Realm.getInstance(realmConfig)) {
                    realm.executeTransaction(r -> r.copyToRealmOrUpdate(data));
                }

            }
        }, cachePolicy);
    }

    public Observable<List<User>> pageUsers(int offset, int limit, CachePolicy cachePolicy) {
        return NetworkUtils.resolve(new NetworkUtils.NetworkRepository<List<User>>() {
            @Override
            public Single<List<User>> fetch() {
                return null;
            }
        }, new NetworkUtils.CacheRepository<List<User>>() {
            @Override
            public Maybe<List<User>> read() {
                try (Realm realm = Realm.getInstance(realmConfig)) {
                    RealmResults<User> results = realm.where(User.class).findAll();
                    if (!results.isEmpty() && results.size() >= offset + limit) {
                        return Maybe.just(realm.copyFromRealm(results));
                    } else {
                        return Maybe.empty();
                    }
                }
            }

            @Override
            public void write(List<User> data) {
                RealmConfiguration realmConfig = App.getRealmConfig();
                try (Realm realm = Realm.getInstance(realmConfig)) {
                    realm.executeTransaction(r -> r.copyToRealmOrUpdate(data));
                }
            }
        }, cachePolicy);
    }

    private interface UserService {
        @GET("users")
        Single<List<User>> getUsers();

        @GET("users")
        Single<List<User>> pageUsers(@QueryMap Map<String, String> params);
    }
}
