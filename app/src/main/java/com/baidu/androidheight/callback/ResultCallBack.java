package com.baidu.androidheight.callback;

import com.baidu.androidheight.bean.HomeBean;

public interface ResultCallBack {
    void onSuccess(HomeBean homeBean);
    void onFile(String s);
}
