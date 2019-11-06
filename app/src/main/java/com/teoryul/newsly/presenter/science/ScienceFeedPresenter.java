package com.teoryul.newsly.presenter.science;

import com.teoryul.newsly.network.utils.ApiConstants;
import com.teoryul.newsly.presenter.BaseNewsFeedPresenter;

public class ScienceFeedPresenter extends BaseNewsFeedPresenter
        implements ScienceFeedContract.Presenter {

    public ScienceFeedPresenter(ScienceFeedContract.View view) {
        super(view, ApiConstants.CATEGORY_SCIENCE);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }
}