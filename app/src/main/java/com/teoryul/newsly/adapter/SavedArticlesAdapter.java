package com.teoryul.newsly.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.utils.PicassoImageLoader;
import com.teoryul.newsly.adapter.viewholder.NewsFeedArticleHolder;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.ArticlePersist;

public class SavedArticlesAdapter extends BaseRecyclerViewAdapter<ArticlePersist> {

    public SavedArticlesAdapter(OnRecyclerViewItemClickListener<ArticlePersist> onRecyclerViewItemClickListener) {
        super(onRecyclerViewItemClickListener);
    }

    @NonNull
    @Override
    public NewsFeedArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NewsFeedArticleHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_favorite_news_article, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final ArticlePersist article = data.get(position);

        ((NewsFeedArticleHolder) holder).getTxtArticleSource().setText(article.getSourceName());
        ((NewsFeedArticleHolder) holder).getTxtArticleTitle().setText(article.getNewsTitle());
        PicassoImageLoader.setViewHolderImageView(((NewsFeedArticleHolder) holder).getImgView(), article.getImgUrl());

        setOnRecyclerViewItemClickListener(holder, article);
    }
}
