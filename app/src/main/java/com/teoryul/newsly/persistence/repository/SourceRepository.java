package com.teoryul.newsly.persistence.repository;

import com.teoryul.newsly.persistence.model.SourcePersist;
import io.reactivex.Single;

import java.util.List;

public interface SourceRepository extends BaseRepository<SourcePersist> {

    void insertSourcesWithReplaceStrategy(List<SourcePersist> sources);

    Single<List<SourcePersist>> getAllSources();
}
