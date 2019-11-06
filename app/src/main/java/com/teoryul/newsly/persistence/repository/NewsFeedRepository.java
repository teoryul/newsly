package com.teoryul.newsly.persistence.repository;

import com.teoryul.newsly.persistence.model.NewsFeedPersist;

public interface NewsFeedRepository extends BaseRepository<NewsFeedPersist> {

    void deleteNewsFeedByTitle(String title);
}
