package com.teoryul.newsly.ui.fragment.favoritetopics;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;
import com.teoryul.newsly.adapter.FavoriteTopicsAdapter;
import com.teoryul.newsly.callback.OnRecyclerViewItemClickListener;
import com.teoryul.newsly.persistence.model.TopicPersist;
import com.teoryul.newsly.presenter.BaseFavoriteContract;
import com.teoryul.newsly.presenter.favoritetopicfeed.FavoriteTopicFeedPresenter;
import com.teoryul.newsly.presenter.favoritetopics.FavoriteTopicsContract;
import com.teoryul.newsly.ui.custom.GridSpacingItemDecoration;
import com.teoryul.newsly.ui.fragment.BaseFavoriteFragment;
import com.teoryul.newsly.ui.fragment.dialog.FragmentSearchDialog;
import com.teoryul.newsly.ui.fragment.favoritetopicfeed.FavoriteTopicFeedFragment;
import com.teoryul.newsly.utils.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class FavoriteTopicsFragment extends BaseFavoriteFragment
        implements FavoriteTopicsContract.View, View.OnClickListener,
        OnRecyclerViewItemClickListener<TopicPersist> {

    @BindView(R.id.fab)
    FloatingActionButton fabSearch;

    private FavoriteTopicsContract.Presenter presenter;
    private FavoriteTopicsAdapter favoriteTopicsAdapter;

    public static FavoriteTopicsFragment newInstance() {
        return new FavoriteTopicsFragment();
    }

    @Override
    public void setPresenter(BaseFavoriteContract.Presenter presenter) {
        this.presenter = (FavoriteTopicsContract.Presenter) presenter;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        fabSearch.setImageResource(R.drawable.ic_search_white);
        fabSearch.setOnClickListener(this);
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
        return R.layout.fragment_favorite_topics_sources;
    }

    @Override
    public String getFragmentTag() {
        return App.getInstance().getString(R.string.title_favorite_topics);
    }

    @Override
    protected void initRecyclerView() {
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(), AppConstants.RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(AppConstants.RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT,
                getResources().getDimensionPixelSize(R.dimen.favorite_topic_card_item_size)));
    }

    @Override
    protected void initRecyclerViewAdapter() {
        favoriteTopicsAdapter = new FavoriteTopicsAdapter(this);
        recyclerView.setAdapter(favoriteTopicsAdapter);
    }

    @Override
    public void onClick(View view) {
        if (R.id.fab == view.getId()) {
            showDialogFragment();
        }
    }

    private void showDialogFragment() {
        if (getFragmentManager() != null) {
            FragmentSearchDialog dialog = new FragmentSearchDialog();
            dialog.setTargetFragment(this, FragmentSearchDialog.REQUEST_CODE);
            dialog.show(getFragmentManager(), FragmentSearchDialog.TAG);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (FragmentSearchDialog.REQUEST_CODE == requestCode && Activity.RESULT_OK == resultCode) {
            String phrase = data.getStringExtra(AppConstants.INTENT_KEY_SEARCH_DIALOG_PHRASE);
            presenter.onSearch(phrase);
        }
    }

    @Override
    public void onRecyclerViewItemClick(TopicPersist item) {
        presenter.processTopicClick(item);
    }

    @Override
    public void setFabSearchTopicState(boolean isEnabled) {
        fabSearch.setEnabled(isEnabled);
    }

    @Override
    public void updateRecyclerView(List<TopicPersist> result) {
        favoriteTopicsAdapter.setData(result);
    }

    @Override
    public void loadTopicFeedFragment(TopicPersist topic) {
        FavoriteTopicFeedFragment fragment = FavoriteTopicFeedFragment.newInstance();
        new FavoriteTopicFeedPresenter(fragment, topic);
        replaceFragment(fragment, true, null);
    }

    @Override
    public void setCanClickOnRecyclerViewItems(boolean canClick) {
        if (favoriteTopicsAdapter != null) {
            favoriteTopicsAdapter.setCanClick(canClick);
        }
    }

    @Override
    public void clearRecyclerView() {
        favoriteTopicsAdapter.setData(new ArrayList<>());
    }
}
