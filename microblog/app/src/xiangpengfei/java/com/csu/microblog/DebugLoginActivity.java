package com.csu.microblog;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;

import com.csu.microblog.activities.HomepageActivity;
import com.csu.microblog.activities.RegisterActivity;
import com.csu.microblog.activities.SimpleActivity;
import com.csu.microblog.model.ResponseBody;
import com.csu.microblog.model.user.LoginData;
import com.csu.microblog.retrofit.RetrofitManager;
import com.csu.microblog.retrofit.api.UserService;
import com.csu.microblog.utils.SimpleViewBuilder;

import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DebugLoginActivity extends SimpleActivity {
    public static final String TAG = "DebugLoginActivity";

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
        mBTRegister = findViewById(R.id.bt_register);
        mBTLogin = findViewById(R.id.bt_login);
        mETAccount = findViewById(R.id.input_account);
        mETPassword = findViewById(R.id.input_password);
    }


    private void handleLogin(String account, String password) {
        Dialog loadingDialog = SimpleViewBuilder.newMessageDialog(this, "正在登录中", false);
        loadingDialog.show();
        RetrofitManager.getRetrofitManager()
                .getMicroblogRetrofit()
                .create(UserService.class)
                .login(Long.parseLong(account), password)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody<LoginData>>() {
                    @Override
                    public void onCompleted() {
                        loadingDialog.dismiss();
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, e.toString());

                        //关闭加载的dialog，并显示错误信息的dialog
                        loadingDialog.dismiss();
                        SimpleViewBuilder.newConfirmDialog(DebugLoginActivity.this, "网络异常").show();
                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        int code = responseBody.getCode();

                        if (code != 0) {
                            Log.e(TAG, responseBody.getMessage());
                        } else {
                            LoginData result = (LoginData) responseBody.getData();
                            if (result.isLogin()) {
                                Log.i(TAG, "登录成功,账号:" + result.getAccount() + "用户名:" + result.getUserName());
                                MainApplication mainApplication = (MainApplication) getApplication();
                                mainApplication.setAccount(result.getAccount());
                                mainApplication.setLogin(true);
                                Intent intent = new Intent(DebugLoginActivity.this, HomepageActivity.class);
                                startActivity(intent);
                                DebugLoginActivity.this.finish();
                            } else {
                                mETPassword.setText("");
                                mETPassword.setHint("");
                                SimpleViewBuilder.newConfirmDialog(DebugLoginActivity.this, result.getMessage()).show();
                                Log.e(TAG, "登录失败:" + result.getMessage());
                            }
                        }
                    }
                });
    }

    @Override
    protected void initListener() {
        mBTRegister.setOnClickListener(v -> {
            Intent intent = new Intent(DebugLoginActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
        mBTLogin.setOnClickListener(v -> {

            String account = mETAccount.getText().toString();
            String password = mETPassword.getText().toString();

            if (account.equals("")) {
                //如果不输入账号则直接以开放人员身份登录
                MainApplication mainApplication = (MainApplication) getApplication();
                mainApplication.setAccount(0);
                mainApplication.setLogin(true);
                Intent intent = new Intent(DebugLoginActivity.this, HomepageActivity.class);
                startActivity(intent);
                Log.i(TAG, "以开放人员身份登录");
                DebugLoginActivity.this.finish();
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
            Intent intent = new Intent(DebugLoginActivity.this, HomepageActivity.class);
            startActivity(intent);
            this.finish();
        }
    }
}
