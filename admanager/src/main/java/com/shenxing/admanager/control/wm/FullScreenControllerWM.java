package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.util.Log;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTFullScreenVideoAd;
import com.shenxing.admanager.callback.FullScreenSimpleListener;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 全屏广告
 * 文档 ：https://ad.oceanengine.com/union/media/union/download/detail?id=3&docId=5de8d9b825b16b00113af0e8&osType=android#6d619a
 */
public class FullScreenControllerWM {

    public static final String TAG = "FullScreenControllerWM";

    public boolean isExpress = false; //是否请求模板广告
    private boolean isLoaded = false; //视频是否加载完成
    private boolean needShow;//视频是否展示

    WeakReference<Activity> weakReference;
    private AdSlot adSlot;
    private TTAdNative mTTAdNative;
    private TTFullScreenVideoAd mttFullVideoAd;

    private FullScreenSimpleListener<TTFullScreenVideoAd, String> simpleListener;

    /*public void setSimpleListener(FullScreenSimpleListener simpleListener) {
        this.simpleListener = simpleListener;
    }*/

    //1 预留接口， 2 简单接口回调 3 自定义AdSlot


    /**
     * 加载成功后直接展示广告，简单回调接口
     * @param context
     * @param codeId 广告id
     * @param orientation TTAdConstant.HORIZONTAL TTAdConstant.VERTICAL
     * @param simpleListener
     */
    public void preAndShowFullScreenAd(@NonNull Activity context,
                                   @NonNull String codeId,
                                   @IntRange(from = 1, to = 2) int orientation,
                                   @NonNull FullScreenSimpleListener simpleListener) {
        this.simpleListener = simpleListener;
        needShow = true;
        loadFullScreenAd(context, codeId, orientation, null);
    }

    /**
     * 预加载全屏广告，简单回调接口
     * 参数如上
     * @param context
     * @param codeId
     * @param simpleListener
     * @param orientation
     */
    public void preFullScreenAd(@NonNull Activity context,
                                @NonNull String codeId,
                                @IntRange(from = 1, to = 2) int orientation,
                                @NonNull FullScreenSimpleListener simpleListener

    ) {
        needShow = false;
        this.simpleListener = simpleListener;
        loadFullScreenAd(context, codeId, orientation, null);
    }


    /**
     *预加载接口 ，开发者自行自己处理回调， 可参考loadFullScreenAd方法，或官方文档
     **/
    public void preFullScreenAd(@NonNull Activity context,
                                  @NonNull String codeId,
                                  @IntRange(from = 1, to = 2) int orientation,
                                  @NonNull TTAdNative.FullScreenVideoAdListener listener){
        needShow = false;
        loadFullScreenAd(context,codeId,orientation,listener);
    }


    /**
     * 全屏广告，原生接口（麻烦，需要自行处理）
     * 参数如上
     * @param context
     * @param codeId
     * @param orientation
     * @param listener
     */
    //基类
    private void loadFullScreenAd(@NonNull Activity context,
                                @NonNull String codeId,
                                @IntRange(from = 1, to = 2) int orientation,
                                TTAdNative.FullScreenVideoAdListener listener) {
        if (weakReference == null||mTTAdNative==null) {
            weakReference = new WeakReference<>(context);
            mTTAdNative = TTAdSdk.getAdManager().createAdNative(weakReference.get());
        }
        if (isExpress) {
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,全屏视频场景，只要设置的值大于0即可
                    .setExpressViewAcceptedSize(500, 500)
                    .setSupportDeepLink(true)
                    .setOrientation(orientation)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();

        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeId)
                    .setSupportDeepLink(true)
                    .setOrientation(orientation)//必填参数，期望视频的播放方向：TTAdConstant.HORIZONTAL 或 TTAdConstant.VERTICAL
                    .build();
        }

        //step5:请求广告
        if (listener==null) {
            loadAd();
        }else {
            mTTAdNative.loadFullScreenVideoAd(adSlot, listener);
        }
    }

    private void loadAd() {
        mTTAdNative.loadFullScreenVideoAd(adSlot, new TTAdNative.FullScreenVideoAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "Callback --> onError: " + code + ", " + String.valueOf(message));
                if (simpleListener != null) {
                    simpleListener.onAdError(code+":"+message);
                }
            }

            @Override
            public void onFullScreenVideoAdLoad(TTFullScreenVideoAd ad) {
                Log.e(TAG, "Callback --> onFullScreenVideoAdLoad");
                mttFullVideoAd = ad;
                isLoaded = false;
                //设置广告互动
                mttFullVideoAd.setFullScreenVideoAdInteractionListener(new TTFullScreenVideoAd.FullScreenVideoAdInteractionListener() {

                    @Override
                    public void onAdShow() {
                        Log.d(TAG, "Callback --> FullVideoAd show");
                    }

                    @Override
                    public void onAdVideoBarClick() {
                        Log.d(TAG, "Callback --> FullVideoAd bar click");
                    }

                    @Override
                    public void onAdClose() {
                        Log.d(TAG, "Callback --> FullVideoAd close");
                        if (simpleListener != null) {
                            simpleListener.onAdClose();
                        }
                    }

                    @Override
                    public void onVideoComplete() {
                        Log.d(TAG, "Callback --> FullVideoAd complete");
                        if (simpleListener != null) {
                            simpleListener.onVideoComplete();
                        }
                    }

                    @Override
                    public void onSkippedVideo() {
                        Log.d(TAG, "Callback --> FullVideoAd skipped");
                        if (simpleListener != null) {
                            simpleListener.onSkippedVideo();
                        }
                    }

                });


                ad.setDownloadListener(new TTAppDownloadListener() {
                    @Override
                    public void onIdle() {
                    }

                    @Override
                    public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadActive==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadPaused===totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFailed==totalBytes=" + totalBytes + ",currBytes=" + currBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                        Log.d("DML", "onDownloadFinished==totalBytes=" + totalBytes + ",fileName=" + fileName + ",appName=" + appName);
                    }

                    @Override
                    public void onInstalled(String fileName, String appName) {
                        Log.d("DML", "onInstalled==" + ",fileName=" + fileName + ",appName=" + appName);
                    }
                });
            }

            @Override
            public void onFullScreenVideoCached() {
                Log.e(TAG, "Callback --> onFullScreenVideoCached");
                isLoaded=true;
                if (simpleListener != null) {
                    simpleListener.onAdLoad(mttFullVideoAd);
                }
                if (needShow) {
                    showAd();
                }else Log.d(TAG, "onFullScreenVideoCached: needshow false inhibit show");
            }
        });
    }


    /**
     * 展示广告
     */
    public void showAd() {
        try {
            if (isLoaded) {
                mttFullVideoAd.showFullScreenVideoAd(weakReference.get());
            } else {
                if (simpleListener != null) {
                    simpleListener.onAdError("not loaded,wait... or maybe not init");
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
            if (isLoaded) {
                mttFullVideoAd.showFullScreenVideoAd(weakReference.get(), ritScenes, scenes);
            } else {
                if (simpleListener != null) {
                    simpleListener.onAdError("not loaded,wait... or maybe not init");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
