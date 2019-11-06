package com.teoryul.newsly.dagger.module;

import android.app.Application;
import android.arch.persistence.room.Room;

import com.teoryul.newsly.persistence.dao.ArticleDao;
import com.teoryul.newsly.persistence.dao.NewsFeedDao;
import com.teoryul.newsly.persistence.dao.SourceDao;
import com.teoryul.newsly.persistence.dao.TopicDao;
import com.teoryul.newsly.persistence.database.NewsDatabase;

import dagger.Module;
import dagger.Provides;

@Module
public class RoomModule {

    private final NewsDatabase newsDatabase;

    public RoomModule(Application application) {
        newsDatabase = Room
                .databaseBuilder(application, NewsDatabase.class, "newsly_db")
                .fallbackToDestructiveMigration()
                .build();
    }

    @Provides
    NewsDatabase provideNewsDatabase() {
        return newsDatabase;
    }

    @Provides
    TopicDao provideFavoriteTopicDao() {
        return newsDatabase.favoriteTopicDao();
    }

    @Provides
    ArticleDao provideNewsArticleDao() {
        return newsDatabase.newsArticleDao();
    }

    @Provides
    NewsFeedDao provideNewsFeedDao() {
        return newsDatabase.newsFeedDao();
    }

    @Provides
    SourceDao provideNewsSourceDao() {
        return newsDatabase.newsSourceDao();
    }
}
