package com.teoryul.newsly.adapter.utils;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public final class PicassoImageLoader {

    private static final int IMAGE_WIDTH = 1600;
    private static final int IMAGE_HEIGHT = 1200;

    /**
     * If the api img url is null or empty directly sets the view visibility to GONE.
     * Otherwise tries loading the image from cache first.
     * Then tries loading it from the web.
     * If it fails it sets the views's visibility to GONE.
     *
     * @param imgView
     * @param imgUrl
     */
    public static void setViewHolderImageView(final ImageView imgView, final String imgUrl) {
        if (TextUtils.isEmpty(imgUrl)) {
            imgView.setVisibility(View.GONE);
            return;
        }

        Picasso.get()
                .load(imgUrl)
                .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                .onlyScaleDown()
                .centerCrop()
                .networkPolicy(NetworkPolicy.OFFLINE)
                .into(imgView, new Callback() {

                    @Override
                    public void onSuccess() {
                        imgView.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onError(Exception e) {
                        Picasso.get()
                                .load(imgUrl)
                                .resize(IMAGE_WIDTH, IMAGE_HEIGHT)
                                .onlyScaleDown()
                                .centerCrop()
                                .into(imgView, new Callback() {

                                    @Override
                                    public void onSuccess() {
                                        imgView.setVisibility(View.VISIBLE);
                                    }

                                    @Override
                                    public void onError(Exception e) {
                                        imgView.setVisibility(View.GONE);
                                    }
                                });
                    }
                });
    }
}
