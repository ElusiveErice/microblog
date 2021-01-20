package com.csu.microblog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.csu.microblog.MainApplication;
import com.csu.microblog.R;
import com.csu.microblog.model.user.login.LoginRequestBody;
import com.csu.microblog.model.user.login.LoginResponseBody;
import com.csu.microblog.model.user.login.LoginData;
import com.csu.microblog.retrofit.RetrofitManager;
import com.csu.microblog.retrofit.api.MicroblogService;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends SimpleActivity {

    public static final String TAG = "LoginActivity";

    private Button mBTRegister;
    private Button mBTLogin;
    private EditText mETAccount;
    private EditText mETPassword;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void findView() {
        mBTRegister = (Button) findViewById(R.id.bt_register);
        mBTLogin = (Button) findViewById(R.id.bt_login);
        mETAccount = (EditText)findViewById(R.id.input_account);
        mETPassword = (EditText)findViewById(R.id.input_password);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBTRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        mBTLogin.setOnClickListener(v -> {
            String account = mETAccount.getText().toString();
            String password = mETPassword.getText().toString();
            LoginRequestBody requestBody = new LoginRequestBody();
            if(account==null||account.equals("")){
                requestBody.setAccount(0);
            }else{
                requestBody.setAccount(Long.parseLong(account));
            }
            requestBody.setPassword(password);

            RetrofitManager.getRetrofitManager()
                    .getMicroblogRetrofit()
                    .create(MicroblogService.class)
                    .login(requestBody)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<LoginResponseBody>() {
                        @Override
                        public void onCompleted() {

                        }

                        @Override
                        public void onError(Throwable e) {
                            Log.e(TAG, e.toString());
                        }

                        @Override
                        public void onNext(LoginResponseBody loginResponseBody) {
                            int code = loginResponseBody.getCode();
                            if(loginResponseBody==null){
                                Log.i(TAG,"Error");
                            }

                            if(code != 0){
                                Log.e(TAG, loginResponseBody.getMessage());
                            }else{
                                LoginData result = loginResponseBody.getData();
                                if(result.isResult()){
                                    Log.i(TAG,"登录成功");
                                    MainApplication mainApplication = (MainApplication)getApplication();
                                    mainApplication.setAccount(result.getUserInfo().getAccount());
                                    mainApplication.setLogin(true);
                                    Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                                    startActivity(intent);
                                }else {
                                    Log.e(TAG, result.getUserInfo().getMessage());
                                }
                            }
                        }
                    });
        });
    }
}
