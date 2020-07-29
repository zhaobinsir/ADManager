package com.shenxing.admanager.control.gdt;

import android.app.Activity;

import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.interstitial2.UnifiedInterstitialAD;
import com.qq.e.ads.interstitial2.UnifiedInterstitialADListener;
import com.qq.e.comm.util.AdError;
import com.shenxing.admanager.utils.ILog;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 插屏2.0
 * 具体参数回调参考： https://developers.adnet.qq.com/doc/android/union/union_interstitial2_0
 */
public class Interstitial2Controller  {

    public static final String TAG="Interstitial2Controller";

    private UnifiedInterstitialAD iad;

    private int minVideoDuration;
    private int maxVideoDuration;
    private boolean autoPlay = true;
    private boolean isMuted = true;
    private boolean isFullAd;//全屏ad
    private WeakReference<Activity> weakReference;


    // 非全屏插屏广告
    public void preAndShowAD(Activity context, String posId){
        prepareAD(context,posId,null);
    }

    /**
     * 非全屏插屏广告
     *
     * @param context
     * @param posId    广告id
     * @param listener 在监听回调里加载广告
     */
    public void prepareAD(Activity context, String posId, UnifiedInterstitialADListener listener) {
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }
        isFullAd = false;
        iad = new UnifiedInterstitialAD(context, posId, listener == null ? getListener() : listener);
        setVideoOption();
        iad.loadAD();
    }

    //初始化全屏广告
    public void preAndShowFullScreenAD(Activity context, String posId) {
        prepareFullScreenAD(context, posId, null);
    }

    //初始化全屏广告
    public void prepareFullScreenAD(Activity context, String posId, UnifiedInterstitialADListener listener) {
        if (iad != null) {
            iad.close();
            iad.destroy();
            iad = null;
        }
        if (weakReference==null) {
            weakReference=new WeakReference<>(context);
        }
        isFullAd = true;
        iad = new UnifiedInterstitialAD(context, posId, listener == null ? getListener() : listener);
        setVideoOption();
        iad.loadFullScreenAD();
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
        if (getMinVideoDuration() != 0) {
            iad.setMinVideoDuration(getMinVideoDuration());
        }
        if (getMaxVideoDuration() != 0) {
            iad.setMaxVideoDuration(getMaxVideoDuration());
        }
        iad.setVideoPlayPolicy(VideoOption.VideoPlayPolicy.AUTO);
    }


    /**
     * 确保加载成功回调之后调用 即：onADReceive回调中
     */
    public void showInterAD() {
        if (iad != null) {
            iad.show();
        }
    }

    /**
     * 确保加载成功回调之后调用 即：onADReceive回调中
     */
    public void showFullScreenAD(Activity activity) {
        if (iad != null) {
            iad.showFullScreenAD(activity);
        }
    }


    //如下视频参数设置 可以不设置使用默认
    private int getMinVideoDuration() {
        return minVideoDuration;
    }

    public void setMinVideoDuration(int minVideoDuration) {
        this.minVideoDuration = minVideoDuration;
    }

    private int getMaxVideoDuration() {
        return maxVideoDuration;
    }

    public void setMaxVideoDuration(int maxVideoDuration) {
        this.maxVideoDuration = maxVideoDuration;
    }

    private boolean isAutoPlay() {
        return autoPlay;
    }

    public void setAutoPlay(boolean autoPlay) {
        this.autoPlay = autoPlay;
    }

    private boolean isMuted() {
        return isMuted;
    }

    //是否静音
    public void setMuted(boolean muted) {
        isMuted = muted;
    }


    private UnifiedInterstitialADListener getListener(){
        return new UnifiedInterstitialADListener() {
            @Override
            public void onADReceive() {
                ILog.d(TAG, "onADReceive: ");
                if (isFullAd) {
                    if (weakReference != null) {
                        showFullScreenAD(weakReference.get());
                    }
                }else {
                    showInterAD();
                }
            }

            @Override
            public void onVideoCached() {
                ILog.d(TAG, "onVideoCached: ");
            }

            @Override
            public void onNoAD(AdError adError) {
                ILog.d(TAG, "onNoAD: ");
            }

            @Override
            public void onADOpened() {
                ILog.d(TAG, "onADOpened: ");
            }

            @Override
            public void onADExposure() {
                ILog.d(TAG, "onADExposure: ");
            }

            @Override
            public void onADClicked() {
                ILog.d(TAG, "onADClicked: ");
            }

            @Override
            public void onADLeftApplication() {
                ILog.d(TAG, "onADLeftApplication: ");
            }

            @Override
            public void onADClosed() {
                ILog.d(TAG, "onADClosed: ");
            }
        };
    }
}
