package com.teoryul.newsly.ui.activity.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.teoryul.newsly.R;
import com.teoryul.newsly.ui.activity.home.HomeActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);

        super.onCreate(savedInstanceState);
        startActivity(new Intent(SplashActivity.this, HomeActivity.class));
        finish();
    }
}
