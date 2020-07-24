package com.shenxing.admanager.control.gdt;

import android.app.Activity;

import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.NativeADUnifiedListener;
import com.qq.e.ads.nativ.NativeUnifiedAD;
import com.qq.e.ads.nativ.NativeUnifiedADData;
import com.qq.e.comm.util.AdError;

import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 自渲染，"需要根据具体需求"编写xml渲染模板，没有固定模板，此处不再实现
 * 仅展示初始化 不调用
 */
public class NativeUnifiedController implements NativeADUnifiedListener {

    private NativeUnifiedAD mAdManager;

    private void initTest(Activity activity){
        mAdManager = new NativeUnifiedAD(activity, "postid", this);
        mAdManager.setMinVideoDuration(0);
        mAdManager.setMaxVideoDuration(0);
        // 下面设置项为海外流量使用，国内暂不支持
        mAdManager.setVastClassName("com.qq.e.union.demo.adapter.vast.unified.ImaNativeDataAdapter");
        /**
         * 设置本次拉取的视频广告，从用户角度看到的视频播放策略<p/>
         *
         * "用户角度"特指用户看到的情况，并非SDK是否自动播放，与自动播放策略AutoPlayPolicy的取值并非一一对应 <br/>
         *
         * 例如开发者设置了VideoOption.AutoPlayPolicy.NEVER，表示从不自动播放 <br/>
         * 但满足某种条件(如晚上10点)时，开发者调用了startVideo播放视频，这在用户看来仍然是自动播放的
         */
        mAdManager.setVideoPlayPolicy(VideoOption.AutoPlayPolicy.WIFI); // 本次拉回的视频广告，在用户看来是否为自动播放的

        /**
         * 设置在视频广告播放前，用户看到显示广告容器的渲染者是SDK还是开发者 <p/>
         *
         * 一般来说，用户看到的广告容器都是SDK渲染的，但存在下面这种特殊情况： <br/>
         *
         * 1. 开发者将广告拉回后，未调用bindMediaView，而是用自己的ImageView显示视频的封面图 <br/>
         * 2. 用户点击封面图后，打开一个新的页面，调用bindMediaView，此时才会用到SDK的容器 <br/>
         * 3. 这种情形下，用户先看到的广告容器就是开发者自己渲染的，其值为VideoADContainerRender.DEV
         * 4. 如果觉得抽象，可以参考NativeADUnifiedDevRenderContainerActivity的实现
         */
        mAdManager.setVideoADContainerRender(VideoOption.VideoADContainerRender.SDK); // 视频播放前，用户看到的广告容器是由SDK渲染的

        mAdManager.loadData(1);
    }

    @Override
    public void onADLoaded(List<NativeUnifiedADData> list) {

    }

    @Override
    public void onNoAD(AdError adError) {

    }
}
