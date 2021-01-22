package com.csu.microblog.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.csu.microblog.R;
import com.csu.microblog.fragments.MessageFragment;
import com.csu.microblog.fragments.MicroblogListFragment;
import com.csu.microblog.fragments.PersonFragment;

public class HomepageActivity extends SimpleActivity {

    private FragmentManager fragmentManager;
    private Fragment onDisplayFragment;
    private Fragment microblogListFragment;
    private Fragment messageFragment;
    private Fragment personFragment;

    private RadioButton mRbMircroblogList;
    private RadioButton mRbMessageList;
    private RadioButton mRbPerson;

    @Override
    protected int getContentView() {
        return R.layout.activity_homepage;
    }

    @Override
    protected void findView() {
        mRbMircroblogList = (RadioButton)findViewById(R.id.rb_list);
        mRbMessageList = (RadioButton)findViewById(R.id.rb_message);
        mRbPerson = (RadioButton)findViewById(R.id.rb_person);
    }

    protected void findFragment(){
        fragmentManager = getSupportFragmentManager();
        microblogListFragment = new MicroblogListFragment();
        messageFragment = new MessageFragment();
        personFragment = new PersonFragment();
        onDisplayFragment = fragmentManager.findFragmentById(R.id.fragment_container);
        if(onDisplayFragment == null){
            onDisplayFragment = microblogListFragment;
        }
    }

    protected void initFragment(){
        fragmentManager.beginTransaction()
                .add(R.id.fragment_container,onDisplayFragment)
                .commit();

        mRbMircroblogList.setOnClickListener(v -> {
            setFragment(microblogListFragment);
        });

        mRbMessageList.setOnClickListener(v->{
            setFragment(messageFragment);
        });

        mRbPerson.setOnClickListener(v -> {
            setFragment(personFragment);
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        findFragment();
        initFragment();
    }



    protected void setFragment(Fragment fragment){
        onDisplayFragment = fragment;
        fragmentManager.beginTransaction()
                .replace(R.id.fragment_container,onDisplayFragment)
                .commit();
    }
}
