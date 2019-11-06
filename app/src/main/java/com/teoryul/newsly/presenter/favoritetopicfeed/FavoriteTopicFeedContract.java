package com.teoryul.newsly.presenter.favoritetopicfeed;

import com.teoryul.newsly.presenter.BaseNewsFeedContract;

public interface FavoriteTopicFeedContract extends BaseNewsFeedContract {

    interface View extends BaseNewsFeedContract.View {

        void setActivityTitle(String title);

        void setMenuItemAddToFavoritesState(boolean isEnabled);

        void setMenuIconAddToFavorites(boolean isFavorite);
    }

    interface Presenter extends BaseNewsFeedContract.Presenter {

        void addNewsFeedToFavorites();
    }
}
