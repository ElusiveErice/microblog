package com.csu.microblog.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public abstract class SimpleFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(getLayoutRes(), container, false);
        findView(v);
        initView();
        initListener();
        return v;
    }

    protected abstract int getLayoutRes();

    protected void findView(View v) {
    }

    protected void initView(){}
    protected void initListener() {
    }
}
