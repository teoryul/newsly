package com.teoryul.newsly.presenter;

import com.teoryul.newsly.adapter.item.NewsFeedMultiItem;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.ui.fragment.LoadingLayoutView;

import java.util.List;

public interface BaseNewsFeedContract {

    interface View extends LoadingLayoutView<Presenter> {

        void updateNewsFeed(List<NewsFeedMultiItem> items);

        void setSwipeRefreshState(boolean isRefreshing);

        void clearNewsFeed();

        void showMessageDialog(int titleId, int messageId);

        void startActivityNewsArticle(ArticlePersist article);

        void setCanClickOnNewsArticles(boolean canClick);
    }

    interface Presenter extends IBasePresenter {

        void requestDataFromApi();

        void incrementApiRequestPageNumber();

        void decrementApiRequestPageNumber();

        void resetApiRequestPageNumber();

        void processNewsArticleClick(ArticlePersist article);
    }
}
