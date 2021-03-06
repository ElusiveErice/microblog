package com.csu.microblog.retrofit.api;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

public interface MicroblogService {

    @GET("/hello")
    Observable<ResponseBody> hello();
}
