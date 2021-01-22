package com.csu.microblog.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;

import com.csu.microblog.MainApplication;
import com.csu.microblog.R;
import com.csu.microblog.model.ResponseBody;
import com.csu.microblog.model.user.LoginData;
import com.csu.microblog.retrofit.RetrofitManager;
import com.csu.microblog.retrofit.api.UserService;
import com.csu.microblog.utils.SimpleViewBuilder;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class LoginActivity extends SimpleActivity {

    public static final String TAG = "LoginActivity";

    private Button mBTRegister;
    private Button mBTLogin;
    private EditText mETAccount;
    private EditText mETPassword;
    private RelativeLayout mRLLoading;

    @Override
    protected int getContentView() {
        return R.layout.activity_login;
    }

    @Override
    protected void findView() {
        mBTRegister = (Button) findViewById(R.id.bt_register);
        mBTLogin = (Button) findViewById(R.id.bt_login);
        mETAccount = (EditText) findViewById(R.id.input_account);
        mETPassword = (EditText) findViewById(R.id.input_password);
        mRLLoading = (RelativeLayout) findViewById(R.id.rl_loading);
    }


    private void handleLogin(String account, String password) {
        mRLLoading.setVisibility(View.VISIBLE);
        RetrofitManager.getRetrofitManager()
                .getMicroblogRetrofit()
                .create(UserService.class)
                .login(Long.parseLong(account), password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {
                        mRLLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());
                        mRLLoading.setVisibility(View.GONE);
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        int code = responseBody.getCode();
                        if (responseBody == null) {
                            Log.i(TAG, "Error");
                        }

                        if (code != 0) {
                            Log.e(TAG, responseBody.getMessage());
                        } else {
                            LoginData result = (LoginData) responseBody.getData();
                            if (result.isLogin()) {
                                Log.i(TAG, "登录成功,账号:" + result.getAccount() + "用户名:" + result.getUserName());
                                MainApplication mainApplication = (MainApplication) getApplication();
                                mainApplication.setAccount(result.getAccount());
                                mainApplication.setLogin(true);
                                Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
                                startActivity(intent);
                                LoginActivity.this.finish();
                            } else {
                                mETPassword.setText("");
                                mETPassword.setHint("");
                                SimpleViewBuilder.newMessageDialog(LoginActivity.this, result.getMessage()).show();
                                Log.e(TAG, "登录失败:" + result.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    protected void initListener() {
        mRLLoading.setOnClickListener(null);
        mBTRegister.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        mBTLogin.setOnClickListener(v -> {

            String account = mETAccount.getText().toString();
            String password = mETPassword.getText().toString();
            if (account.equals("")) {
                mETAccount.setHint(R.string.error_account_null);
                mETAccount.setHintTextColor(getResources().getColor(R.color.red));
            } else if (password.equals("")) {
                mETPassword.setHint(R.string.error_password_null);
                mETPassword.setHintTextColor(getResources().getColor(R.color.red));
            } else {
                handleLogin(account, password);
            }
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        MainApplication app = (MainApplication) getApplication();
        if (app.isLogin()) {
            Intent intent = new Intent(LoginActivity.this, HomepageActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}


//TODO
//点击登录后改用dialog来做loading
//后端返回登录失败改用Dialog来通知