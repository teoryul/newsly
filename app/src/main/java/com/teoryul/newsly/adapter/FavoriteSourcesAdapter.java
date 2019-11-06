package com.teoryul.newsly.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.viewholder.FavoriteTopicSourceHolder;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.SourcePersist;

public class FavoriteSourcesAdapter extends BaseRecyclerViewAdapter<SourcePersist> {

    public FavoriteSourcesAdapter(OnRecyclerViewItemClickListener<SourcePersist> onRecyclerViewItemClickListener) {
        super(onRecyclerViewItemClickListener);
    }

    @NonNull
    @Override
    public FavoriteTopicSourceHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new FavoriteTopicSourceHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_topic_source, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final SourcePersist source = data.get(position);
        ((FavoriteTopicSourceHolder) holder).getTxtFavoriteTopicSource()
                .setText(TextUtils.concat(source.getName(), " (", source.getLanguage().toUpperCase(), ")").toString());
        setOnRecyclerViewItemClickListener(holder, source);
    }
}
