package com.shenxing.admanager.control.gdt;

import android.app.Activity;
import android.os.SystemClock;

import androidx.annotation.NonNull;

import com.qq.e.ads.rewardvideo.RewardVideoAD;
import com.qq.e.ads.rewardvideo.RewardVideoADListener;
import com.qq.e.comm.util.AdError;
import com.shenxing.admanager.callback.RewardStatusListener;

/**
 * Created by zhaobinsir
 * on 2020/7/23.
 * 激励视频
 * 参数文档：https://developers.adnet.qq.com/doc/android/union/union_reward_video
 */
public class RewardVideoController {

    private RewardVideoAD rewardVideoAD;
    private RewardStatusListener listener;

    /**
     * 预加载激励视频
     * @param context
     * @param posid 广告id
     * @param listener
     * @param volumeOn 是否开启声音
     */
    public void onPrepareAd(@NonNull Activity context, @NonNull String posid,@NonNull RewardStatusListener listener,boolean volumeOn){
        this.listener=listener;
        rewardVideoAD = new RewardVideoAD(context, posid, listener, volumeOn);
        rewardVideoAD.loadAD();
    }

    /**
     * 展示激励视频 放到onADLoad中回调
     */
    public void showAd(){
        if (rewardVideoAD != null) {//广告展示检查1：广告成功加载，此处也可以使用videoCached来实现视频预加载完成后再展示激励视频广告的逻辑
            if (!rewardVideoAD.hasShown()) {//广告展示检查2：当前广告数据还没有展示过
                long delta = 1000;//建议给广告过期时间加个buffer，单位ms，这里demo采用1000ms的buffer
                //广告展示检查3：展示广告前判断广告数据未过期
                if (SystemClock.elapsedRealtime() < (rewardVideoAD.getExpireTimestamp() - delta)) {
                    rewardVideoAD.showAD();
                } else {
                    listener.onAdExpired();
                }
            } else {
                listener.onAdShowed();
            }
        } else {
            listener.onAdInvalid();
        }
    }

    /**
     * 直接展示激励视频
     * 参数如上
     * @param context
     * @param posid
     * @param listener
     * @param volumeOn
     */
    public void preAndShow(@NonNull Activity context, @NonNull String posid, @NonNull  RewardStatusListener listener, boolean volumeOn){
        this.listener=listener;
        rewardVideoAD = new RewardVideoAD(context, posid, new RewardVideoADListener() {
            @Override
            public void onADLoad() {
                getListener().onADLoad();
                showAd();
            }

            @Override
            public void onVideoCached() {
                getListener().onVideoCached();
            }

            @Override
            public void onADShow() {
                getListener().onADShow();
            }

            @Override
            public void onADExpose() {
                getListener().onADExpose();
            }

            @Override
            public void onReward() {
                getListener().onReward();
            }

            @Override
            public void onADClick() {
                getListener().onADClick();
            }

            @Override
            public void onVideoComplete() {
                getListener().onVideoComplete();
            }

            @Override
            public void onADClose() {
                getListener().onADClose();
            }

            @Override
            public void onError(AdError adError) {
                getListener().onError(adError);
            }
        }, volumeOn);
        rewardVideoAD.loadAD();
    }

    public RewardStatusListener getListener() {
        return listener;
    }
}
