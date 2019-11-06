package com.teoryul.newsly.network.response;

import com.google.gson.annotations.SerializedName;

public class ArticleResponse {

    @SerializedName("source")
    private SourceResponse source;

    @SerializedName("title")
    private String title;

    @SerializedName("url")
    private String webUrl;

    @SerializedName("urlToImage")
    private String imgUrl;

    @SerializedName("publishedAt")
    private String publishedAt;

    public SourceResponse getSource() {
        return source;
    }

    public String getTitle() {
        return title;
    }

    public String getWebUrl() {
        return webUrl;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public String getPublishedAt() {
        return publishedAt;
    }
}
