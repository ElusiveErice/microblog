package com.csu.microblog.activities;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.csu.microblog.R;

public class HomepageActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
    }
}
