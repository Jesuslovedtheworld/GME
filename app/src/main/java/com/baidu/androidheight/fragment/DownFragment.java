package com.baidu.androidheight.fragment;


import android.Manifest;
import android.app.Notification;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.androidheight.R;
import com.baidu.androidheight.api.MyApi;
import com.baidu.androidheight.serivice.DowmService;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.ResponseBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

/**
 * A simple {@link Fragment} subclass.
 */
public class DownFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "DownFragment";

    private View view;
    /**
     * 当前进度
     */
    /**
     * 开始下载
     */
    private File sd;
    private SeekBar mSeek;
    /**
     * 当前进度
     */
    private TextView mTxt;
    /**
     * 开始下载
     */
    private TextView mTxtDown;

    public DownFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_two, container, false);
        initView(view);

        return view;
    }





    private void initView(View view) {
        mSeek = (SeekBar) view.findViewById(R.id.seek);
        mTxt = (TextView) view.findViewById(R.id.txt);
        mTxtDown = (TextView) view.findViewById(R.id.txt_down);
        mTxtDown.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            default:
                break;
            case R.id.txt_down:
                //notice();
                down();
                Log.e(TAG, "onClick: 方法走了");
                break;
        }
    }


    private void notice() {
      /*  NotificationManager manager = (NotificationManager) getActivity().getSystemService(Context.NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(getContext())
                .setSmallIcon(R.mipmap.godten)
                .setContentText("下载完成")
                .setContentTitle("标题")
                .build();
        manager.notify(1008,notification);
        Intent intent = new Intent(getContext(), DowmService.class);
        getActivity().startService(intent);*/
       /* getContext().bindService(intent,ServiceConnection,1)*/
    }
    private void down() {
        sd = Environment.getExternalStorageDirectory();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MyApi.downUrl)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        MyApi api = retrofit.create(MyApi.class);
        Observable<ResponseBody> apk = api.downApk();
        apk.subscribeOn(Schedulers.io())
                .subscribe(new Observer<ResponseBody>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(ResponseBody responseBody) {
                        Log.e(TAG, "onNext: 下载走了");
                        //获取网络输入流
                        InputStream inputStream = responseBody.byteStream();
                        long max = responseBody.contentLength();
                        saveFile(inputStream, sd + "/" + "xx.apk", max);
                        Log.e(TAG, "下载地址 "+sd );
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }
    private void saveFile(InputStream inputStream, String s, long max) {
        //mSeek.setMax((int) max);
        long counth = 0;
        try {
            int leng = -1;
            FileOutputStream stream = new FileOutputStream(new File(s));
            byte[] bytes = new byte[1024 * 10];
            while ((inputStream.read(bytes)) != -1) {
                stream.write(bytes, 0, leng);
                counth += leng;
                Log.e(TAG, "下载量: " + leng);
                float d = counth/max;
                mSeek.setProgress((int) d);
                //mSeek.setProgress((int) d);
            }

            //关流
            stream.close();
            inputStream.close();
           getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(getContext(), "下载完成", Toast.LENGTH_SHORT).show();
                }
            });

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
