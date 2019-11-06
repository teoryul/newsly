package com.teoryul.newsly.presenter.newsarticle;

import com.teoryul.newsly.presenter.IBasePresenter;
import com.teoryul.newsly.ui.fragment.LoadingLayoutView;

public interface NewsArticleContract {

    interface View extends LoadingLayoutView<Presenter> {

        void setActivityTitle(String title);

        void configWebViewSettings();

        void openWebView(String url);

        void setWebViewVisibility(boolean isVisible);

        void setMenuItemBookmarkState(boolean isEnabled);

        void setMenuIconBookmarked(boolean isBookmarked);

        void stopWebViewLoading();
    }

    interface Presenter extends IBasePresenter {

        void bookmarkNewsArticle();
    }
}
