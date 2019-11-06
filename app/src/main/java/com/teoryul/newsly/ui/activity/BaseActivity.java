package com.teoryul.newsly.ui.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import com.teoryul.newsly.R;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutResId());
    }

    protected int getLayoutResId() {
        return R.layout.activity_main;
    }

    /**
     * @param fragment
     * @param shouldAddToBackStack
     * @param name                 Name for the back stack state
     */
    public void replaceFragment(Fragment fragment, boolean shouldAddToBackStack, String name) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment);

        if (shouldAddToBackStack) {
            transaction.addToBackStack(name);
        }

        transaction.commit();
    }

    /**
     * @param fragment
     * @param fragmentTag
     * @param shouldAddToBackStack
     * @param name                 Name for the back stack state
     */
    public void replaceFragment(Fragment fragment, String fragmentTag, boolean shouldAddToBackStack, String name) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frame_container, fragment, fragmentTag);

        if (shouldAddToBackStack) {
            transaction.addToBackStack(name);
        }

        transaction.commit();
    }

    /**
     * @param fragment
     * @param shouldAddToBackStack
     * @param name                 Name for the back stack state
     */
    public void addFragment(Fragment fragment, boolean shouldAddToBackStack, String name) {
        FragmentTransaction transaction =
                getSupportFragmentManager()
                        .beginTransaction()
                        .add(R.id.frame_container, fragment);

        if (shouldAddToBackStack) {
            transaction.addToBackStack(name);
        }

        transaction.commit();
    }
}
