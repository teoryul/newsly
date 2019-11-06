package com.teoryul.newsly.ui.fragment.favoritetopicfeed;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.teoryul.newsly.R;
import com.teoryul.newsly.presenter.favoritetopicfeed.FavoriteTopicFeedContract;
import com.teoryul.newsly.ui.fragment.BaseNewsFeedFragment;

public class FavoriteTopicFeedFragment extends BaseNewsFeedFragment
        implements FavoriteTopicFeedContract.View {

    private Menu optionsMenu;

    public static FavoriteTopicFeedFragment newInstance() {
        return new FavoriteTopicFeedFragment();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        setActionBarBackButtonState(true);
    }

    private void setActionBarBackButtonState(boolean isEnabled) {
        if (getBaseActivity().getSupportActionBar() != null) {
            getBaseActivity().getSupportActionBar().setDisplayHomeAsUpEnabled(isEnabled);
        }
    }

    @Override
    public void setActivityTitle(String title) {
        getBaseActivity().setTitle(title);
    }

    @Override
    public void setMenuItemAddToFavoritesState(boolean isEnabled) {
        if (optionsMenu == null) {
            return;
        }

        optionsMenu.findItem(R.id.menu_item_favorite).setEnabled(isEnabled);
    }

    @Override
    public void setMenuIconAddToFavorites(boolean isFavorite) {
        if (optionsMenu == null) {
            return;
        }

        MenuItem item = optionsMenu.findItem(R.id.menu_item_favorite);
        if (item != null) {
            if (isFavorite) {
                item.setIcon(R.drawable.ic_star_white);
            } else {
                item.setIcon(R.drawable.ic_star_border_white);
            }
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_favorite_news_feed, menu);
        optionsMenu = menu;
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (R.id.menu_item_favorite == item.getItemId()) {
            return processFavoriteMenuItemClick();
        }

        if (android.R.id.home == item.getItemId()) {
            if (getFragmentManager() != null) {
                getFragmentManager().popBackStack();
                return true;
            }
            return false;
        }

        return super.onOptionsItemSelected(item);
    }

    private boolean processFavoriteMenuItemClick() {
        try {
            ((FavoriteTopicFeedContract.Presenter) presenter).addNewsFeedToFavorites();
        } catch (ClassCastException e) {
            Log.e("Presenter cast failed", e.getMessage());
            return false;
        }
        return true;
    }
}
