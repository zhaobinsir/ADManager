package com.shenxing.admanager.callback;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 穿山甲 激励视频简单回调
 */
public interface SimpleListener {


    /**
     * 广告关闭
     */
    void onAdClose();

    /**
     * 视频播放完成
     */
    void onVideoComplete();

    /**
     * 跳过视频
     */
    void onSkippedVideo();
}
