package com.teoryul.newsly.persistence.service;

import com.teoryul.newsly.persistence.dao.NewsFeedDao;
import com.teoryul.newsly.persistence.model.NewsFeedPersist;
import com.teoryul.newsly.persistence.repository.NewsFeedRepository;
import com.teoryul.newsly.utils.AppExecutor;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NewsFeedDBService implements NewsFeedRepository {

    private final NewsFeedDao dao;
    private final AppExecutor appExecutor;

    @Inject
    public NewsFeedDBService(NewsFeedDao dao, AppExecutor appExecutor) {
        this.dao = dao;
        this.appExecutor = appExecutor;
    }

    @Override
    public void insertItemWithIgnoreStrategy(NewsFeedPersist newsFeed) {
        appExecutor.execute(() -> dao.insertItemWithIgnoreStrategy(newsFeed));
    }

    @Override
    public void insertItemWithReplaceStrategy(NewsFeedPersist feed) {
        appExecutor.execute(() -> dao.insertItemWithReplaceStrategy(feed));
    }

    @Override
    public void deleteItem(NewsFeedPersist feed) {
        appExecutor.execute(() -> dao.deleteItem(feed));
    }

    @Override
    public void deleteNewsFeedByTitle(String title) {
        appExecutor.execute(() -> dao.deleteNewsFeedByTitle(title));
    }
}
