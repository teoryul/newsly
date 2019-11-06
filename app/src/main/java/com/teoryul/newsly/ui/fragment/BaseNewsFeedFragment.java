package com.teoryul.newsly.ui.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.NewsFeedArticleAdapter;
import com.teoryul.newsly.adapter.item.NewsFeedMultiItem;
import com.teoryul.newsly.callback.OnRecyclerViewBottomReachedListener;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.presenter.BaseNewsFeedContract;
import com.teoryul.newsly.ui.activity.newsarticle.NewsArticleActivity;
import com.teoryul.newsly.ui.custom.LoadingLayout;
import com.teoryul.newsly.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * Base implementation of a news feed fragment.
 */
public abstract class BaseNewsFeedFragment extends BaseFragment
        implements BaseNewsFeedContract.View, SwipeRefreshLayout.OnRefreshListener,
        OnRecyclerViewItemClickListener<NewsFeedMultiItem>, OnRecyclerViewBottomReachedListener {

    @BindView(R.id.loading_view)
    LoadingLayout loadingLayout;
    @BindView(R.id.swipe_container)
    protected SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_view_news_feed)
    protected RecyclerView recyclerView;

    protected NewsFeedArticleAdapter newsFeedArticleAdapter;
    protected BaseNewsFeedContract.Presenter presenter;

    @Override
    public void setPresenter(BaseNewsFeedContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter.onCreate();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        configSwipeRefreshLayout();
        initRecyclerView();
        initRecyclerViewAdapter();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.onResume();
    }

    @Override
    public void onDestroy() {
        presenter.onDestroy();
        super.onDestroy();
    }

    protected void configSwipeRefreshLayout() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeColors(
                getResources().getColor(android.R.color.holo_blue_light),
                getResources().getColor(android.R.color.holo_green_light),
                getResources().getColor(android.R.color.holo_orange_light),
                getResources().getColor(android.R.color.holo_red_light));
    }

    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    protected void initRecyclerViewAdapter() {
        newsFeedArticleAdapter = new NewsFeedArticleAdapter(this, this);
        recyclerView.setAdapter(newsFeedArticleAdapter);
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_news_feed;
    }

    /**
     * Displays a progress bar. See {@link LoadingLayout}.
     */
    @Override
    public void showProgressBar() {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.showLoading();
        }
    }

    /**
     * Displays text when there is no loaded content. See {@link LoadingLayout}.
     *
     * @param messageId The text to display
     */
    @Override
    public void showLoadingMessage(int messageId) {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.showLoadingMessage(getString(messageId));
        }
    }

    /**
     * Hides any displayed view from {@link LoadingLayout}.
     */
    @Override
    public void hideAllStates() {
        if (isAdded() && loadingLayout != null) {
            loadingLayout.hideAllViews();
        }
    }

    @Override
    public void showMessageDialog(int titleId, int messageId) {
        if (isAdded()) {
            new AlertDialog.Builder(getBaseActivity())
                    .setTitle(getString(titleId))
                    .setMessage(getString(messageId))
                    .setPositiveButton(R.string.btn_ok,
                            (dialog, which) -> dialog.cancel())
                    .show();
        }
    }

    @Override
    public void onRefresh() {
        refreshNewsFeed();
    }

    protected void refreshNewsFeed() {
        presenter.resetApiRequestPageNumber();
        presenter.requestDataFromApi();
    }

    @Override
    public void updateNewsFeed(List<NewsFeedMultiItem> items) {
        newsFeedArticleAdapter.setData(items);
        setSwipeRefreshState(false);
    }

    @Override
    public void setSwipeRefreshState(boolean isRefreshing) {
        if (swipeRefreshLayout.isRefreshing() != isRefreshing) {
            swipeRefreshLayout.setRefreshing(isRefreshing);
        }
    }

    @Override
    public void clearNewsFeed() {
        newsFeedArticleAdapter.setData(new ArrayList<>());
    }

    @Override
    public void startActivityNewsArticle(ArticlePersist article) {
        Bundle extras = new Bundle();
        extras.putParcelable(AppConstants.BUNDLE_KEY_NEWS_ARTICLE, article);

        Intent intent = new Intent(getContext(), NewsArticleActivity.class);
        intent.putExtras(extras);

        startActivity(intent);
    }

    @Override
    public void setCanClickOnNewsArticles(boolean canClick) {
        newsFeedArticleAdapter.setCanClick(canClick);
    }

    @Override
    public void onRecyclerViewItemClick(NewsFeedMultiItem item) {
        presenter.processNewsArticleClick((ArticlePersist) item);
    }

    @Override
    public void onBottomReached() {
        presenter.incrementApiRequestPageNumber();
        presenter.requestDataFromApi();
    }
}
