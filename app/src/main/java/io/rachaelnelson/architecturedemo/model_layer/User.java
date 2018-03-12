package io.rachaelnelson.architecturedemo.model_layer;
import com.google.gson.annotations.SerializedName;

import io.rachaelnelson.networklibrary.NetworkUtils;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class User extends RealmObject implements NetworkUtils.Expirable {
    private String login;
    private @PrimaryKey int id;
    private @SerializedName("avatar_url") String avatarUrl;
    private long expiration;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public long getExpiration() {
        return expiration;
    }

    @Override
    public void setExpiration(long expiration) {
        this.expiration = expiration;
    }
}
