package com.teoryul.newsly.network.utils;

import android.text.TextUtils;
import android.util.Log;

import com.teoryul.newsly.network.response.ArticleResponse;
import com.teoryul.newsly.network.response.NewsFeedResponse;
import com.teoryul.newsly.network.response.NewsPublishersResponse;
import com.teoryul.newsly.network.response.SourceResponse;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.utils.DateUtil;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

public final class NetworkUtils {

    /**
     * Converts a {@link NewsFeedResponse} object to list of {@link ArticlePersist} and
     * sets each article's id and parent table id.
     *
     * @param responseArticles The news feed articles returned from the api
     * @param newsFeedTitle    The unique news feed id of which the feed is part of
     * @return
     */
    public static List<ArticlePersist> extractNewsFeedArticles(List<ArticleResponse> responseArticles, String newsFeedTitle) {
        List<ArticlePersist> persistArticles = new ArrayList<>();
        ArticleResponse articleResponse;

        for (int i = 0; i < responseArticles.size(); i++) {
            articleResponse = responseArticles.get(i);

            if (isArticleResponseInvalid(articleResponse)) {
                continue;
            }

            long publishedAt = 0;
            try {
                publishedAt = DateUtil.stringToMillis(articleResponse.getPublishedAt());
            } catch (ParseException e) {
                Log.e("Error parsing article", e.getMessage());
            }

            ArticlePersist articlePersist = new ArticlePersist(
                    correctStringProperty(articleResponse.getSource().getName()),
                    correctStringProperty(articleResponse.getTitle()),
                    correctApiUrl(articleResponse.getWebUrl()),
                    correctApiUrl(articleResponse.getImgUrl()),
                    publishedAt);

            articlePersist.setIds(newsFeedTitle);

            persistArticles.add(articlePersist);
        }

        return correctPublishedAtTimeProperty(persistArticles);
    }

    /**
     * If the img url is empty or null its respective view will be made invisible.
     *
     * @param article
     * @return True - if either the title or web url are null or empty.
     * False - if both properties are not null and empty.
     */
    private static boolean isArticleResponseInvalid(ArticleResponse article) {
        return TextUtils.isEmpty(article.getTitle()) || TextUtils.isEmpty(article.getWebUrl());
    }

    /**
     * Handles the case if the provided url is null.
     * <p>
     * Handles the case when the api returns an url that doesn't start with "https:" or "http:"
     * which cannot be resolved by Picasso,
     * ie. "//img.bg.sof.cmestatic.com/media/images/640x360/May2019/2111879765.jpg",
     *
     * @param url
     * @return Returns an empty string if url is null.
     * Returns the url with "https:" or "http:" inserted if they are missing or the trimmed url.
     */
    private static String correctApiUrl(String url) {
        if (url == null) {
            return "";
        }

        url = url.trim();

        if (!url.startsWith("https:") && !url.startsWith("http:")) {
            return "https:" + url;
        }

        return url;
    }

    /**
     * Fixes any ArticlePersist objects with their publishedAt property set to 0 after a failed attempt
     * to parse an iso8601 string to long time.
     * <p>
     * If it find such an object, it loops through the complete list of articles,
     * first to the right and then in to the left of its index, in order to find another object
     * with a publishedAt property different than 0 which is then assigned.
     * <p>
     * This is needed so the articles are properly sorted.
     *
     * @param articles
     * @return
     */
    private static List<ArticlePersist> correctPublishedAtTimeProperty(List<ArticlePersist> articles) {
        for (int i = 0; i < articles.size(); i++) {
            if (articles.get(i).getPublishedAt() != 0) {
                continue;
            }

            long time = checkForwards(articles, i + 1);

            if (time == 0) {
                time = checkBackwards(articles, i - 1);
            }

            if (time != 0) {
                articles.get(i).setPublishedAt(time);
            }
        }
        return articles;
    }

    /**
     * Recursive method that looks for a ArticlePersist object with its publishedAt property different than 0.
     * Loops to the left of the provided index through the articles list in order to find it.
     *
     * @param articles
     * @param index
     * @return Returns the first publishedAt property different than 0.
     * Could return 0 if all objects to the left of the index have publishedAt == 0.
     */
    private static long checkForwards(List<ArticlePersist> articles, int index) {
        if (index >= articles.size() - 1) {
            return articles.get(index).getPublishedAt();
        }

        if (articles.get(index).getPublishedAt() != 0) {
            return articles.get(index).getPublishedAt();
        }

        return checkForwards(articles, ++index);
    }

    /**
     * Recursive method that looks for a ArticlePersist object with its publishedAt property different than 0.
     * Loops to the right of the provided index through the articles list in order to find it.
     *
     * @param articles
     * @param index
     * @return Returns the first publishedAt property different than 0.
     * Could return 0 if all objects to the right of the index have publishedAt == 0.
     */
    private static long checkBackwards(List<ArticlePersist> articles, int index) {
        if (index <= 0) {
            return articles.get(index).getPublishedAt();
        }

        if (articles.get(index).getPublishedAt() != 0) {
            return articles.get(index).getPublishedAt();
        }

        return checkBackwards(articles, --index);
    }

    public static List<SourcePersist> extractNewsSources(NewsPublishersResponse allSources) {
        List<SourcePersist> persistSources = new ArrayList<>();

        for (SourceResponse sourceResponse : allSources.getSources()) {
            if (isSourceResponseInvalid(sourceResponse)) {
                continue;
            }

            SourcePersist sourcePersist = new SourcePersist(
                    correctStringProperty(sourceResponse.getId()),
                    correctStringProperty(sourceResponse.getName()),
                    correctStringProperty(sourceResponse.getLanguage()),
                    correctStringProperty(sourceResponse.getCountry()));

            persistSources.add(sourcePersist);
        }

        return persistSources;
    }

    private static boolean isSourceResponseInvalid(SourceResponse source) {
        return TextUtils.isEmpty(source.getId())
                || TextUtils.isEmpty(source.getName())
                || TextUtils.isEmpty(source.getLanguage())
                || TextUtils.isEmpty(source.getCountry());
    }

    private static String correctStringProperty(String property) {
        return TextUtils.isEmpty(property) ? "" : property.trim();
    }
}
