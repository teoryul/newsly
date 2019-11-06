package com.teoryul.newsly.callback;

import com.teoryul.newsly.adapter.diffutil.RecyclerViewItemMarker;

public interface OnRecyclerViewItemClickListener<T extends RecyclerViewItemMarker> {

    void onRecyclerViewItemClick(T item);
}
