package com.teoryul.newsly.ui.fragment;

import com.teoryul.newsly.presenter.IBasePresenter;

public interface LoadingLayoutView<T extends IBasePresenter> extends BaseView<T> {

    void showProgressBar();

    void showLoadingMessage(int messageId);

    void hideAllStates();
}
