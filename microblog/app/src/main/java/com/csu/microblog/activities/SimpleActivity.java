package com.csu.microblog.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.WindowManager;

import androidx.annotation.Nullable;

public abstract class SimpleActivity extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        setContentView(getContentView());
        findView();
        initListener();
    }

    protected void initListener() {
    }

    protected abstract int getContentView();

    protected void findView() {
    }

}
