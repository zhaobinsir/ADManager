package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTRewardVideoAd;
import com.shenxing.admanager.bean.BindDownload;
import com.shenxing.admanager.callback.RewardSimpleListener;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 激励视频
 * 文档 https://ad.oceanengine.com/union/media/union/download/detail?id=3&docId=5de8d9b725b16b00113af0e5&osType=android#6a28b1
 */
public class RewardControllerWM {

    public static final String TAG = "RewardVideoController";

    private TTAdNative mTTAdNative;
    private TTRewardVideoAd mttRewardVideoAd;
    private AdSlot adSlot;
    private WeakReference<Activity> weakReference;
    private int bannerNum = -1;//暂时最多仅支持3条,

    public boolean isExpress = false; //是否请求模板广告
    private boolean needShow;//视频是否展示
    private boolean mIsLoaded = false; //视频是否加载完成
    private RewardSimpleListener<TTRewardVideoAd, String> simpleListener;

    /**
     * 设置简单回调，原回调废话太多
     *
     * @see RewardSimpleListener
     */
    /*public void setSimpleListener(RewardSimpleListener<TTRewardVideoAd, String> simpleListener) {
        this.simpleListener = simpleListener;
    }*/


    //预加载并展示视频，开发懒得处理回调，不支持自定义参数
    public void preAndShowRewardAd(@NonNull Activity context,
                                   @NonNull String codeId,
                                   @IntRange(from = 1, to = 2) int orientation) {
        needShow = true;
        defPreRewardVideo(context, codeId, orientation, null);
    }

    //预加载并展示视频，回调自己处理 ，不支持自定义参数
    public void preAndShowRewardAd(@NonNull Activity context,
                                   @NonNull String codeId,
                                   @IntRange(from = 1, to = 2) int orientation,
                                   @NonNull RewardSimpleListener simpleListener) {
        this.simpleListener = simpleListener;
        needShow = true;
        defPreRewardVideo(context, codeId, orientation, null);
    }

    /**
     * 预加载激励视频，简单回调
     *
     * @param context
     * @param codeId
     * @param simpleListener
     * @param orientation
     */
    public void preRewardVideo(@NonNull Activity context,
                               @NonNull String codeId,
                               @NonNull RewardSimpleListener simpleListener,
                               @IntRange(from = 1, to = 2) int orientation) {
        needShow = false;
        this.simpleListener = simpleListener;
        defPreRewardVideo(context, codeId, orientation, null);
    }

    /**
     * 加载激励视频,自定义参数
     *
     * @param context
     * @param adSlot   自定义参数
     * @param listener
     */
    public void preRewardVideo(@NonNull Activity context,
                               @NonNull AdSlot adSlot,
                               TTAdNative.RewardVideoAdListener listener) {
        needShow = false;
        if (weakReference == null||mTTAdNative==null) {
            weakReference = new WeakReference<>(context);
            mTTAdNative = TTAdSdk.getAdManager().createAdNative(weakReference.get());
        }
        //step5:请求广告
        if (listener == null) {
            loadAd();
        } else {
            mTTAdNative.loadRewardVideoAd(adSlot, listener);
        }
    }

    /**
     * 加载激励视频,自行处理回调
     *
     * @param context
     * @param codeId
     * @param orientation VERTICAL=1,HORIZONTAL=2
     * @param listener
     */
    public void defPreRewardVideo(@NonNull Activity context,
                               @NonNull String codeId,
                               @IntRange(from = 1, to = 2) int orientation,
                               TTAdNative.RewardVideoAdListener listener) {
        if (weakReference == null) {
            weakReference = new WeakReference<>(context);
        }
        if (isExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setAdCount(bannerNum == -1 ? 1 : bannerNum)
                    .setRewardAmount(3)  //奖励的数量
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,激励视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500, 500)
                    .setUserID("")//用户id, 可设置为空字符串
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        } else {
            //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setRewardName("金币") //奖励的名称
                    .setAdCount(bannerNum == -1 ? 1 : bannerNum)
                    .setRewardAmount(3)  //奖励的数量
                    .setUserID("")//用户id,可设置为空字符串
                    .setMediaExtra("media_extra") //附加参数，可选
                    .setOrientation(orientation) //必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        }
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(weakReference.get());

        //step5:请求广告
        if (listener == null) {
            loadAd();
        } else {
            mTTAdNative.loadRewardVideoAd(adSlot, listener);
        }
    }

