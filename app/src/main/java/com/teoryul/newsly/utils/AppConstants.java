package com.teoryul.newsly.utils;

/**
 * Final class for storing app constants.
 */
public final class AppConstants {

    public static final int RECYCLER_VIEW_ON_BOTTOM_SCROLL_POSITION_OFFSET = 5;
    public static final int RECYCLER_VIEW_GRID_LAYOUT_SPAN_COUNT = 3;

    public static final long PREVIOUS_API_REQUEST_DEFAULT_TIME = 0L;
    public static final long TEN_MIN_AS_MILLIS = 600000L;
    public static final long THREE_DAYS_AS_MILLIS = 259200000L;

    public static final String SETTINGS_COUNTRY_DEFAULT_VALUE = "us";
    public static final String SETTINGS_LANGUAGE_DEFAULT_VALUE = "en";

    public static final String SHARED_PREFERENCES_KEY_SETTING_COUNTRY = "count";
    public static final String SHARED_PREFERENCES_KEY_SETTING_LANGUAGE = "lang";
    public static final String SHARED_PREFERENCES_KEY_PREVIOUS_API_REQUEST = "lar";

    public static final String INTENT_KEY_SEARCH_DIALOG_PHRASE = "searchPhrase";
    public static final String BUNDLE_KEY_NEWS_ARTICLE = "newsArticle";

    public static final String THROWABLE_MSG_INVALID_VIEW_HOLDER_VIEW_TYPE = "Invalid view holder view type";
}
