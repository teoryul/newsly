package com.teoryul.newsly.presenter.favoritetopics;

import android.text.TextUtils;
import android.util.Log;

import com.teoryul.newsly.R;
import com.teoryul.newsly.persistence.model.TopicPersist;
import com.teoryul.newsly.persistence.service.NewsDBService;
import com.teoryul.newsly.presenter.BasePresenter;
import com.teoryul.newsly.utils.InternetConnectivityHelper;

import java.util.List;

import javax.inject.Inject;

public class FavoriteTopicsPresenter extends BasePresenter
        implements FavoriteTopicsContract.Presenter {

    private final FavoriteTopicsContract.View view;

    @Inject
    NewsDBService newsDBService;

    public FavoriteTopicsPresenter(FavoriteTopicsContract.View view) {
        super();
        this.view = view;
        this.view.setPresenter(this);
    }

    @Override
    protected void inject() {
        getAppComponent().inject(this);
    }

    @Override
    public void onCreate() {
        // Not used
    }

    @Override
    public void onResume() {
        view.showProgressBar();
        setViewElementsState(false);
        subscribeSingle(
                newsDBService.getTopicDBService().getAllTopics(),
                this::onQuerySuccess,
                this::onQueryError);
    }

    @Override
    public void onSearch(String phrase) {
        if (TextUtils.isEmpty(phrase)) {
            view.showMessageDialog(R.string.title_dialog_error, R.string.invalid_topic_search_phrase_msg);
            return;
        }
        view.setFabSearchTopicState(false);
        view.setCanClickOnRecyclerViewItems(false);
        processTopicClick(new TopicPersist(phrase));
    }

    @Override
    public void processTopicClick(TopicPersist topic) {
        if (!InternetConnectivityHelper.isInternetConnectionAvailable()) {
            view.showMessageDialog(R.string.title_dialog_error, R.string.check_network_conn_msg);
            view.setCanClickOnRecyclerViewItems(true);
            return;
        }
        view.loadTopicFeedFragment(topic);
    }

    private void onQuerySuccess(List<TopicPersist> result) {
        if (result == null || result.isEmpty()) {
            onQueryEmpty();
            return;
        }

        view.hideAllStates();
        view.updateRecyclerView(result);
        setViewElementsState(true);
    }

    private void onQueryEmpty() {
        view.clearRecyclerView();
        setViewElementsState(true);
        view.showLoadingMessage(R.string.add_favorite_topics_msg);
    }

    private void onQueryError(Throwable throwable) {
        Log.e("Failed room query", throwable.getMessage());
        onQueryEmpty();
    }

    private void setViewElementsState(boolean enabled) {
        view.setFabSearchTopicState(enabled);
        view.setCanClickOnRecyclerViewItems(enabled);
    }
}