    /**
     * 展示广告
     */
    public void showAd() {
        try {
            if (mIsLoaded) {
                mttRewardVideoAd.showRewardVideoAd(weakReference.get());
            } else {
                if (simpleListener != null) {
                    simpleListener.onAdError("video not loaded over,wait... or maybe not init");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param ritScenes 广告展示的场景
     * @param scenes    广告展示场景为 CUSTOMIZE_SCENES 时，自定义的场景信息
     */
    public void showAd(TTAdConstant.RitScenes ritScenes, String scenes) {
        try {
            if (mIsLoaded) {
                mttRewardVideoAd.showRewardVideoAd(weakReference.get(), ritScenes, scenes);
            } else {
                if (simpleListener != null) {
                    simpleListener.onAdError("not loaded,wait... or maybe not init");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //    缓存广告
    private void loadAd() {
        mTTAdNative.loadRewardVideoAd(adSlot, new TTAdNative.RewardVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
                if (simpleListener != null) {
                    simpleListener.onAdError(message);
                }
            }

            //视频广告加载后，视频资源缓存到本地的回调，在此回调后，播放本地视频，流畅不阻塞。
            @Override
            public void onRewardVideoCached() {
                Log.e(TAG, "Callback --> onRewardVideoCached");
                mIsLoaded = true;
                if (needShow) {
                    mttRewardVideoAd.showRewardVideoAd(weakReference.get());
                } else Log.e(TAG, "Callback --> onRewardVideoCached mNeedShow false");
                if (simpleListener != null) {
                    simpleListener.onAdLoad(mttRewardVideoAd);
                }
            }

            //视频广告的素材加载完毕，比如视频url等，在此回调后，可以播放在线视频，网络不好可能出现加载缓冲，影响体验。
            @Override
            public void onRewardVideoAdLoad(TTRewardVideoAd ad) {
                Log.e(TAG, "Callback --> onRewardVideoAdLoad");
                mIsLoaded = false;
                mttRewardVideoAd = ad;
                mttRewardVideoAd.setRewardAdInteractionListener(new TTRewardVideoAd.RewardAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> rewardVideoAd show");

                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> rewardVideoAd bar click");

                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> rewardVideoAd close");
                        if (simpleListener != null) {
                            simpleListener.onAdClose();
                        }

                    }

                    //视频播放完成回调
                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> rewardVideoAd complete");
                        if (simpleListener != null) {
                            simpleListener.onVideoComplete();
                        }
                    }

                    @Override
                    public void onVideoError() {
                        Log.e(TAG, "Callback --> rewardVideoAd error");

                    }

                    //视频播放完成后，奖励验证回调，rewardVerify：是否有效，rewardAmount：奖励梳理，rewardName：奖励名称
                    @Override
                    public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                        String logString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                                " name:" + rewardName;
                        Log.e(TAG, "Callback --> " + logString);
                        if (simpleListener != null) {
                            simpleListener.onRewardVerify(rewardVerify,rewardAmount,rewardName);
                        }
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.e(TAG, "Callback --> rewardVideoAd has onSkippedVideo");
                        if (simpleListener != null) {
                            simpleListener.onSkippedVideo();
                        }
                    }
                });
                mttRewardVideoAd.setDownloadListener(new BindDownload());
            }
        });
    }

    /**
     * 资源释放
     */
    public void release() {
        if (weakReference != null) {
            weakReference.clear();
        }
    }

}
