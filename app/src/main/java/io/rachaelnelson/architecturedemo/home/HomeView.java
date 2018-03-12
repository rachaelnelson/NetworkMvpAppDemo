package io.rachaelnelson.architecturedemo.home;

import java.util.List;

import io.rachaelnelson.architecturedemo.model_layer.User;

public interface HomeView {
    void showData(List<User> users);
    void showRefresh(List<User> users);
    void showPagedData(List<User> users);

}
