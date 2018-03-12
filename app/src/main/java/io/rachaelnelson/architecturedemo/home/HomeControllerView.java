package io.rachaelnelson.architecturedemo.home;

import android.support.annotation.NonNull;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.bluelinelabs.conductor.Controller;

import java.util.ArrayList;
import java.util.List;

import io.rachaelnelson.architecturedemo.R;
import io.rachaelnelson.architecturedemo.model_layer.User;
import io.rachaelnelson.architecturedemo.model_layer.UserModel;
import io.rachaelnelson.coreui.InfiniteScrollRecyclerView;

public class HomeControllerView extends Controller implements HomeView, HomeAdapter.UserClickListener, InfiniteScrollRecyclerView.PageEventListener {

    private ProgressBar progress;
    private SwipeRefreshLayout refreshLayout;
    private InfiniteScrollRecyclerView recycler;
    private HomeAdapter adapter;

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
        adapter = new HomeAdapter(new ArrayList<>(), this);
        recycler.setAdapter(adapter);
        recycler.setPageEventListener(this);

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
    public void showData(List<User> users) {
        progress.setVisibility(View.GONE);
        refreshLayout.setVisibility(View.VISIBLE);
        adapter = new HomeAdapter(users, this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void showRefresh(List<User> users) {
        refreshLayout.setRefreshing(false);
        adapter = new HomeAdapter(users, this);
        recycler.setAdapter(adapter);
    }

    @Override
    public void showPagedData(List<User> users) {
        adapter.appendItems(users);
    }

    @Override
    public void onUserClick(User user) {
        //// TODO: 3/12/18 do something here
    }

    @Override
    public void onEndOfListReached() {
//        Toast.makeText(getContext(), "That's all the transactions we have for this card", Toast.LENGTH_SHORT).show();

    }

    @Override
    public void pageItems(int currentListItemCount) {
        presenter.pageData(currentListItemCount, 100);
    }
    //endregion HomeView
}
