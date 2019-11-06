package com.teoryul.newsly.callback;

import android.graphics.Bitmap;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * Custom Web View Client which tracks when a page has loaded and if any errors occurred.
 */
public class CustomWebViewClient extends WebViewClient {

    private final WebViewCallback callback;

    public CustomWebViewClient(WebViewCallback callback) {
        super();
        this.callback = callback;
    }

    @Override
    public void onPageStarted(WebView view, String url, Bitmap favicon) {
        view.clearHistory();
        view.clearCache(true);
    }

    @Override
    public void onPageFinished(WebView view, String url) {
        callback.onWebViewLoadSuccess();
    }
}
