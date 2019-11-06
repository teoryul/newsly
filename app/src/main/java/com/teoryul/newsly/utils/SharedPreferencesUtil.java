package com.teoryul.newsly.utils;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.teoryul.newsly.App;
import com.teoryul.newsly.R;

import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class SharedPreferencesUtil {

    private SharedPreferences sharedPreferences;

    @Inject
    public SharedPreferencesUtil(SharedPreferences sharedPreferences) {
        this.sharedPreferences = sharedPreferences;
    }

    public boolean read(String key, boolean defaultVal) {
        return sharedPreferences.getBoolean(key, defaultVal);
    }

    public void write(String key, boolean val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, val);
        editor.apply();
    }

    public String read(String key, String defaultVal) {
        return sharedPreferences.getString(key, defaultVal);
    }

    public void write(String key, String val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, val);
        editor.apply();
    }

    public int read(String key, int defaultVal) {
        return sharedPreferences.getInt(key, defaultVal);
    }

    public void write(String key, int val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, val);
        editor.apply();
    }

    public Long read(String key, Long defaultVal) {
        return sharedPreferences.getLong(key, defaultVal);
    }

    public void write(String key, Long val) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putLong(key, val);
        editor.apply();
    }

    public void clear() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();
    }

    public String getCountrySetting() {
        return PreferenceManager
                .getDefaultSharedPreferences(App.getInstance())
                .getString(App.getInstance()
                        .getString(R.string.settings_list_country_key), AppConstants.SETTINGS_COUNTRY_DEFAULT_VALUE);
    }

    public String getLanguageSetting() {
        return PreferenceManager
                .getDefaultSharedPreferences(App.getInstance())
                .getString(App.getInstance()
                        .getString(R.string.settings_list_language_key), AppConstants.SETTINGS_LANGUAGE_DEFAULT_VALUE);
    }

    /**
     * Check if a certain amount of time has passed since the last successful API request for a particular news feed.
     * <p>
     * See {@link com.teoryul.newsly.utils.AppConstants#TEN_MIN_AS_MILLIS}.
     *
     * @param newsFeedTitle
     * @return True - if more than the set time has passed, False - otherwise.
     */
    public boolean hasTimePassedSincePreviousApiRequest(String newsFeedTitle) {
        String prevReqKey = TextUtils.concat(AppConstants.SHARED_PREFERENCES_KEY_PREVIOUS_API_REQUEST, newsFeedTitle).toString();
        long prevReqTimestamp = read(prevReqKey, AppConstants.PREVIOUS_API_REQUEST_DEFAULT_TIME);
        long currTimestamp = DateUtil.getCurrentTimeAsMillis();
        return currTimestamp > prevReqTimestamp + AppConstants.TEN_MIN_AS_MILLIS;
    }

    public void saveApiRequestTimestamp(String newsFeedTitle) {
        String reqKey = TextUtils.concat(AppConstants.SHARED_PREFERENCES_KEY_PREVIOUS_API_REQUEST, newsFeedTitle).toString();
        long reqTimestamp = DateUtil.getCurrentTimeAsMillis();
        write(reqKey, reqTimestamp);
    }

    public boolean hasNewsFeedCountrySettingChanged(String newsFeedTitle) {
        String defSharedPrefCountryVal = getCountrySetting();
        String key = TextUtils.concat(AppConstants.SHARED_PREFERENCES_KEY_SETTING_COUNTRY, newsFeedTitle).toString();
        String sharedPrefCountryVal = read(key, "");
        return !sharedPrefCountryVal.equalsIgnoreCase(defSharedPrefCountryVal);
    }

    public void saveNewsFeedCountrySetting(String newsFeedTitle) {
        String countryValue = getCountrySetting();
        String key = TextUtils.concat(AppConstants.SHARED_PREFERENCES_KEY_SETTING_COUNTRY, newsFeedTitle).toString();
        write(key, countryValue);
    }

    public boolean hasNewsFeedLanguageSettingChanged(String newsFeedTitle) {
        String defSharedPrefLanguageVal = getLanguageSetting();
        String key = TextUtils.concat(AppConstants.SHARED_PREFERENCES_KEY_SETTING_LANGUAGE, newsFeedTitle).toString();
        String sharedPrefLanguageVal = read(key, "");
        return !sharedPrefLanguageVal.equalsIgnoreCase(defSharedPrefLanguageVal);
    }
}
