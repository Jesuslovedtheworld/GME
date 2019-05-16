package com.baidu.androidheight.serivice;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.res.Resources;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;

import com.baidu.androidheight.api.MyApi;
import com.baidu.androidheight.base.MyApp;

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

public class DowmService extends Service {
    private static final String TAG = "DowmService";
    private File sd;

    public DowmService() {
    }

    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate: ");

        super.onCreate();
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        return null;

    }

    @Override
    public boolean bindService(Intent service, ServiceConnection conn, int flags) {
        Log.e(TAG, "服务: ");
        return super.bindService(service, conn, flags);
    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy: ");
        super.onDestroy();
    }



    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand: ");
         //initData();
         down();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.e(TAG, "onUnbind: " );
        return super.onUnbind(intent);
    }

    @Override
    public Resources getResources() {
        Log.e(TAG, "getResources: " );
        return super.getResources();
    }
    private void initData() {
        sd = Environment.getExternalStorageDirectory();
        new Thread(new Runnable() {
            @Override
            public void run() {
                down();
            }
        }).start();
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
                //mSeek.setProgress((int) d);
            }

            //关流
            stream.close();
            inputStream.close();
            Toast.makeText(MyApp.getApp(), "下载完成", Toast.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
