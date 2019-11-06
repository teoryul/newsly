package com.teoryul.newsly.presenter.business;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class BusinessFeedPresenter extends BaseNewsFeedPresenter
        implements BusinessFeedContract.Presenter {

    public BusinessFeedPresenter(BusinessFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_BUSINESS);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}