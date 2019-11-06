package com.teoryul.newsly.adapter.viewholder;

import com.teoryul.newsly.adapter.diffutil.RecyclerViewItemMarker;

/**
 * Interface for handling multiple adapter view types in a single recycler view.
 */
public interface NewsFeedViewHolderTypes extends RecyclerViewItemMarker {

    int ARTICLE = 0;
    int END = 1;

    int getViewType();
}
