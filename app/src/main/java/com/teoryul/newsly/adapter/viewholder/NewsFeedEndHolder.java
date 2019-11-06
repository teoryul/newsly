package com.teoryul.newsly.adapter.viewholder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.teoryul.newsly.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class NewsFeedEndHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.txt_news_feed_end)
    TextView txtNewsFeedEnd;

    public NewsFeedEndHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public TextView getTxtNewsFeedEnd() {
        return txtNewsFeedEnd;
    }
}
