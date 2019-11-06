package com.teoryul.newsly.presenter;

import android.util.Log;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.item.NewsFeedEndItem;
import com.teoryul.newsly.adapter.item.NewsFeedMultiItem;
import com.teoryul.newsly.network.response.ArticleResponse;
import com.teoryul.newsly.network.response.NewsFeedResponse;
import com.teoryul.newsly.network.service.NewsApiService;
import com.teoryul.newsly.network.utils.NetworkUtils;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.model.NewsFeedPersist;
import com.teoryul.newsly.persistence.service.NewsDBService;
import com.teoryul.newsly.utils.AppConstants;
import com.teoryul.newsly.utils.DateUtil;
import com.teoryul.newsly.utils.InternetConnectivityHelper;
import com.teoryul.newsly.utils.SharedPreferencesUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

/**
 * Base implementation of a news feed presenter.
 */
public abstract class BaseNewsFeedPresenter extends BasePresenter
        implements BaseNewsFeedContract.Presenter {

    protected final BaseNewsFeedContract.View view;
    protected final String newsFeedTitle;
    protected int requestPageNumber = 1;
    protected final Set<ArticlePersist> articlesSet;

    @Inject
    protected NewsApiService newsApiService;
    @Inject
    protected NewsDBService newsDBService;
    @Inject
    protected SharedPreferencesUtil sharedPreferencesUtil;

    public BaseNewsFeedPresenter(BaseNewsFeedContract.View view, String newsFeedTitle) {
        super();
        this.view = view;
        this.view.setPresenter(this);
        this.newsFeedTitle = newsFeedTitle;
        this.articlesSet = new HashSet<>();
    }

    @Override
    public void onCreate() {
        newsDBService.getNewsFeedDBService().insertItemWithIgnoreStrategy(new NewsFeedPersist(newsFeedTitle));
        newsDBService.getArticleDBService().deleteArticlesOlderThan(newsFeedTitle, DateUtil.getCurrentTimeAsMillis(), AppConstants.THREE_DAYS_AS_MILLIS);
    }

    @Override
    public void onResume() {
        view.setCanClickOnNewsArticles(false);
        view.showProgressBar();

        if (sharedPreferencesUtil.hasNewsFeedCountrySettingChanged(newsFeedTitle)) {
            view.clearNewsFeed();
            emptyNewsFeedTable();
            requestDataFromApi();
            return;
        }

        if (sharedPreferencesUtil.hasTimePassedSincePreviousApiRequest(newsFeedTitle)) {
            requestDataFromApi();
            return;
        }

        queryDataFromDB();
    }

    @Override
    public void incrementApiRequestPageNumber() {
        requestPageNumber++;
    }

    @Override
    public void decrementApiRequestPageNumber() {
        if (--requestPageNumber <= 0) {
            resetApiRequestPageNumber();
        }
    }

    @Override
    public void resetApiRequestPageNumber() {
        requestPageNumber = 1;
    }

    @Override
    public void processNewsArticleClick(ArticlePersist article) {
        if (!InternetConnectivityHelper.isInternetConnectionAvailable()) {
            view.showMessageDialog(R.string.title_dialog_error, R.string.check_network_conn_msg);
            view.setCanClickOnNewsArticles(true);
            return;
        }
        view.startActivityNewsArticle(article);
    }

    @Override
    public void requestDataFromApi() {
        subscribeSingle(
                newsApiService.getFeedTopHeadlines(sharedPreferencesUtil.getCountrySetting(), newsFeedTitle, requestPageNumber),
                this::onRequestSuccess,
                this::onRequestError);
    }

    protected void onRequestSuccess(NewsFeedResponse result) {
        if (result == null || result.getArticles().isEmpty()) {
            onRequestEmpty();
            return;
        }

        sharedPreferencesUtil.saveApiRequestTimestamp(newsFeedTitle);
        sharedPreferencesUtil.saveNewsFeedCountrySetting(newsFeedTitle);
        List<ArticlePersist> articles = processArticlesResponse(result.getArticles());

        view.hideAllStates();
        view.updateNewsFeed(convertToNewsFeedMultiItemList(articles));
        view.setCanClickOnNewsArticles(true);
    }

    protected void onRequestEmpty() {
        decrementApiRequestPageNumber();
        sharedPreferencesUtil.saveApiRequestTimestamp(newsFeedTitle);
        sharedPreferencesUtil.saveNewsFeedCountrySetting(newsFeedTitle);
        queryDataFromDB();
    }

    protected void onRequestError(Throwable throwable) {
        Log.e("Failed api request", throwable.getMessage());
        view.showMessageDialog(R.string.title_dialog_error, R.string.check_network_conn_msg);
        queryDataFromDB();
    }

    protected void queryDataFromDB() {
        subscribeSingle(
                newsDBService.getArticleDBService().getArticlesFromNewsFeed(newsFeedTitle),
                this::onQuerySuccess,
                this::onQueryError);
    }

    protected void onQuerySuccess(List<ArticlePersist> result) {
        if (result == null || result.isEmpty()) {
            onQueryEmpty();
            return;
        }

        if (!articlesSet.isEmpty()) {
            articlesSet.clear();
        }
        articlesSet.addAll(result);
        Collections.sort(result);

        view.hideAllStates();
        view.updateNewsFeed(convertToNewsFeedMultiItemList(result));
        view.setCanClickOnNewsArticles(true);
    }

    protected void onQueryEmpty() {
        view.clearNewsFeed();
        view.setSwipeRefreshState(false);
        view.setCanClickOnNewsArticles(true);
        view.showLoadingMessage(R.string.empty_news_feed_msg);
    }

    protected void onQueryError(Throwable throwable) {
        Log.e("Failed room query", throwable.getMessage());
        onQueryEmpty();
    }

    /**
     * Converts a list of article response objects to a list of articles persist objects.
     * Persists the new articles in the db, skips any duplicates.
     * Adds the whole list to the set of articles, skips any duplicates.
     * Converts the set back to a list, which is then sorted and returned.
     *
     * @param response The new articles received via a api request
     * @return Sorted list of article persist objects by their publishedAt property is descending order.
     */
    private List<ArticlePersist> processArticlesResponse(List<ArticleResponse> response) {
        List<ArticlePersist> persist = NetworkUtils.extractNewsFeedArticles(response, newsFeedTitle);

        for (ArticlePersist article : persist) {
            newsDBService.getArticleDBService().insertItemWithIgnoreStrategy(article);
        }

        articlesSet.addAll(persist);
        persist.clear();
        persist.addAll(articlesSet);
        Collections.sort(persist);

        return persist;
    }

    /**
     * Converts the list of articles to a list of news feed multi items which will end up displayed
     * in the recycler view. Also, appends a NewsFeedEndItem, which represents a TextView for
     * showing the end of the news feed.
     *
     * @param articles
     * @return
     */
    private List<NewsFeedMultiItem> convertToNewsFeedMultiItemList(List<ArticlePersist> articles) {
        List<NewsFeedMultiItem> items = new ArrayList<>(articles);
        items.add(new NewsFeedEndItem());
        return items;
    }

    /**
     * Deletes all news articles from the news feed table.
     * Called when the user switches the preferred news language / region.
     */
    protected final void emptyNewsFeedTable() {
        newsDBService.getArticleDBService().deleteArticlesFromNewsFeed(newsFeedTitle);
    }
}
