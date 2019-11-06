package com.teoryul.newsly.adapter.item;

import com.teoryul.newsly.adapter.viewholder.NewsFeedViewHolderTypes;

/**
 * Sub class for handling an "end of news feed" adapter item in a recycler view.
 * See {@link com.teoryul.newsly.adapter.viewholder.NewsFeedEndHolder}.
 */
public class NewsFeedEndItem extends NewsFeedMultiItem {

    @Override
    public int getViewType() {
        return NewsFeedViewHolderTypes.END;
    }

    @Override
    public int getUniqueId() {
        return NewsFeedViewHolderTypes.END;
    }
}
