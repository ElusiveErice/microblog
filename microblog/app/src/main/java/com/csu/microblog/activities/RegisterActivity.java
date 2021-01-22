package com.csu.microblog.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.csu.microblog.R;
import com.csu.microblog.model.ResponseBody;
import com.csu.microblog.model.user.RegisterData;
import com.csu.microblog.retrofit.RetrofitManager;
import com.csu.microblog.retrofit.api.UserService;
import com.csu.microblog.utils.FileUtils;
import com.csu.microblog.utils.PictureUtils;
import com.csu.microblog.utils.ToastUtil;
import com.csu.microblog.views.CircleImageView;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import rx.Observer;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class RegisterActivity extends SimpleActivity {

    public static final String TAG = "RegisterActivity";

    private static final int REQUEST_CAMERA_PICTURE = 1;
    private static final int REQUEST_PICK_PICTURE = 2;

    private Button mBTRegister;
    private CircleImageView mCIVPortrait;
    private EditText mETUserName;
    private EditText mETPassword;
    private EditText mETConfirmPassword;
    private RadioGroup mRGSex;

    private AlertDialog mADCamera;
    private Button mBTTakePhotos;
    private Button mBTPhotoAlbum;

    @Override
    protected int getContentView() {
        return R.layout.activity_register;
    }

    @Override
    protected void findView() {

        mBTRegister = (Button) findViewById(R.id.bt_register);
        mCIVPortrait = (CircleImageView) findViewById(R.id.head_portrait);
        mETPassword = (EditText) findViewById(R.id.et_password);
        mETConfirmPassword = (EditText) findViewById(R.id.et_confirm_password);
        mETUserName = (EditText) findViewById(R.id.et_user_name);
        mRGSex = (RadioGroup) findViewById(R.id.rg_sex);

        final LayoutInflater inflater = this.getLayoutInflater();
        View cameraView = inflater.inflate(R.layout.dialog_camera, null, false);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setView(cameraView);
        mADCamera = builder.create();
        mBTTakePhotos = (Button) cameraView.findViewById(R.id.bt_take_photos);
        mBTPhotoAlbum = (Button) cameraView.findViewById(R.id.bt_photo_album);
    }

    @Override
    protected void initListener() {
        mBTRegister.setOnClickListener(v -> {
            String userName;
            String password;
            String confirmPassword;
            int sex;
            File portraitFile;

            userName = mETUserName.getText().toString();
            if (userName.equals("")) {
                ToastUtil.newToast(RegisterActivity.this, "请填写用户名", Toast.LENGTH_LONG);
                return;
            }
            password = mETPassword.getText().toString();
            if (password.equals("")) {
                ToastUtil.newToast(RegisterActivity.this, "请设置密码", Toast.LENGTH_LONG);
                return;
            }
            confirmPassword = mETConfirmPassword.getText().toString();
            if (!password.equals(confirmPassword)) {
                ToastUtil.newToast(RegisterActivity.this, "密码确认错误", Toast.LENGTH_LONG);
                return;
            }
            if (mRGSex.getCheckedRadioButtonId() == R.id.rb_male) {
                sex = getResources().getInteger(R.integer.male);
            } else if (mRGSex.getCheckedRadioButtonId() == R.id.rb_female) {
                sex = getResources().getInteger(R.integer.female);
            } else {
                ToastUtil.newToast(RegisterActivity.this, "请选择性别", Toast.LENGTH_LONG);
                return;
            }

            try {
                mCIVPortrait.setDrawingCacheEnabled(true);
                Bitmap bitmap = mCIVPortrait.getDrawingCache();
                portraitFile = FileUtils.bitmapToFile(RegisterActivity.this,
                        bitmap, UUID.randomUUID().toString() + "_portrait.png");
                mCIVPortrait.setDrawingCacheEnabled(false);
            } catch (IOException e) {
                ToastUtil.newToast(RegisterActivity.this, "头像读写错误", Toast.LENGTH_LONG);
                Log.e(TAG, e.getLocalizedMessage());
                return;
            }

            handleLoginResult(userName, password, sex, portraitFile);
        });
        mCIVPortrait.setOnClickListener(v -> mADCamera.show());
        mBTTakePhotos.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, REQUEST_CAMERA_PICTURE);
            mADCamera.dismiss();
        });
        mBTPhotoAlbum.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_PICK, null);
            intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
            startActivityForResult(intent, REQUEST_PICK_PICTURE);
            mADCamera.dismiss();
        });
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            Log.i(TAG, "Activity返回参数异常,resultCode:" + resultCode);
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CAMERA_PICTURE) {
            Bundle bundle = data.getExtras();
            Bitmap bitmap = (Bitmap) bundle.get("data");
            mCIVPortrait.setImageBitmap(bitmap);
        }else if(requestCode == REQUEST_PICK_PICTURE){
            Uri uri = data.getData();
            try {
                mCIVPortrait.setImageBitmap(PictureUtils.getBitmapFormUri(uri, this));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handleLoginResult(String userName, String password, int sex, File portraitFile) {
        RequestBody userNameBody = RequestBody.create(MediaType.parse("multipart/form-data"), userName);
        RequestBody passwordBody = RequestBody.create(MediaType.parse("multipart/form-data"), password);
        RequestBody sexBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(sex));
        RequestBody requestFile = RequestBody.create(MediaType.parse("applicaiton/otcet-stream"), portraitFile);
        MultipartBody.Part portraitBody =
                MultipartBody.Part.createFormData("portrait", portraitFile.getName(), requestFile);

        RetrofitManager.getRetrofitManager().getMicroblogRetrofit().create(UserService.class)
                .register(userNameBody, passwordBody, sexBody, portraitBody)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        int code = responseBody.getCode();
                        if (responseBody == null) {
                            Log.i(TAG, "Error");
                        }
                        Log.i(TAG, "code:" + responseBody.getData());
                        if (code != 0) {
                            Log.e(TAG, responseBody.getMessage());
                        } else {
                            RegisterData data = (RegisterData) responseBody.getData();
                            if (data.isRegister()) {
                                Log.i(TAG, "注册成功,账号:" + data.getAccount());
                                Intent intent =
                                        RegisterSuccessActivity.newIntent(RegisterActivity.this, data.getAccount());
                                startActivity(intent);
                                RegisterActivity.this.finish();
                            } else {
                                Log.e(TAG, data.getMessage());
                            }
                        }
                    }
                });

    }
}
