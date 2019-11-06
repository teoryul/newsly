package com.teoryul.newsly.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.teoryul.newsly.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedArticleHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_news_feed_article_source_item)
    TextView txtArticleSource;
    @BindView(R.id.img_news_feed_article_item)
    ImageView imgView;
    @BindView(R.id.txt_news_feed_article_item)
    TextView txtArticleTitle;

    public NewsFeedArticleHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTxtArticleSource() {
        return txtArticleSource;
    }

    public ImageView getImgView() {
        return imgView;
    }

    public TextView getTxtArticleTitle() {
        return txtArticleTitle;
    }
}
