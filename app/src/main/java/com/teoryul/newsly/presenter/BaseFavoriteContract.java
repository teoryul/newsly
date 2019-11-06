package com.teoryul.newsly.presenter;

import com.teoryul.newsly.ui.fragment.LoadingLayoutView;

public interface BaseFavoriteContract {

    interface View extends LoadingLayoutView<Presenter> {

        void showMessageDialog(int titleId, int messageId);

        void setCanClickOnRecyclerViewItems(boolean canClick);

        void clearRecyclerView();
    }

    interface Presenter extends IBasePresenter {
        // Not used
    }
}
