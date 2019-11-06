package com.teoryul.newsly.network.service;

import com.teoryul.newsly.network.response.NewsFeedResponse;
import com.teoryul.newsly.network.response.NewsPublishersResponse;
import com.teoryul.newsly.network.retrofit.NewsApi;

import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class NewsApiService {

    private NewsApi newsApi;

    @Inject
    public NewsApiService(NewsApi newsApi) {
        this.newsApi = newsApi;
    }

    public Single<NewsFeedResponse> getFeedTopHeadlines(String country, String category, int pageNumber) {
        return newsApi.getTopHeadlines(country, category, pageNumber);
    }

    public Single<NewsFeedResponse> getFeedRecentHeadlines(Map<String, String> params) {
        return newsApi.getRecentHeadlines(params);
    }

    public Single<NewsPublishersResponse> getNewsPublishersResponse() {
        return newsApi.getNewsPublishers();
    }
}
