package com.baidu.androidheight.ui;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.baidu.androidheight.R;
import com.baidu.androidheight.adapter.MainVpAdapter;
import com.baidu.androidheight.adapter.MainVpAdapterNoTitles;
import com.baidu.androidheight.fragment.DownFragment;
import com.baidu.androidheight.fragment.HomeFragment;

import java.util.ArrayList;

/*
 * *  author gme
 *    time  2019年4月26日18:33:01
 */
public class MainActivity extends AppCompatActivity implements TabLayout.BaseOnTabSelectedListener {

    private Toolbar tool;
    private ViewPager vp;
    private TabLayout tab;
    /**
     * 首页
     */
    private TextView txt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment();
    }

    private void initFragment() {
        HomeFragment homeFragment = new HomeFragment();
        DownFragment downFragment = new DownFragment();
        ArrayList<Fragment> fragments = new ArrayList<>();
        ArrayList<String> list = new ArrayList<>();
        list.add("首页");
        list.add("下载");
        fragments.add(homeFragment);
        fragments.add(downFragment);
        MainVpAdapter adapter = new MainVpAdapter(getSupportFragmentManager(), fragments, list);
         vp.setAdapter(adapter);
        tab.setupWithViewPager(vp);
        tab.getTabAt(0).setIcon(R.mipmap.godt);
        tab.getTabAt(1).setIcon(R.mipmap.godten);
        tab.setOnTabSelectedListener(this);
    }

    private void initView() {
        tool = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(tool);
        vp = (ViewPager) findViewById(R.id.vp);
        tab = (TabLayout) findViewById(R.id.tab);
        txt = (TextView) findViewById(R.id.txt);
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        int position = tab.getPosition();
        if (position == 0){
            txt.setText("首页");
        }
        if (position == 1){
            txt.setText("下载");
        }

    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
