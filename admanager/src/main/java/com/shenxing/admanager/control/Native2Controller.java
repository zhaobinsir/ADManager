package com.shenxing.admanager.control;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.qq.e.ads.nativ.express2.NativeExpressAD2;
import com.qq.e.ads.nativ.express2.NativeExpressADData2;
import com.qq.e.ads.nativ.express2.VideoOption2;
import com.qq.e.comm.util.AdError;
import com.shenxing.admanager.callback.NativeLoadMoreListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/23.
 * 原生广告渲染2.0 注意注意 官网说明： 目前模板2.0功能处于内测中，请开发者联系对应商务开通权限。
 * 回调参考文档链接：https://developers.adnet.qq.com/doc/android/union/union_native_express2_0
 */
public class Native2Controller implements NativeExpressAD2.AdLoadListener{

    public static final String TAG="Native2Controller";

    private NativeExpressAD2 mNativeExpressAD2;

    private boolean loadMoreAd;//是否加载更多广告中
    private List<NativeExpressADData2> adList=new ArrayList<>();
    private int adCount;
    private NativeLoadMoreListener listener;

    /**
     * 加载native 默认宽高
     * @param context
     * @param posid    广告id
     * @param listener 广告加载回调
     */
    public void loadNative2Ad(@NonNull Activity context, @NonNull String posid, @IntRange(from = 1,to = 10) int adcount, @NonNull NativeExpressAD2.AdLoadListener listener) {
        try {
            mNativeExpressAD2 = new NativeExpressAD2(context, posid, listener);
            mNativeExpressAD2.setAdSize(0,0);
            // 如果您在平台上新建原生模板广告位时，选择了支持视频，那么可以进行个性化设置（可选）
            mNativeExpressAD2.setVideoOption2(getVideoOption());
            mNativeExpressAD2.loadAd(adcount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private VideoOption2 getVideoOption() {
        VideoOption2.Builder builder = new VideoOption2.Builder();
        builder.setAutoPlayPolicy(VideoOption2.AutoPlayPolicy.WIFI) // WIFI 环境下可以自动播放视频
                .setAutoPlayMuted( true)  // 自动播放时是否为静音
                .setDetailPageMuted(false); // 视频详情页是否为静音
               /* //  设置返回视频广告的最大视频时长（闭区间，可单独设置），单位:秒，默认为 0 代表无限制，合法输入为：5<=maxVideoDuration<=60. 此设置会影响广告填充，请谨慎设置
                .setMaxVideoDuration(0)
                // 设置返回视频广告的最小视频时长（闭区间，可单独设置），单位:秒，默认为 0 代表无限制， 此设置会影响广告填充，请谨慎设置
                .setMinVideoDuration(0);*/
        return builder.build();
    }

    /**
     * 加载native 自定义宽高
     * @param context
     * @param posid    广告id
     * @param listener 广告加载回调
     */
    public void loadNative2Ad(@NonNull Activity context, @NonNull String posid, @IntRange(from = 1,to = 10)int adcount, @NonNull int width,@NonNull int height, @NonNull NativeExpressAD2.AdLoadListener listener) {
        try {
            mNativeExpressAD2 = new NativeExpressAD2(context, posid, listener);
            mNativeExpressAD2.setAdSize(width,height);
            // 如果您在平台上新建原生模板广告位时，选择了支持视频，那么可以进行个性化设置（可选）
            mNativeExpressAD2.setVideoOption2(getVideoOption());
            mNativeExpressAD2.loadAd(adcount);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     *加载10条以上的native 最大限制100条
     * @param context
     * @param posid
     * @param adcount
     */
    public synchronized void loadNative2AdMore(@NonNull Activity context, @NonNull String posid, @IntRange(from = 11,to = 100) int adcount,@NonNull int width,@NonNull int height, NativeLoadMoreListener listener){
        if (loadMoreAd==true) {
            Log.d(TAG, "loadNativeAdMore: not load over,please wait...");
            return;
        }
        loadMoreAd=true;
        destroyAd();
        adList.clear();
        adCount=adcount;
        this.listener=listener;
        ///参数设置
        //
        try {
            mNativeExpressAD2 = new NativeExpressAD2(context, posid, this);
            mNativeExpressAD2.setAdSize(width,height);
            // 如果您在平台上新建原生模板广告位时，选择了支持视频，那么可以进行个性化设置（可选）
            mNativeExpressAD2.setVideoOption2(getVideoOption());
            mNativeExpressAD2.loadAd(adcount);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 释放前一个 NativeExpressAD2Data 的资源
     */
    private void destroyAd() {
        try {
            for (NativeExpressADData2 data2 : adList) {
                data2.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onLoadSuccess(List<NativeExpressADData2> list) {
        adList.addAll(list);
        if (adCount>0) {
            onRqCount();
        }else {//回调
            reset();
            if (listener != null) {
                listener.onAdLoad(adList);
            }
        }
    }

    @Override
    public void onNoAD(AdError adError) {
        reset();
        if (listener != null) {
            listener.onNoAd(adList);//存在之前加载成功的数据，的可能性
        }
    }

    /**
     * 请求广告
     */
    private void onRqCount() {
        if (adCount>10) {
            adCount=adCount-10;
            mNativeExpressAD2.loadAd(10);
        }else if(adCount<10&&adCount>0){
            mNativeExpressAD2.loadAd(adCount);
            adCount=0;
        }else if(adCount==0){
            return;
        }
    }
    /**
     * 重置状态
     */
    private void reset() {
        loadMoreAd = false;
        adCount = 0;
    }

}
