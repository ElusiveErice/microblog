package com.csu.microblog.activities;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.csu.microblog.R;
import com.csu.microblog.utils.ToastUtil;

public class RegisterSuccessActivity extends SimpleActivity {

    private static final String REGISTER_ACCOUNT = "com.csu.microblog.activities.RegisterSuccessActivity.register_login";

    public static Intent newIntent(Context context, long account) {
        Intent intent = new Intent(context, RegisterSuccessActivity.class);
        intent.putExtra(REGISTER_ACCOUNT, account);
        return intent;
    }

    private long account;
    private TextView mTVAccount;
    private Button mBTCopy;
    private Button mBTReturnLogin;

    @Override
    protected int getContentView() {
        return R.layout.activity_register_success;
    }

    @Override
    protected void findView() {
        mTVAccount = (TextView) findViewById(R.id.tv_account);
        mBTCopy = (Button) findViewById(R.id.bt_copy);
        mBTReturnLogin = (Button) findViewById(R.id.bt_return_login);
    }

    @Override
    protected void initListener() {
        mBTCopy.setOnClickListener(v -> {
            ClipboardManager cm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
            ClipData mClipData = ClipData.newPlainText("Label", String.valueOf(account));
            cm.setPrimaryClip(mClipData);
            ToastUtil.newToast(RegisterSuccessActivity.this, "复制" + account, Toast.LENGTH_SHORT);
        });
        mBTReturnLogin.setOnClickListener(v -> {
            RegisterSuccessActivity.this.finish();
        });
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        long account = getIntent().getLongExtra(REGISTER_ACCOUNT, 0);
        mTVAccount.setText(String.valueOf(account));
    }
}
