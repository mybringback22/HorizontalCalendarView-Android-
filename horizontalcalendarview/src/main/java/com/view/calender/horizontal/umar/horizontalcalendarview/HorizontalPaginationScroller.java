package com.view.calender.horizontal.umar.horizontalcalendarview;


import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by UManzoor on 11/28/2017.
 */

public abstract class HorizontalPaginationScroller extends RecyclerView.OnScrollListener  {
    LinearLayoutManager layoutManager;

    public HorizontalPaginationScroller(LinearLayoutManager layoutManager) {
        this.layoutManager = layoutManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        super.onScrolled(recyclerView, dx, dy);

        int visibleItemCount = layoutManager.getChildCount();
        int totalItemCount = layoutManager.getItemCount();
        int firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition();

        if (!isLoading()) {
            if ((visibleItemCount + firstVisibleItemPosition) >= totalItemCount
                    && firstVisibleItemPosition >= 0) {
                loadMoreItems();
            }
            if(layoutManager.findFirstCompletelyVisibleItemPosition()==0){
                loadMoreItemsOnLeft();
            }

        }
    }

    protected abstract void loadMoreItems();

    protected abstract void loadMoreItemsOnLeft();


    public abstract boolean isLoading();
}
