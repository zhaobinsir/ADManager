package com.shenxing.admanager.callback;

import com.qq.e.ads.rewardvideo.RewardVideoADListener;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 */
public interface RewardStatusListener extends RewardVideoADListener {

   //激励视频广告已过期
    void onAdExpired();

   //此条广告已经展示过
    void onAdShowed();

  //成功加载广告后再进行广告展示
    void onAdInvalid();

}
