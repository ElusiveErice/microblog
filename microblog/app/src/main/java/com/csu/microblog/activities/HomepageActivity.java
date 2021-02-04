package com.csu.microblog.activities;

import android.os.Bundle;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;

import com.csu.microblog.R;
import com.csu.microblog.fragments.MessageFragment;
import com.csu.microblog.fragments.MicroblogListFragment;
import com.csu.microblog.fragments.PersonFragment;

public class HomepageActivity extends FragmentActivity {

    private FragmentManager fragmentManager;
    private Fragment onDisplayFragment;
    private Fragment microblogListFragment;
    private Fragment messageFragment;
    private Fragment personFragment;

    private RadioButton mRBMicroblogList;
    private RadioButton mRBMessageList;
    private RadioButton mRBPerson;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        findView();
        findFragment();
        initFragment();
    }

    protected void findView() {
        mRBMicroblogList = (RadioButton) findViewById(R.id.rb_list);
        mRBMessageList = (RadioButton) findViewById(R.id.rb_message);
        mRBPerson = (RadioButton) findViewById(R.id.rb_person);
    }

    protected void findFragment() {
        fragmentManager = getSupportFragmentManager();
        microblogListFragment = new MicroblogListFragment();
        messageFragment = new MessageFragment();
        personFragment = new PersonFragment();
        onDisplayFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if (onDisplayFragment == null) {
            onDisplayFragment = microblogListFragment;
        }
    }

    protected void initFragment() {
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container, onDisplayFragment)
                .commit();

        mRBMicroblogList.setOnClickListener(v -> setFragment(microblogListFragment));

        mRBMessageList.setOnClickListener(v -> setFragment(messageFragment));

        mRBPerson.setOnClickListener(v -> setFragment(personFragment));
    }


    protected void setFragment(Fragment fragment) {
        onDisplayFragment = fragment;
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, onDisplayFragment)
                .commit();
    }
}
