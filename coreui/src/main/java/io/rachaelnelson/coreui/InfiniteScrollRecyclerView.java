package io.rachaelnelson.coreui;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class InfiniteScrollRecyclerView extends RecyclerView {

    private boolean isPaging;
    private boolean endOfHistoryReached;
    private PageEventListener listener;

    public interface PageEventListener {
        void onEndOfListReached();
        void pageItems(int currentListItemCount);
    }

    public InfiniteScrollRecyclerView(Context context) {
        super(context);
    }

    public InfiniteScrollRecyclerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public InfiniteScrollRecyclerView(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

    }

    public void setPageEventListener(PageEventListener listener) {
        this.listener = listener;

        addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                double itemCount = getAdapter().getItemCount();
                double lastVisibleItemPosition = ((LinearLayoutManager)getLayoutManager()).findLastVisibleItemPosition();

                if (!isPaging && !endOfHistoryReached && (lastVisibleItemPosition / itemCount > .8)) {
                    isPaging = true;
                    listener.pageItems(getAdapter().getItemCount());
                } else if (endOfHistoryReached) {
                    listener.onEndOfListReached();
                }

            }
        });
    }

    // Call after adapter data has been paged to re-allow isPaging
    public void setAllowPaging(boolean allowPaging) {
        this.isPaging = !allowPaging;
    }

    public void setEndOfHistoryReached(boolean endOfHistoryReached) {
        this.endOfHistoryReached = endOfHistoryReached;
        this.isPaging = false;
    }
}