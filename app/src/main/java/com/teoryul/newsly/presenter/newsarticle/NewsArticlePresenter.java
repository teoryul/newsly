package com.teoryul.newsly.presenter.newsarticle;

import android.text.TextUtils;
import android.util.Log;

import com.teoryul.newsly.R;
import com.teoryul.newsly.callback.WebViewCallback;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.persistence.service.NewsDBService;
import com.teoryul.newsly.presenter.BasePresenter;

import javax.inject.Inject;

public class NewsArticlePresenter extends BasePresenter
        implements NewsArticleContract.Presenter, WebViewCallback {

    private final NewsArticleContract.View view;
    private final ArticlePersist article;
    private boolean hasWebViewLoaded;

    @Inject
    NewsDBService newsDBService;

    public NewsArticlePresenter(NewsArticleContract.View view, ArticlePersist article) {
        super();
        this.view = view;
        this.view.setPresenter(this);
        this.article = article;
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onCreate() {
        getArticleBookmarkedState();

        view.setWebViewVisibility(false);
        hasWebViewLoaded = false;

        if (!canLoadNewsArticle()) {
            view.showLoadingMessage(R.string.failed_to_load_web_view);
            return;
        }

        view.showLoadingMessage(R.string.web_view_loading);

        view.setActivityTitle(article.getNewsTitle());
        view.configWebViewSettings();
        view.openWebView(article.getWebUrl());
    }

    @Override
    public void onResume() {
        // Not used
    }

    @Override
    public void onDestroy() {
        view.stopWebViewLoading();
        super.onDestroy();
    }

    @Override
    public void onWebViewLoadSuccess() {
        if (hasWebViewLoaded) {
            return;
        }

        hasWebViewLoaded = true;
        view.hideAllStates();
        view.setWebViewVisibility(true);
        view.setMenuItemBookmarkState(true);
        view.setMenuIconBookmarked(article.isBookmarked());
    }

    @Override
    public void bookmarkNewsArticle() {
        article.setBookmarked(!article.isBookmarked());
        newsDBService.getArticleDBService().insertItemWithReplaceStrategy(article);
        view.setMenuIconBookmarked(article.isBookmarked());
    }

    private boolean canLoadNewsArticle() {
        return article != null
                && !TextUtils.isEmpty(article.getNewsTitle())
                && !TextUtils.isEmpty(article.getWebUrl());
    }

    private void getArticleBookmarkedState() {
        subscribeSingle(
                newsDBService.getArticleDBService().getArticleBookmarkedState(article.getNewsArticleId()),
                article::setBookmarked,
                throwable -> {
                    Log.e("Failed to get state", throwable.getMessage());
                    article.setBookmarked(false);
                });
    }
}
