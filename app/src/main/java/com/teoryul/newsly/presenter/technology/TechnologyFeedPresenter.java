package com.teoryul.newsly.presenter.technology;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class TechnologyFeedPresenter extends BaseNewsFeedPresenter
        implements TechnologyFeedContract.Presenter {

    public TechnologyFeedPresenter(TechnologyFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_TECHNOLOGY);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}