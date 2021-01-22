package com.csu.microblog;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

public class MainActivity extends Activity {

    private LinearLayout page1;
    private RelativeLayout page2;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        page1 = (LinearLayout)findViewById(R.id.page1);
        page2 = (RelativeLayout) findViewById(R.id.page2);
        button = (Button)findViewById(R.id.bt_button);
        button.setOnClickListener(v -> {
            page2.setVisibility(View.VISIBLE);
            page2.setOnClickListener(null);
        });
    }

}