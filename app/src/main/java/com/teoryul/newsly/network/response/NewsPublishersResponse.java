package com.teoryul.newsly.network.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class NewsPublishersResponse {

    @SerializedName("sources")
    private List<SourceResponse> sources;

    public List<SourceResponse> getSources() {
        return sources;
    }
}
