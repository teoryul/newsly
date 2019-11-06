package com.teoryul.newsly.persistence.service;

import com.teoryul.newsly.persistence.dao.ArticleDao;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.repository.ArticleRepository;
import com.teoryul.newsly.utils.AppExecutor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class ArticleDBService implements ArticleRepository {

    private final ArticleDao dao;
    private final AppExecutor appExecutor;

    @Inject
    public ArticleDBService(ArticleDao dao, AppExecutor appExecutor) {
        this.dao = dao;
        this.appExecutor = appExecutor;
    }

    @Override
    public void insertItemWithIgnoreStrategy(ArticlePersist article) {
        appExecutor.execute(() -> dao.insertItemWithIgnoreStrategy(article));
    }

    @Override
    public void insertItemWithReplaceStrategy(ArticlePersist article) {
        appExecutor.execute(() -> dao.insertItemWithReplaceStrategy(article));
    }

    @Override
    public void deleteItem(ArticlePersist article) {
        appExecutor.execute(() -> dao.deleteItem(article));
    }

    @Override
    public void insertArticlesWithReplaceStrategy(List<ArticlePersist> articles) {
        appExecutor.execute(() -> dao.insertListWithReplaceStrategy(articles));
    }

    @Override
    public void deleteArticlesOlderThan(String newsFeedTitle, long currentTime, long period) {
        appExecutor.execute(() -> dao.deleteArticlesOlderThan(newsFeedTitle, currentTime, period));
    }

    @Override
    public void deleteArticlesFromNewsFeed(String newsFeedTitle) {
        appExecutor.execute(() -> dao.deleteArticlesFromNewsFeed(newsFeedTitle));
    }

    @Override
    public Single<Boolean> getArticleBookmarkedState(int newsArticleId) {
        return dao.getArticleBookmarkedState(newsArticleId);
    }

    @Override
    public Single<List<ArticlePersist>> getBookmarkedArticles() {
        return dao.getBookmarkedArticles();
    }

    @Override
    public Single<List<ArticlePersist>> getArticlesFromNewsFeed(String newsFeedTitle) {
        return dao.getArticlesFromNewsFeed(newsFeedTitle);
    }
}
