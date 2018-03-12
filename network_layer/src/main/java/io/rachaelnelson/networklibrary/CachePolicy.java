package io.rachaelnelson.networklibrary;

public enum CachePolicy {
    NETWORK_ONLY,       // fetch freshest data from network (store first non-empty response in cache in app)
    CACHE_ELSE_NETWORK, // fetch cache, if empty, fetch network (store first non-empty response in cache in app)
}
