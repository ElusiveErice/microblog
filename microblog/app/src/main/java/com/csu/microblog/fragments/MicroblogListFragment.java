package com.csu.microblog.fragments;


import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.csu.microblog.R;
import com.csu.microblog.model.microblog.Microblog;
import com.csu.microblog.model.microblog.MicroblogLab;

import java.util.ArrayList;
import java.util.List;

public class MicroblogListFragment extends SimpleFragment {

    private static final String TAG = "MicroblogListFragment";

    private ImageButton mIBTRefresh;
    private ImageButton mIBTAdd;

    private RecyclerView mRVMicroblogs;

    @Override
    protected int getLayoutRes() {
        return R.layout.fragment_microblog_list;
    }


    @Override
    protected void findView(View v) {
        mIBTRefresh = (ImageButton) v.findViewById(R.id.ibt_refresh);
        mIBTAdd = (ImageButton) v.findViewById(R.id.ibt_add);
        mRVMicroblogs = (RecyclerView) v.findViewById(R.id.rv_microblogs);
    }

    @Override
    protected void initView() {
        mRVMicroblogs.setLayoutManager(new LinearLayoutManager(getActivity()));
        mRVMicroblogs.setAdapter(new MicroblogAdapter(MicroblogLab.get(getActivity()).getMicroblogs()));
    }

    @Override
    protected void initListener() {
        mIBTRefresh.setOnClickListener(v -> Log.i(TAG, "更新微博列表"));
        mIBTAdd.setOnClickListener(v -> Log.i(TAG, "新增微博"));
    }

    /**
     * 控制微博item视图
     */
    private static class MicroblogHolder extends RecyclerView.ViewHolder {

        private Microblog mMicroblog;

        public MicroblogHolder(@NonNull View itemView) {
            super(itemView);
        }

        public void bind(Microblog microblog) {
            mMicroblog = microblog;
        }
    }

    /**
     * 微博列表的适配器
     */
    private class MicroblogAdapter extends RecyclerView.Adapter<MicroblogHolder> {

        private final List<Microblog> mMicroblogs;

        public MicroblogAdapter(List<Microblog> microblogs) {
            mMicroblogs = microblogs;
        }

        @NonNull
        @Override
        public MicroblogHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater.inflate(R.layout.item_microblog, parent, false);
            return new MicroblogHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull MicroblogHolder holder, int position) {
            Microblog microblog = mMicroblogs.get(position);
            holder.bind(microblog);
        }

        @Override
        public int getItemCount() {
            return mMicroblogs.size();
        }
    }
}
