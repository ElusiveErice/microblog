package com.csu.microblog.fragments;


import android.view.View;
import android.widget.ImageButton;

import com.csu.microblog.R;

public class MicroblogListFragment extends SimpleFragment {

    private ImageButton mIBTRefresh;
    private ImageButton mIBTAdd;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_microblog_list;
    }


    @Override
    protected void findView(View v) {
        mIBTRefresh = (ImageButton) v.findViewById(R.id.ibt_refresh);
        mIBTAdd = (ImageButton) v.findViewById(R.id.ibt_add);
    }
}
