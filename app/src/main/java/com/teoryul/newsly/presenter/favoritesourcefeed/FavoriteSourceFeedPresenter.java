package com.teoryul.newsly.presenter.favoritesourcefeed;

import android.text.TextUtils;
import android.util.Log;

import com.teoryul.newsly.network.response.NewsFeedResponse;
import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class FavoriteSourceFeedPresenter extends BaseNewsFeedPresenter
        implements FavoriteSourceFeedContract.Presenter {

    private final SourcePersist source;

    public FavoriteSourceFeedPresenter(FavoriteSourceFeedContract.View view, SourcePersist source) {
        super(view, source.getId());
        this.source = source;
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

        if (source.isFavorite()) {
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
        updateSourceInDatabase();
        updateNewsFeedInDatabase();
    }

    @Override
    public void requestDataFromApi() {
        subscribeSingle(
                newsApiService.getFeedRecentHeadlines(getRequestParams()),
                this::onRequestSuccess,
                this::onRequestError);
    }

    /**
     * Adds/Removes this news source feed to/from favorites.
     */
    @Override
    public void addNewsFeedToFavorites() {
        source.setFavorite(!source.isFavorite());
        setMenuIconAddToFavorites();
    }

    @Override
    protected void onRequestSuccess(NewsFeedResponse result) {
        super.onRequestSuccess(result);
        setMenuItemAddToFavoritesState(true);
        setMenuIconAddToFavorites();
    }

    @Override
    protected void onQuerySuccess(List<ArticlePersist> result) {
        super.onQuerySuccess(result);
        setMenuItemAddToFavoritesState(true);
        setMenuIconAddToFavorites();
    }

    @Override
    protected void onQueryEmpty() {
        super.onQueryEmpty();
        setMenuItemAddToFavoritesState(true);
        setMenuIconAddToFavorites();
    }

    private void setActivityTitle() {
        try {
            ((FavoriteSourceFeedContract.View) view).setActivityTitle(TextUtils.concat(source.getName(), " (", source.getLanguage().toUpperCase(), ")").toString());
        } catch (ClassCastException e) {
            Log.e("View cast failed", e.getMessage());
        }
    }

    private void setMenuItemAddToFavoritesState(boolean isEnabled) {
        try {
            ((FavoriteSourceFeedContract.View) view).setMenuItemAddToFavoritesState(isEnabled);
        } catch (ClassCastException e) {
            Log.e("View cast failed", e.getMessage());
        }
    }

    private void setMenuIconAddToFavorites() {
        try {
            ((FavoriteSourceFeedContract.View) view).setMenuIconAddToFavorites(source.isFavorite());
        } catch (ClassCastException e) {
            Log.e("View cast failed", e.getMessage());
        }
    }

    private Map<String, String> getRequestParams() {
        Map<String, String> params = new HashMap<>();
        params.put(ApiConstants.PARAM_SOURCES, newsFeedTitle);
        params.put(ApiConstants.PARAM_PAGE, String.valueOf(requestPageNumber));
        return params;
    }

    /**
     * Adds/Removes this source object to/from the database.
     */
    private void updateSourceInDatabase() {
        if (source.isFavorite()) {
            newsDBService.getSourceDBService().insertItemWithReplaceStrategy(source);
        } else {
            newsDBService.getSourceDBService().deleteItem(source);
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
    private void updateNewsFeedInDatabase() {
        if (!source.isFavorite()) {
            newsDBService.getNewsFeedDBService().deleteNewsFeedByTitle(newsFeedTitle);
        }
    }
}
