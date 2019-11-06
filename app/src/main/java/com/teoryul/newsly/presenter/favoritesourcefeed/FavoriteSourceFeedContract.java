package com.teoryul.newsly.presenter.favoritesourcefeed;

import com.teoryul.newsly.presenter.BaseNewsFeedContract;

public interface FavoriteSourceFeedContract extends BaseNewsFeedContract {

    interface View extends BaseNewsFeedContract.View {

        void setActivityTitle(String title);

        void setMenuItemAddToFavoritesState(boolean isEnabled);

        void setMenuIconAddToFavorites(boolean isFavorite);
    }

    interface Presenter extends BaseNewsFeedContract.Presenter {

        void addNewsFeedToFavorites();
    }
}
