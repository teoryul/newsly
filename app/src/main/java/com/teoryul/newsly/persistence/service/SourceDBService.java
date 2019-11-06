package com.teoryul.newsly.persistence.service;

import com.teoryul.newsly.persistence.dao.SourceDao;
import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.persistence.repository.SourceRepository;
import com.teoryul.newsly.utils.AppExecutor;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Singleton;

import io.reactivex.Single;

@Singleton
public class SourceDBService implements SourceRepository {

    private final SourceDao dao;
    private final AppExecutor appExecutor;

    @Inject
    public SourceDBService(SourceDao dao, AppExecutor appExecutor) {
        this.dao = dao;
        this.appExecutor = appExecutor;
    }

    @Override
    public void insertItemWithIgnoreStrategy(SourcePersist source) {
        appExecutor.execute(() -> dao.insertItemWithIgnoreStrategy(source));
    }

    @Override
    public void insertItemWithReplaceStrategy(SourcePersist source) {
        appExecutor.execute(() -> dao.insertItemWithReplaceStrategy(source));
    }

    @Override
    public void deleteItem(SourcePersist source) {
        appExecutor.execute(() -> dao.deleteItem(source));
    }

    @Override
    public void insertSourcesWithReplaceStrategy(List<SourcePersist> sources) {
        appExecutor.execute(() -> dao.insertListWithReplaceStrategy(sources));
    }

    @Override
    public Single<List<SourcePersist>> getAllSources() {
        return dao.getAllSources();
    }
}
