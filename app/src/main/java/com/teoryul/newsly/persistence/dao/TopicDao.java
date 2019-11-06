package com.teoryul.newsly.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.teoryul.newsly.persistence.model.TopicPersist;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface TopicDao extends BaseDao<TopicPersist> {

    @Query("SELECT * FROM favorite_topics_table ORDER BY added_at DESC")
    Single<List<TopicPersist>> getAllTopics();
}
