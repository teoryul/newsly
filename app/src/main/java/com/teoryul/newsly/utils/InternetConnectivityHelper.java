package com.teoryul.newsly.utils;

import android.content.Context;
import android.net.ConnectivityManager;

import com.teoryul.newsly.App;

public final class InternetConnectivityHelper {

    public static boolean isInternetConnectionAvailable() {
        ConnectivityManager con_manager = (ConnectivityManager)
                App.getInstance().getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        return con_manager.getActiveNetworkInfo() != null
                && con_manager.getActiveNetworkInfo().isAvailable()
                && con_manager.getActiveNetworkInfo().isConnected();
    }
}
