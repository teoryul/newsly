package com.teoryul.newsly.presenter.favoritetopicfeed;

import android.util.Log;

import com.teoryul.newsly.network.response.NewsFeedResponse;
import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.model.NewsFeedPersist;
import com.teoryul.newsly.persistence.model.TopicPersist;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * A favorite news feed consists of the following objects:
 * {@link TopicPersist}, which holds the topics title/user's topic search keywords
 * and whether the user has added it to favorites,
 * {@link NewsFeedPersist}, which holds the title/user's topic search keywords
 * and serves as a parent to all news articles,
 * {@link ArticlePersist}, basically a list of news which is returned as a search result form the api.
 */
public class FavoriteTopicFeedPresenter extends BaseNewsFeedPresenter
        implements FavoriteTopicFeedContract.Presenter {

    private final TopicPersist topic;

    public FavoriteTopicFeedPresenter(FavoriteTopicFeedContract.View view, TopicPersist topic) {
        super(view, topic.getTopicName());
        this.topic = topic;
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onCreate() {
        setActivityTitle();
        super.onCreate();
    }

    @Override
    public void onResume() {
        view.setCanClickOnNewsArticles(true);
        view.showProgressBar();

        if (sharedPreferencesUtil.hasNewsFeedLanguageSettingChanged(newsFeedTitle)) {
            view.clearNewsFeed();
            emptyNewsFeedTable();
            requestDataFromApi();
            return;
        }

        if (topic.isFavorite()) {
            if (sharedPreferencesUtil.hasTimePassedSincePreviousApiRequest(newsFeedTitle)) {
                requestDataFromApi();
                return;
            }

            queryDataFromDB();
            return;
        }

        requestDataFromApi();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        updateTopicInDatabase();
        updateTopicFeedInDatabase();
    }

    @Override
    public void requestDataFromApi() {
        subscribeSingle(
                newsApiService.getFeedRecentHeadlines(getRequestParams()),
                this::onRequestSuccess,
                this::onRequestError);
    }

    /**
     * Adds or Removes this topic to/from favorites.
     */
    @Override
    public void addNewsFeedToFavorites() {
        topic.setFavorite(!topic.isFavorite());
        setMenuIconAddToFavorites();
    }

    @Override
    protected void onRequestSuccess(NewsFeedResponse result) {
        super.onRequestSuccess(result);
        setMenuItemAddToFavoritesState(true);
        setMenuIconAddToFavorites();
    }

    @Override
    public void onQuerySuccess(List<ArticlePersist> result) {
        super.onQuerySuccess(result);
        setMenuItemAddToFavoritesState(true);
        setMenuIconAddToFavorites();
    }

    @Override
    public void onQueryEmpty() {
        super.onQueryEmpty();
        setMenuItemAddToFavoritesState(true);
        setMenuIconAddToFavorites();
    }

    private void setActivityTitle() {
        try {
            ((FavoriteTopicFeedContract.View) view).setActivityTitle(newsFeedTitle);
        } catch (ClassCastException e) {
            Log.e("View cast failed", e.getMessage());
        }
    }

    private void setMenuIconAddToFavorites() {
        try {
            ((FavoriteTopicFeedContract.View) view).setMenuIconAddToFavorites(topic.isFavorite());
        } catch (ClassCastException e) {
            Log.e("View cast failed", e.getMessage());
        }
    }

    private void setMenuItemAddToFavoritesState(boolean isEnabled) {
        try {
            ((FavoriteTopicFeedContract.View) view).setMenuItemAddToFavoritesState(isEnabled);
        } catch (ClassCastException e) {
            Log.e("View cast failed", e.getMessage());
        }
    }

    private Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.PARAM_LANGUAGE, sharedPreferencesUtil.getLanguageSetting());
        params.put(ApiConstants.PARAM_SEARCH, newsFeedTitle);
        params.put(ApiConstants.PARAM_PAGE, String.valueOf(requestPageNumber));
        return params;
    }

    /**
     * Adds/Removes this topic object to/from the database.
     */
    private void updateTopicInDatabase() {
        if (topic.isFavorite()) {
            newsDBService.getTopicDBService().insertItemWithReplaceStrategy(topic);
        } else {
            newsDBService.getTopicDBService().deleteItem(topic);
        }
    }

    /**
     * Adds/Removes this topic's news feed and articles to/from the database.
     * <p>
     * NOTE: This news feed and its articles are already saved in the db.
     * They must be removed if the user exits this screen without adding the feed to favorites.
     * <p>
     * When deleting a news feed its articles will automatically be deleted as well
     * due to how they are set up - onDelete = ForeignKey.CASCADE. See {@link ArticlePersist}.
     */
    private void updateTopicFeedInDatabase() {
        if (!topic.isFavorite()) {
            newsDBService.getNewsFeedDBService().deleteNewsFeedByTitle(newsFeedTitle);
        }
    }
}
