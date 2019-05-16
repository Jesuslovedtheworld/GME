package com.baidu.androidheight.persenter;

import com.baidu.androidheight.bean.HomeBean;
import com.baidu.androidheight.callback.ResultCallBack;
import com.baidu.androidheight.model.HomeModel;
import com.baidu.androidheight.view.HomeView;

public class HomePersenterImpi implements HomePersenter, ResultCallBack {
    private HomeModel model;
    private HomeView homeView;

    public HomePersenterImpi(HomeModel model, HomeView homeView) {
        this.model = model;
        this.homeView = homeView;
    }

    @Override
    public void getHomeData() {
        if (model != null){
            model.getData(this);//实现接口
        }
    }

    @Override
    public void onSuccess(HomeBean homeBean) {
        if (homeView != null){
            homeView.onSuccess(homeBean);
        }
    }

    @Override
    public void onFile(String s) {
            if (homeView != null){
                homeView.onFile(s);
            }
    }
}
