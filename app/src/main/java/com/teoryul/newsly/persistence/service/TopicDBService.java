package com.teoryul.newsly.persistence.service;

import com.teoryul.newsly.persistence.dao.TopicDao;
import com.teoryul.newsly.persistence.model.TopicPersist;
import com.teoryul.newsly.persistence.repository.TopicRepository;
import com.teoryul.newsly.utils.AppExecutor;
import io.reactivex.Single;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.List;

@Singleton
public class TopicDBService implements TopicRepository {

    private final TopicDao dao;
    private final AppExecutor appExecutor;

    @Inject
    public TopicDBService(TopicDao dao, AppExecutor appExecutor) {
        this.dao = dao;
        this.appExecutor = appExecutor;
    }

    @Override
    public void insertItemWithIgnoreStrategy(TopicPersist topic) {
        appExecutor.execute(() -> dao.insertItemWithIgnoreStrategy(topic));
    }

    @Override
    public void insertItemWithReplaceStrategy(TopicPersist topic) {
        appExecutor.execute(() -> dao.insertItemWithReplaceStrategy(topic));
    }

    /**
     * NOTE: When deleting a topic you must also delete its respective news feed and articles.
     *
     * @param topic
     */
    @Override
    public void deleteItem(TopicPersist topic) {
        appExecutor.execute(() -> dao.deleteItem(topic));
    }

    @Override
    public Single<List<TopicPersist>> getAllTopics() {
        return dao.getAllTopics();
    }
}
