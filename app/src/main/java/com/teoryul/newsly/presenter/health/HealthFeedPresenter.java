package com.teoryul.newsly.presenter.health;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class HealthFeedPresenter extends BaseNewsFeedPresenter
        implements HealthFeedContract.Presenter {

    public HealthFeedPresenter(HealthFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_HEALTH);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}