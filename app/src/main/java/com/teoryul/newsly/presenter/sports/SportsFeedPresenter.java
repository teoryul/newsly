package com.teoryul.newsly.presenter.sports;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class SportsFeedPresenter extends BaseNewsFeedPresenter
        implements SportsFeedContract.Presenter {

    public SportsFeedPresenter(SportsFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_SPORTS);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}