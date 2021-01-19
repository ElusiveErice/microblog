package com.csu.microblog.retrofit.api;

import com.csu.microblog.model.user.login.LoginRequestBody;
import com.csu.microblog.model.user.login.LoginResponseBody;

import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import rx.Observable;

public interface MicroblogService {

    @POST("/login")
    Observable<LoginResponseBody> login(@Body LoginRequestBody loginRequestBody);

    @GET("/hello")
    Observable<ResponseBody> hello();
}
