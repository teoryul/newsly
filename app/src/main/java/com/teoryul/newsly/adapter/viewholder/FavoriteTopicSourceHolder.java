package com.teoryul.newsly.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.teoryul.newsly.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FavoriteTopicSourceHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_favorite_topic_source_item)
    TextView txtFavoriteTopicSource;

    public FavoriteTopicSourceHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTxtFavoriteTopicSource() {
        return txtFavoriteTopicSource;
    }
}
