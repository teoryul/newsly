package com.teoryul.newsly.network.response;

import com.google.gson.annotations.SerializedName;

public class SourceResponse {

    @SerializedName("id")
    private String id;

    @SerializedName("name")
    private String name;

    @SerializedName("language")
    private String language;

    @SerializedName("country")
    private String country;

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getLanguage() {
        return language;
    }

    public String getCountry() {
        return country;
    }
}
