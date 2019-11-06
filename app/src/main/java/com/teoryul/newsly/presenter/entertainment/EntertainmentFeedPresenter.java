package com.teoryul.newsly.presenter.entertainment;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class EntertainmentFeedPresenter extends BaseNewsFeedPresenter
        implements EntertainmentFeedContract.Presenter {

    public EntertainmentFeedPresenter(EntertainmentFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_ENTERTAINMENT);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}