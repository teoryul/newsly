package com.teoryul.newsly.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;
import com.teoryul.newsly.persistence.model.SourcePersist;
import io.reactivex.Single;

import java.util.List;

@Dao
public interface SourceDao extends BaseDao<SourcePersist> {

    @Query("SELECT * FROM news_sources_table ORDER BY id ASC")
    Single<List<SourcePersist>> getAllSources();
}
