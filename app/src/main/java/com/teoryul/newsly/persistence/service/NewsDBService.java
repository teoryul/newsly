package com.teoryul.newsly.persistence.service;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class NewsDBService {

    private ArticleDBService articleDBService;
    private NewsFeedDBService newsFeedDBService;
    private TopicDBService topicDBService;
    private SourceDBService sourceDBService;

    @Inject
    public NewsDBService(ArticleDBService articleDBService,
                         NewsFeedDBService newsFeedDBService,
                         TopicDBService topicDBService,
                         SourceDBService sourceDBService) {
        this.articleDBService = articleDBService;
        this.newsFeedDBService = newsFeedDBService;
        this.topicDBService = topicDBService;
        this.sourceDBService = sourceDBService;
    }

    public ArticleDBService getArticleDBService() {
        return articleDBService;
    }

    public NewsFeedDBService getNewsFeedDBService() {
        return newsFeedDBService;
    }

    public TopicDBService getTopicDBService() {
        return topicDBService;
    }

    public SourceDBService getSourceDBService() {
        return sourceDBService;
    }
}
