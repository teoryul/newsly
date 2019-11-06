package com.teoryul.newsly.presenter.favoritetopics;

import com.teoryul.newsly.persistence.model.TopicPersist;
import com.teoryul.newsly.presenter.BaseFavoriteContract;

import java.util.List;

public interface FavoriteTopicsContract extends BaseFavoriteContract {

    interface View extends BaseFavoriteContract.View {

        void setFabSearchTopicState(boolean isEnabled);

        void updateRecyclerView(List<TopicPersist> result);

        void loadTopicFeedFragment(TopicPersist topic);
    }

    interface Presenter extends BaseFavoriteContract.Presenter {

        void onSearch(String phrase);

        void processTopicClick(TopicPersist topic);
    }
}
