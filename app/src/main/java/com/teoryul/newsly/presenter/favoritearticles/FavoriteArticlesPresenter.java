package com.teoryul.newsly.presenter.favoritearticles;

import android.util.Log;

import com.teoryul.newsly.R;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.service.NewsDBService;
import com.teoryul.newsly.presenter.BasePresenter;
import com.teoryul.newsly.utils.InternetConnectivityHelper;

import java.util.List;

import javax.inject.Inject;

public class FavoriteArticlesPresenter extends BasePresenter
        implements FavoriteArticlesContract.Presenter {

    private final FavoriteArticlesContract.View view;

    @Inject
    NewsDBService newsDBService;

    public FavoriteArticlesPresenter(FavoriteArticlesContract.View view) {
        super();
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onCreate() {
        // Not used
    }

    @Override
    public void onResume() {
        view.showProgressBar();
        view.setCanClickOnRecyclerViewItems(false);
        subscribeSingle(
                newsDBService.getArticleDBService().getBookmarkedArticles(),
                this::onQuerySuccess,
                this::onQueryError);
    }

    @Override
    public void processArticleClick(ArticlePersist article) {
        if (!InternetConnectivityHelper.isInternetConnectionAvailable()) {
            view.showMessageDialog(R.string.title_dialog_error, R.string.check_network_conn_msg);
            view.setCanClickOnRecyclerViewItems(true);
            return;
        }
        view.startActivityNewsArticle(article);
    }

    private void onQuerySuccess(List<ArticlePersist> result) {
        if (result == null || result.isEmpty()) {
            onQueryEmpty();
            return;
        }

        view.hideAllStates();
        view.updateRecyclerView(result);
        view.setCanClickOnRecyclerViewItems(true);
    }

    private void onQueryEmpty() {
        view.clearRecyclerView();
        view.setCanClickOnRecyclerViewItems(true);
        view.showLoadingMessage(R.string.no_saved_news_articles_msg);
    }

    private void onQueryError(Throwable throwable) {
        Log.e("Failed room query", throwable.getMessage());
        onQueryEmpty();
    }
}
