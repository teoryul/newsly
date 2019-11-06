package com.teoryul.newsly.persistence.repository;

public interface BaseRepository<T> {

    void insertItemWithIgnoreStrategy(T t);

    void insertItemWithReplaceStrategy(T t);

    void deleteItem(T t);
}
