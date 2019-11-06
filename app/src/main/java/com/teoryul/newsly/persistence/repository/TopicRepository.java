package com.teoryul.newsly.persistence.repository;

import com.teoryul.newsly.persistence.model.TopicPersist;
import io.reactivex.Single;

import java.util.List;

public interface TopicRepository extends BaseRepository<TopicPersist> {

    Single<List<TopicPersist>> getAllTopics();
}
