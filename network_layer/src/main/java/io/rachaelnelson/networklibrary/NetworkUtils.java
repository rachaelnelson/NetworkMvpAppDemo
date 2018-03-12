package io.rachaelnelson.networklibrary;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;

public class NetworkUtils {

    private NetworkUtils() {
        throw new AssertionError("No instances allowed!");
    }

    public interface Expirable {
        long getExpiration();
        void setExpiration(long expiration);
    }

    public interface NetworkRepository<T> {
        Single<T> fetch();
    }

    public interface CacheRepository<T> {
        Maybe<T> read();
        void write(T data);
    }

    public static long getExpirationTime(int hours) {
        return System.currentTimeMillis() + TimeUnit.HOURS.toMillis(hours);
    }

    public static <T> Observable<T> resolve(NetworkRepository<T> networkRepository,
                                            CacheRepository<T> cacheRepository,
                                            CachePolicy cachePolicy) {
        switch (cachePolicy) {
            case NETWORK_ONLY:
                return networkRepository.fetch()
                        .doOnSuccess(cacheRepository::write)
                        .toObservable();
            case CACHE_ELSE_NETWORK:
                return Maybe.concat(Maybe.defer(cacheRepository::read), networkRepository.fetch()
                        .doOnSuccess(cacheRepository::write).toMaybe())
                        .firstOrError()
                        .toObservable();
            default:
                return Observable.empty();
        }
    }

}
