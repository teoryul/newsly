package com.teoryul.newsly.ui.fragment.settings;

import android.os.Bundle;
import android.support.v7.preference.PreferenceFragmentCompat;

import com.teoryul.newsly.R;

public class SettingsFragment extends PreferenceFragmentCompat {

    public static SettingsFragment newInstance() {
        return new SettingsFragment();
    }

    @Override
    public void onCreatePreferences(Bundle bundle, String rootKey) {
        setPreferencesFromResource(R.xml.preferences, rootKey);
    }
}
