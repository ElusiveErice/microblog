package com.csu.microblog.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.csu.microblog.R;
import com.csu.microblog.fragments.MessageFragment;
import com.csu.microblog.fragments.MicroblogListFragment;
import com.csu.microblog.fragments.PersonFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomepageActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private Fragment onDisplayFragment;
    private Fragment microblogListFragment;
    private Fragment messageFragment;
    private Fragment personFragment;

    private BottomNavigationView mBottomNavigationView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        findView();
        initFragment();
        initListener();
    }

    private void findView() {
        mBottomNavigationView = findViewById(R.id.navigation);
    }

    private void initFragment() {
        fragmentManager = getSupportFragmentManager();
        microblogListFragment = new MicroblogListFragment();
        messageFragment = new MessageFragment();
        personFragment = new PersonFragment();
        onDisplayFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (onDisplayFragment == null) {
            onDisplayFragment = microblogListFragment;
        }

        //默认加载第一个首页Fragment
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, onDisplayFragment)
                .commit();
    }

    private void initListener() {
        mBottomNavigationView.setOnNavigationItemSelectedListener(menuItem -> {

            if (menuItem.getItemId() == R.id.item_microblog_list) {
                onDisplayFragment = microblogListFragment;
            } else if (menuItem.getItemId() == R.id.item_message) {
                onDisplayFragment = messageFragment;
            } else if (menuItem.getItemId() == R.id.item_personal_center) {
                onDisplayFragment = personFragment;
            } else {
                return false;
            }

            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, onDisplayFragment)
                    .commit();
            return true;
        });
    }
}
