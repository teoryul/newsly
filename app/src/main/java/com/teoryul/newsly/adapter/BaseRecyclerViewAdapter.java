package com.teoryul.newsly.adapter;

import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;

import com.teoryul.newsly.adapter.diffutil.DiffUtilCallback;
import com.teoryul.newsly.adapter.diffutil.RecyclerViewItemMarker;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerViewAdapter<T extends RecyclerViewItemMarker>
        extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    protected List<T> data;
    /**
     * See {@link #setOnRecyclerViewItemClickListener(RecyclerView.ViewHolder, RecyclerViewItemMarker)}
     * for information about the use of this flag.
     */
    protected boolean canClick;
    protected final OnRecyclerViewItemClickListener<T> onRecyclerViewItemClickListener;

    public BaseRecyclerViewAdapter(OnRecyclerViewItemClickListener<T> onRecyclerViewItemClickListener) {
        this.data = new ArrayList<>();
        this.canClick = true;
        this.onRecyclerViewItemClickListener = onRecyclerViewItemClickListener;
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    /**
     * Handles the click event for a recycler view item.
     * Uses a global boolean flag to prevent users from mistakenly clicking on an item multiple times.
     * It gets set to false immediately after a click is registered.
     * It gets set back to true either when there is no internet connection and the item cannot be open,
     * or when the user returns to the screen with the recycler view.
     *
     * @param holder
     * @param item
     */
    protected void setOnRecyclerViewItemClickListener(final RecyclerView.ViewHolder holder, final T item) {
        holder.itemView.setOnClickListener(v -> {
            if (canClick) {
                canClick = false;
                onRecyclerViewItemClickListener.onRecyclerViewItemClick(item);
            }
        });
    }

    public void setData(List<T> newData) {
        DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(new DiffUtilCallback<>(data, newData));
        diffResult.dispatchUpdatesTo(this);
        if (!data.isEmpty()) {
            data.clear();
        }
        data.addAll(newData);
    }

    public void setCanClick(boolean canClick) {
        this.canClick = canClick;
    }
}
