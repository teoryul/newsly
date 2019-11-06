package com.teoryul.newsly.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsFeedResponse {

    @SerializedName("articles")
    private List<ArticleResponse> articles;

    public List<ArticleResponse> getArticles() {
        return articles;
    }
}
