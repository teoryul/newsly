package com.teoryul.newsly.presenter.personalized;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class PersonalizedFeedPresenter extends BaseNewsFeedPresenter
        implements PersonalizedFeedContract.Presenter {

    public PersonalizedFeedPresenter(PersonalizedFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_GENERAL);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}