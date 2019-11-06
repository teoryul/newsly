package com.teoryul.newsly.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.item.NewsFeedMultiItem;
import com.teoryul.newsly.adapter.utils.PicassoImageLoader;
import com.teoryul.newsly.adapter.viewholder.NewsFeedArticleHolder;
import com.teoryul.newsly.adapter.viewholder.NewsFeedEndHolder;
import com.teoryul.newsly.adapter.viewholder.NewsFeedViewHolderTypes;
import com.teoryul.newsly.callback.OnRecyclerViewBottomReachedListener;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.utils.AppConstants;

public class NewsFeedArticleAdapter extends BaseRecyclerViewAdapter<NewsFeedMultiItem> {

    private final OnRecyclerViewBottomReachedListener onRecyclerViewBottomReachedListener;

    public NewsFeedArticleAdapter(OnRecyclerViewItemClickListener<NewsFeedMultiItem> onRecyclerViewItemClickListener,
                                  OnRecyclerViewBottomReachedListener onRecyclerViewBottomReachedListener) {
        super(onRecyclerViewItemClickListener);
        this.onRecyclerViewBottomReachedListener = onRecyclerViewBottomReachedListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {

            case NewsFeedViewHolderTypes.ARTICLE:
                return new NewsFeedArticleHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_news_feed_article, parent, false));

            case NewsFeedViewHolderTypes.END:
                return new NewsFeedEndHolder(LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.item_news_feed_end, parent, false));
        }

        throw new IllegalArgumentException(AppConstants.THROWABLE_MSG_INVALID_VIEW_HOLDER_VIEW_TYPE);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        switch (data.get(position).getViewType()) {

            case NewsFeedViewHolderTypes.ARTICLE:
                bindNewsArticleHolder(holder, position);
                break;

            case NewsFeedViewHolderTypes.END:
                bindNewsFeedEndHolder(holder, position);
                break;
        }
    }

    @Override
    public int getItemViewType(int position) {
        return data.get(position).getViewType();
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private void bindNewsArticleHolder(final RecyclerView.ViewHolder holder, final int position) {
        final ArticlePersist article = (ArticlePersist) data.get(position);

        ((NewsFeedArticleHolder) holder).getTxtArticleSource().setText(article.getSourceName());
        PicassoImageLoader.setViewHolderImageView(((NewsFeedArticleHolder) holder).getImgView(), article.getImgUrl());
        ((NewsFeedArticleHolder) holder).getTxtArticleTitle().setText(article.getNewsTitle());

        setOnRecyclerViewItemClickListener(holder, article);
        callOnBottomReachedListener(position);
    }

    private void bindNewsFeedEndHolder(final RecyclerView.ViewHolder holder, final int position) {
        ((NewsFeedEndHolder) holder).getTxtNewsFeedEnd().setText(App.getInstance().getString(R.string.txt_news_feed_end));
    }

    private void callOnBottomReachedListener(final int position) {
        if (position == getItemCount() - AppConstants.RECYCLER_VIEW_ON_BOTTOM_SCROLL_POSITION_OFFSET) {
            onRecyclerViewBottomReachedListener.onBottomReached();
        }
    }
}
