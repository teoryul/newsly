package com.teoryul.newsly.ui.fragment.favoritearticles;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.SavedArticlesAdapter;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.ArticlePersist;
import com.teoryul.newsly.presenter.BaseFavoriteContract;
import com.teoryul.newsly.presenter.favoritearticles.FavoriteArticlesContract;
import com.teoryul.newsly.ui.activity.newsarticle.NewsArticleActivity;
import com.teoryul.newsly.ui.fragment.BaseFavoriteFragment;
import com.teoryul.newsly.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

public class FavoriteArticlesFragment extends BaseFavoriteFragment
        implements FavoriteArticlesContract.View, OnRecyclerViewItemClickListener<ArticlePersist> {

    private SavedArticlesAdapter savedArticlesAdapter;
    private FavoriteArticlesContract.Presenter presenter;

    public static FavoriteArticlesFragment newInstance() {
        return new FavoriteArticlesFragment();
    }

    @Override
    public void setPresenter(BaseFavoriteContract.Presenter presenter) {
        this.presenter = (FavoriteArticlesContract.Presenter) presenter;
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

    @Override
    protected int getLayoutResId() {
        return R.layout.fragment_favorite_articles;
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.title_favorite_articles);
    }

    @Override
    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
    }

    @Override
    protected void initRecyclerViewAdapter() {
        savedArticlesAdapter = new SavedArticlesAdapter(this);
        recyclerView.setAdapter(savedArticlesAdapter);
    }

    @Override
    public void setCanClickOnRecyclerViewItems(boolean canClick) {
        if (savedArticlesAdapter != null) {
            savedArticlesAdapter.setCanClick(canClick);
        }
    }

    @Override
    public void clearRecyclerView() {
        savedArticlesAdapter.setData(new ArrayList<>());
    }

    @Override
    public void updateRecyclerView(List<ArticlePersist> result) {
        savedArticlesAdapter.setData(result);
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
    public void onRecyclerViewItemClick(ArticlePersist item) {
        presenter.processArticleClick(item);
    }
}
