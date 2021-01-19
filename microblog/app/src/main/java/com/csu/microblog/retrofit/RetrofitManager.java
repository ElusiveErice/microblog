package com.csu.microblog.retrofit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager {
    public static final String LocalIP = "http://10.0.2.2:8080/";
    public static final String TestIP = "http://192.168.1.105:8080/";
    public static final String IP = TestIP;
    public static final String MICROBLOG = IP;

    private static RetrofitManager retrofitManager;

    private RetrofitManager(){

    }

    public static RetrofitManager getRetrofitManager(){
        if(retrofitManager == null){
            synchronized (RetrofitManager.class){
                if(retrofitManager == null){
                    retrofitManager = new RetrofitManager();
                }
            }
        }
        return retrofitManager;
    }

    public Retrofit getMicroblogRetrofit(){
        Retrofit retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(MICROBLOG)
                .build();
        return retrofit;
    }

//    public void test(Observer<ResponseBody> observer) {
//        Observable<ResponseBody> observable = retrofit.create(TestService.class).test();
//        observable.subscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(observer);
//    }

}
