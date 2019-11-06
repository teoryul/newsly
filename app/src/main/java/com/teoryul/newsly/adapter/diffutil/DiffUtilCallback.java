package com.teoryul.newsly.adapter.diffutil;

import android.support.v7.util.DiffUtil;

import java.util.List;

public class DiffUtilCallback<T extends RecyclerViewItemMarker> extends DiffUtil.Callback {

    private List<T> oldList;
    private List<T> newList;

    public DiffUtilCallback(List<T> oldList, List<T> newList) {
        this.oldList = oldList;
        this.newList = newList;
    }

    @Override
    public int getOldListSize() {
        return oldList != null ? oldList.size() : 0;
    }

    @Override
    public int getNewListSize() {
        return newList != null ? newList.size() : 0;
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
        return newList.get(newItemPosition).getUniqueId()
                == oldList.get(oldItemPosition).getUniqueId();
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
        int result = Integer.compare(
                newList.get(newItemPosition).getUniqueId(),
                oldList.get(oldItemPosition).getUniqueId());
        return result == 0;
    }
}
