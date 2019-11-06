package com.teoryul.newsly.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.viewholder.FavoriteTopicSourceHolder;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.TopicPersist;

public class FavoriteTopicsAdapter extends BaseRecyclerViewAdapter<TopicPersist> {

    public FavoriteTopicsAdapter(OnRecyclerViewItemClickListener<TopicPersist> onRecyclerViewItemClickListener) {
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
        final TopicPersist topic = data.get(position);
        ((FavoriteTopicSourceHolder) holder).getTxtFavoriteTopicSource().setText(topic.getTopicName());
        setOnRecyclerViewItemClickListener(holder, topic);
    }
}
