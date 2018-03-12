package io.rachaelnelson.architecturedemo.home;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.Controller;

import io.rachaelnelson.architecturedemo.R;
import io.rachaelnelson.architecturedemo.model_layer.UserModel;

public class HomeControllerView extends Controller implements HomeView {

    private ProgressBar progress;
    private SwipeRefreshLayout refreshLayout;
    private RecyclerView recycler;

    private HomePresenter presenter;

    @NonNull
    @Override
    protected View onCreateView(@NonNull LayoutInflater inflater, @NonNull ViewGroup container) {
        View view = inflater.inflate(R.layout.controller_home, container, false);

        // Cache the view inflated by this controller (RETAIN_DETACH), as it is not only expensive to recreate the home
        // tab, but will be revisited frequently (you won't want to reload each time the user impatiently switches tabs
        // on the BottomNavigationView menu).
        // By allowing the view to remain cached on these essential (pretty much guaranteed) revisited tabs during a user "session",
        // we can also keep the network interaction calls running in the background and ready for the users return to this primary/root screen.
        setRetainViewMode(RetainViewMode.RETAIN_DETACH);

        progress = view.findViewById(R.id.progressBar);

        refreshLayout = view.findViewById(R.id.refreshContainer);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                presenter.refreshData();
            }
        });

        recycler = view.findViewById(R.id.recycler);

        presenter = new HomePresenter(this, new UserModel());
        presenter.fetchData();

        return view;
    }


    // onDestroyView is of important note in the chain of lifecycle callbacks. This callback allows us to properly our destroy
    // our presenters, models, and associated background thread processes that are likely to be running on background threads to load data into view
    // when using the MVP pattern. We want to "clean up" any resources created to perform operations on this view upon destruction of the view.
    @Override
    protected void onDestroyView(@NonNull View view) {
        super.onDestroyView(view);
        presenter.destroy();
    }


    //region HomeView
    @Override
    public void showData() {
        progress.setVisibility(View.GONE);
        //recyclerView.setAdapter(adapter)
    }

    @Override
    public void showRefresh() {
        refreshLayout.setRefreshing(false);
        //recyclerView.setAdapter(adapter)
    }

    @Override
    public void showPagedData() {
        //adapter.addItems();
    }
    //endregion HomeView
}
