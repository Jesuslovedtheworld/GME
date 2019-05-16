package com.baidu.androidheight.model;

import com.baidu.androidheight.api.MyApi;
import com.baidu.androidheight.bean.HomeBean;
import com.baidu.androidheight.callback.ResultCallBack;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class HomeModelImpi implements HomeModel {
    @Override
    public void getData(final ResultCallBack resultCallBack) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.url)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyApi api = retrofit.create(MyApi.class);
        Observable<HomeBean> data = api.getData();
        data.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<HomeBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(HomeBean homeBean) {
                            if (homeBean != null){
                                resultCallBack.onSuccess(homeBean);
                            }else {
                                resultCallBack.onFile("请求失败");
                            }
                    }

                    @Override
                    public void onError(Throwable e) {
                        resultCallBack.onFile(e.getMessage());
                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
