package com.csu.microblog.activities;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;

import com.csu.microblog.R;
import com.csu.microblog.retrofit.RetrofitManager;
import com.csu.microblog.retrofit.UrlInfo;

import java.io.IOException;

import okhttp3.ResponseBody;
import rx.Observer;

public class LoginActivity extends Activity {

    private Button mBTRegister;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mBTRegister = (Button)findViewById(R.id.bt_register);
        mBTRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
//                startActivity(intent);

                new RetrofitManager(UrlInfo.TEST).test(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        try {
                            Log.i("Test", responseBody.string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        });
    }
}
