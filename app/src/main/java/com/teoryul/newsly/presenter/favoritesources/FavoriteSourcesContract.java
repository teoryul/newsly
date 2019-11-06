package com.teoryul.newsly.presenter.favoritesources;

import com.teoryul.newsly.persistence.model.SourcePersist;
import com.teoryul.newsly.presenter.BaseFavoriteContract;

import java.util.List;

public interface FavoriteSourcesContract extends BaseFavoriteContract {

    interface View extends BaseFavoriteContract.View {

        void setFabAddSourceState(boolean isEnabled);

        void updateRecyclerView(List<SourcePersist> result);

        void showMultiChoiceDialog(String[] sources, boolean[] checkedItems);

        void loadSourceFeedFragment(SourcePersist source);
    }

    interface Presenter extends BaseFavoriteContract.Presenter {

        void processFabAddSourceClick();

        void onDialogItemClick(int position);

        void onDialogPositiveButtonClick();

        void onDialogNegativeButtonClick();

        void onDialogNeutralButtonClick();

        void processSourceClick(SourcePersist source);
    }
}
