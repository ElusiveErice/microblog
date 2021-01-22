package com.csu.microblog.retrofit.api;

import com.csu.microblog.model.ResponseBody;
import com.csu.microblog.model.user.LoginData;
import com.csu.microblog.model.user.RegisterData;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import rx.Observable;

public interface UserService {

    @FormUrlEncoded
    @POST("user/login")
    Observable<ResponseBody<LoginData>> login(@Field("account")long account, @Field("password") String password);

    @Multipart
    @POST("user/register")
    Observable<ResponseBody<RegisterData>> register(@Part("userName") RequestBody userName,
                                                    @Part("password") RequestBody password,
                                                    @Part("sex") RequestBody sex,
                                                    @Part MultipartBody.Part portrait);
}
