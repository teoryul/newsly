package com.teoryul.newsly.ui.activity.newsarticle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;

import com.teoryul.newsly.R;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.presenter.newsarticle.NewsArticlePresenter;
import com.teoryul.newsly.ui.activity.BaseActivity;
import com.teoryul.newsly.ui.fragment.newsarticle.NewsArticleFragment;
import com.teoryul.newsly.utils.AppConstants;

public class NewsArticleActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addBackButton();
        loadFragment();
    }

    private void addBackButton() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    /**
     * Loads a fragment with a Web View.
     * Passes the clicked article object to the presenter which handles the case if it is null.
     * If the getExtras is null, a null object is passed and later checked here {@link NewsArticlePresenter#canLoadNewsArticle()}.
     * Also, see {@link com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment#startActivityNewsArticle(ArticlePersist)}.
     */
    private void loadFragment() {
        NewsArticleFragment fragment = NewsArticleFragment.newInstance();

        if (getIntent().getExtras() == null) {
            new NewsArticlePresenter(fragment, null);
        } else {
            new NewsArticlePresenter(fragment, getIntent().getExtras().getParcelable(AppConstants.BUNDLE_KEY_NEWS_ARTICLE));
        }

        replaceFragment(fragment, NewsArticleFragment.TAG, false, null);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_news_article;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (android.R.id.home == item.getItemId()) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Handles back button presses depending on the following cases:
     * - exit the news article activity
     * - if the user has clicked on links from within the Web View, see {@link NewsArticleFragment#canGoBack()}.
     */
    @Override
    public void onBackPressed() {
        Fragment webView = getSupportFragmentManager().findFragmentByTag(NewsArticleFragment.TAG);
        if (webView instanceof NewsArticleFragment) {
            boolean canGoBack = ((NewsArticleFragment) webView).canGoBack();
            if (!canGoBack) {
                super.onBackPressed();
            }
        }
    }
}
