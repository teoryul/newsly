package com.teoryul.newsly.persistence.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;

import java.util.List;

@Dao
public interface BaseDao<T> {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insertItemWithIgnoreStrategy(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertItemWithReplaceStrategy(T t);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insertListWithReplaceStrategy(List<T> t);

    @Delete
    void deleteItem(T t);
}
