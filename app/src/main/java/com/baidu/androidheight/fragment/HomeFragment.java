package com.baidu.androidheight.fragment;


import android.app.Notification;
import android.app.NotificationManager;
import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.baidu.androidheight.R;
import com.baidu.androidheight.adapter.HomeRlvAdapter;
import com.baidu.androidheight.bean.HomeBean;
import com.baidu.androidheight.model.HomeModelImpi;
import com.baidu.androidheight.persenter.HomePersenterImpi;
import com.baidu.androidheight.view.HomeView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements HomeView {


    private View view;
    private RecyclerView mRe;
    private ArrayList<HomeBean.DataBean.DatasBean> arr;
    private HomeRlvAdapter adapter;

    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        initView(view);
        initData();
        return view;
    }

    private void initData() {
        HomePersenterImpi impi = new HomePersenterImpi(new HomeModelImpi(), this);
        impi.getHomeData();//发送网路请求
    }

    private void initView(View view) {
        mRe = (RecyclerView) view.findViewById(R.id.re);
        mRe.setLayoutManager(new LinearLayoutManager(getContext()));
        arr = new ArrayList<>();
        adapter = new HomeRlvAdapter(arr, getContext());
        mRe.setAdapter(adapter);
    }

    @Override
    public void onSuccess(HomeBean homeBean) {
            if (homeBean != null){
                if (homeBean.getData().isOver() == false){
                    arr.addAll(homeBean.getData().getDatas());
                    adapter.setArr(arr);
                    adapter.notifyDataSetChanged();//刷新适配器
                }
            }
    }

    @Override
    public void onFile(String s) {
            if (s != null){
                Toast.makeText(getContext(),"信息"+s,Toast.LENGTH_SHORT).show();
            }

    }
}
