package com.example.moduletest;

import android.app.Application;

import com.shenxing.admanager.ADConfig;


/**
 * Created by zhaobinsir
 * on 2020/7/24.
 */
public class App extends Application {

    String appid="1101152570";

    @Override
    public void onCreate() {
        super.onCreate();
        //初始化GDT WM
        ADConfig.initAD(getApplicationContext(),appid,"5001121");
        ADConfig.openDebug(true);
    }
}
