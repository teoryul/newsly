package com.teoryul.newsly.network.retrofit;

import com.teoryul.newsly.network.response.NewsFeedResponse;
import com.teoryul.newsly.network.response.NewsPublishersResponse;

import java.util.Map;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;

public interface NewsApi {

    @GET("top-headlines")
    Single<NewsFeedResponse> getTopHeadlines(
            @Query("country") String country,
            @Query("category") String category,
            @Query("page") int pageNumber);

    @GET("everything")
    Single<NewsFeedResponse> getRecentHeadlines(@QueryMap Map<String, String> params);

    @GET("sources")
    Single<NewsPublishersResponse> getNewsPublishers();
}
