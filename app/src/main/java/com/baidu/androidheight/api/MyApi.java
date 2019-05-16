package com.baidu.androidheight.api;

import com.baidu.androidheight.bean.HomeBean;

import io.reactivex.Observable;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;

public interface MyApi {
    String url = "https://www.wanandroid.com/";

    String downUrl = "http://cdn.banmi.com/";

    @GET("project/list/1/json?cid=294")
    Observable<HomeBean> getData();

    @GET("banmiapp/apk/banmi_330.apk")
    Observable<ResponseBody> downApk();


}
