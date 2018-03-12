package io.rachaelnelson.architecturedemo.home;

import io.rachaelnelson.architecturedemo.model_layer.UserModel;
import io.rachaelnelson.networklibrary.CachePolicy;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class HomePresenter {

    private UserModel model;
    private Disposable disposable;
    private HomeView view;

    public HomePresenter(HomeView view, UserModel model) {
        this.view = view;
        this.model = model;
    }

    public void fetchData() {
        disposable = model.getUsers(CachePolicy.CACHE_ELSE_NETWORK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> view.showData());
    }

    public void refreshData() {
        disposable = model.getUsers(CachePolicy.NETWORK_ONLY)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> view.showData());
    }

    public void pageData(int offset, int limit) {
        disposable = model.pageUsers(offset, limit, CachePolicy.CACHE_ELSE_NETWORK)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(users -> view.showData());
    }

    public void destroy() {
        if (disposable != null) {
            disposable.dispose();
        }
    }
}
