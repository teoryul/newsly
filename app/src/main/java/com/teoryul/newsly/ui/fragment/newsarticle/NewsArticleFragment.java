package com.teoryul.newsly.ui.fragment.newsarticle;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.teoryul.newsly.R;
import com.teoryul.newsly.callback.CustomWebViewClient;
import com.teoryul.newsly.callback.WebViewCallback;
import com.teoryul.newsly.presenter.newsarticle.NewsArticleContract;
import com.teoryul.newsly.ui.custom.LoadingLayout;
import com.teoryul.newsly.ui.fragment.BaseFragment;

import butterknife.BindView;

public class NewsArticleFragment extends BaseFragment
        implements NewsArticleContract.View {

    public static final String TAG = "WEB_VIEW_FRAGMENT";

    @BindView(R.id.loading_view)
    LoadingLayout loadingLayout;
    @BindView(R.id.web_view_news_article)
    WebView webView;

    private NewsArticleContract.Presenter presenter;
    private Menu optionsMenu;

    public static NewsArticleFragment newInstance() {
        return new NewsArticleFragment();
    }

    @Override
    public void setPresenter(NewsArticleContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        presenter.onCreate();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_bookmark_news_article, menu);
        optionsMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_item_bookmark == item.getItemId()) {
            presenter.bookmarkNewsArticle();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_article;
    }

    @Override
    public void setActivityTitle(String title) {
        getBaseActivity().setTitle(title);
    }

    @Override
    public void configWebViewSettings() {
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setBuiltInZoomControls(true);
    }

    @Override
    public void openWebView(String url) {
        webView.setWebViewClient(new CustomWebViewClient((WebViewCallback) presenter));
        webView.loadUrl(url);
    }

    @Override
    public void setWebViewVisibility(boolean isVisible) {
        if (webView != null) {
            webView.setVisibility(isVisible ? View.VISIBLE : View.GONE);
        }
    }

    @Override
    public void setMenuItemBookmarkState(boolean isEnabled) {
        if (optionsMenu == null) {
            return;
        }

        optionsMenu.findItem(R.id.menu_item_bookmark).setEnabled(isEnabled);
    }

    @Override
    public void setMenuIconBookmarked(boolean isBookmarked) {
        if (optionsMenu == null) {
            return;
        }

        MenuItem item = optionsMenu.findItem(R.id.menu_item_bookmark);
        if (item != null) {
            if (isBookmarked) {
                item.setIcon(R.drawable.ic_bookmark_white);
            } else {
                item.setIcon(R.drawable.ic_bookmark_border_white);
            }
        }
    }

    @Override
    public void stopWebViewLoading() {
        if (webView != null) {
            webView.stopLoading();
        }
    }

    /**
     * Handles the button back press in case the user has clicked on links from within the webview.
     *
     * @return True - if the user has clicked on links, False - otherwise
     */
    public boolean canGoBack() {
        if (webView != null && webView.canGoBack()) {
            webView.goBack();
            return true;
        }
        return false;
    }

    @Override
    public void showProgressBar() {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.showLoading();
        }
    }

    @Override
    public void showLoadingMessage(int messageId) {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.showLoadingMessage(getString(messageId));
        }
    }

    @Override
    public void hideAllStates() {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.hideAllViews();
        }
    }
}
