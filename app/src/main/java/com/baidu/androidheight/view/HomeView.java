package com.baidu.androidheight.view;

import com.baidu.androidheight.bean.HomeBean;

public interface HomeView {
    void onSuccess(HomeBean homeBean);

    void onFile(String s);
}
