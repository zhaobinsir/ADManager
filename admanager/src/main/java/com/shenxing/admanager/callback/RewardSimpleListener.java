package com.shenxing.admanager.callback;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 穿山甲 激励视频简单回调
 */
public interface RewardSimpleListener<T,M> extends SimpleListener {

    /**
     * 广告缓存完成
     */
    void onAdLoad(T t);

    /**
     * 广告发生错误
     */
    void onAdError(M m);


    /**
     * 视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
     */
    void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName);

}
