package com.teoryul.newsly.persistence.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import com.teoryul.newsly.persistence.dao.ArticleDao;
import com.teoryul.newsly.persistence.dao.NewsFeedDao;
import com.teoryul.newsly.persistence.dao.SourceDao;
import com.teoryul.newsly.persistence.dao.TopicDao;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.model.NewsFeedPersist;
import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.persistence.model.TopicPersist;

@Database(
        entities = {
                TopicPersist.class,
                ArticlePersist.class,
                NewsFeedPersist.class,
                SourcePersist.class},
        version = 1)
public abstract class NewsDatabase extends RoomDatabase {

    public abstract TopicDao favoriteTopicDao();

    public abstract ArticleDao newsArticleDao();

    public abstract NewsFeedDao newsFeedDao();

    public abstract SourceDao newsSourceDao();
}
