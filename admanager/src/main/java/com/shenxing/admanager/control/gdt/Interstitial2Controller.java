package com.shenxing.admanager.control.gdt;

import android.app.Activity;

import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;

/**
 * Created by zhaobinsir
 * on 2020/7/22.
 * 插屏2.0
 * 具体参数回调参考： https://developers.adnet.qq.com/doc/android/union/union_interstitial2_0
 */
public class Interstitial2Controller {

    private UnifiedInterstitialAD iad;

    private int minVideoDuration;
    private int maxVideoDuration;
    private boolean autoPlay=true;
    private boolean isMuted=true;

    /**
     * 非全屏插屏广告
     * @param context
     * @param posId 广告id
     * @param listener 在监听回调里加载广告
     */
    public void prepareAD(Activity context, String posId, UnifiedInterstitialADListener listener){
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }
        iad = new UnifiedInterstitialAD(context, posId, listener);
        setVideoOption();
        iad.loadAD();
    }

    //设置视频参数配置，此处使用默认
    private void setVideoOption() {
        VideoOption.Builder builder = new VideoOption.Builder();
        //使用默认设置
        VideoOption option = builder.setAutoPlayMuted(isAutoPlay())//设置是否自动播放
                .setAutoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO)//设置播放策略
                .setDetailPageMuted(isMuted())//是否静音
                .build();
        iad.setVideoOption(option);
//        此设置会影响广告填充，请谨，暂不预留设置
        if (getMinVideoDuration()!=0) {
            iad.setMinVideoDuration(getMinVideoDuration());
        }
        if (getMaxVideoDuration()!=0){
            iad.setMaxVideoDuration(getMaxVideoDuration());
        }
        iad.setVideoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO);
    }


    /**
     * 确保加载成功回调之后调用 即：onADReceive回调中
     */
    public void showInterAD(){
        if (iad != null) {
            iad.show();
        }
    }

    //初始化全屏广告
    public void prepareFullScreenAD(Activity context,String posId,UnifiedInterstitialADListener listener){
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }
        iad = new UnifiedInterstitialAD(context, posId, listener);
        setVideoOption();
        iad.loadFullScreenAD();
    }

    //展示全屏插屏
    private void showFullScreenVideoAD(Activity activity) {
        if (iad != null) {
            iad.showFullScreenAD(activity);
        }
    }


    //如下视频参数设置 可以不设置使用默认
    public int getMinVideoDuration() {
        return minVideoDuration;
    }

    public void setMinVideoDuration(int minVideoDuration) {
        this.minVideoDuration = minVideoDuration;
    }

    public int getMaxVideoDuration() {
        return maxVideoDuration;
    }

    public void setMaxVideoDuration(int maxVideoDuration) {
        this.maxVideoDuration = maxVideoDuration;
    }

    public boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    public boolean isMuted() {
        return isMuted;
    }
    //是否静音
    public void setMuted(boolean muted) {
        isMuted = muted;
    }
}
