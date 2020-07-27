package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.MainThread;
import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTSplashAd;
import com.shenxing.admanager.utils.UIUtils;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 */
public class SplashControllerWM {

    public static final String TAG = "SplashControllerWM";

    private TTAdNative mTTAdNative;
    private WeakReference<Activity> weakReference;
    AdSlot adSlot = null;
    private static final int AD_TIME_OUT = 3000;
    private ViewGroup container;
    private Class<Activity> tagClass;//跳转
    private boolean mIsExpress; //是否请求模板广告

    /**
     *非个性化模板广告，自动跳转,开发者不处理回调
     *
     * @param context
     * @param container
     * @param codeid
     * @param tagClass  跳转类
     */
    public void loadSplash(@NonNull Activity context,
                           @NonNull ViewGroup container,
                           String codeid,
                           Class<Activity> tagClass
    ) {
        mIsExpress=false;
        loadSplash(context, container, codeid, null, null, tagClass, null);
    }

    /**
      *非个性化模板广告， 不支持跳转,回调自己处理
     *
     * @param context
     * @param codeid
     * @param listener
     */
    public void loadSplash(@NonNull Activity context,
                           String codeid,
                           @NonNull TTAdNative.SplashAdListener listener
    ) {
        mIsExpress=false;
        loadSplash(context, null, codeid, null, null, null, listener);
    }

    //个性化模板开启插屏 不处理回调，宽高不处理
    public void loadIndividuatSplash(@NonNull Activity context,
                           ViewGroup container,
                           String codeid,
                           Class<Activity> tagClass
                           ){
        mIsExpress=true;
        loadSplash(context,container,codeid,null,null,tagClass,null);
    }

    /**
     * 个性化模板开启插屏 不处理回调，宽高自己处理
     * @param context
     * @param container
     * @param codeid
     * @param width 个性化模板宽高
     * @param height
     * @param tagClass
     */
    public void loadIndividuatSplash(@NonNull Activity context,
                              ViewGroup container,
                              String codeid,
                              @NonNull Integer width,
                              @NonNull Integer height,
                              Class<Activity> tagClass
    ){
        mIsExpress=true;
        loadSplash(context,container,codeid,width,height,tagClass,null);
    }

    /**
     * 个性化模板开启插屏 不处理回调，宽高自己处理
     * @param context
     * @param codeid
     * @param width
     * @param height
     * @param listener
     */
    public void loadIndividuatSplash(@NonNull Activity context,
                              String codeid,
                              @NonNull Integer width,
                              @NonNull Integer height,
                              TTAdNative.SplashAdListener listener
    ){
        mIsExpress=true;
        loadSplash(context,null,codeid,width,height,null,listener);
    }

    /**
     *
     * @param context
     * @param container 广告容器
     * @param codeid    广告id
     * @param width     个性化广告宽高
     * @param height
     * @param listener  回调监听
     */
    private void loadSplash(@NonNull Activity context,
                           ViewGroup container,
                           String codeid,
                           Integer width,
                           Integer height,
                           Class<Activity> tagClass,
                           TTAdNative.SplashAdListener listener) {
        weakReference = new WeakReference<>(context);
        this.container = container;
        this.tagClass = tagClass;
        mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
        if (mIsExpress) {
            //个性化模板广告需要传入期望广告view的宽、高，单位dp，请传入实际需要的大小，
            //比如：广告下方拼接logo、适配刘海屏等，需要考虑实际广告大小
            float expressViewWidth = UIUtils.getScreenWidthDp(context);
            float expressViewHeight = UIUtils.getHeight(context);
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeid)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    //模板广告需要设置期望个性化模板广告的大小,单位dp,代码位是否属于个性化模板广告，请在穿山甲平台查看
                    .setExpressViewAcceptedSize(width == null ? expressViewWidth : width, height == null ? expressViewHeight : height)
                    .build();
        } else {
            adSlot = new AdSlot.Builder()
                    .setCodeId(codeid)
                    .setSupportDeepLink(true)
                    .setImageAcceptedSize(1080, 1920)
                    .build();
        }

        //step4:请求广告，调用开屏广告异步请求接口，对请求回调的广告作渲染处理
        if (listener == null) {
            showSplash();
        } else {
            mTTAdNative.loadSplashAd(adSlot, listener);
        }
    }

    private void showSplash() {
        mTTAdNative.loadSplashAd(adSlot, new TTAdNative.SplashAdListener() {
            @Override
            @MainThread
            public void onError(int code, String message) {
                Log.d(TAG, String.valueOf(message));
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onTimeout() {
                goToMainActivity();
            }

            @Override
            @MainThread
            public void onSplashAdLoad(TTSplashAd ad) {
                if (ad == null) {
                    return;
                }
                //获取SplashView
                View view = ad.getSplashView();
                if (view != null && container != null && !weakReference.get().isFinishing()) {
                    container.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    container.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                } else {
                    goToMainActivity();
                }

                //设置SplashView的交互监听器
                ad.setSplashInteractionListener(new TTSplashAd.AdInteractionListener() {
                    @Override
                    public void onAdClicked(View view, int type) {
                        Log.d(TAG, "onAdClicked");
                    }

                    @Override
                    public void onAdShow(View view, int type) {
                        Log.d(TAG, "onAdShow");
                    }

                    @Override
                    public void onAdSkip() {
                        Log.d(TAG, "onAdSkip");
                        goToMainActivity();

                    }

                    @Override
                    public void onAdTimeOver() {
                        Log.d(TAG, "onAdTimeOver");
                        goToMainActivity();
                    }
                });
                if (ad.getInteractionType() == TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
                    ad.setDownloadListener(new TTAppDownloadListener() {
                        boolean hasShow = false;

                        @Override
                        public void onIdle() {
                        }

                        @Override
                        public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                            if (!hasShow) {
                                Log.d(TAG, "onDownloadActive: downloading");
                                hasShow = true;
                            }
                        }

                        @Override
                        public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {


                        }

                        @Override
                        public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onDownloadFinished(long totalBytes, String fileName, String appName) {

                        }

                        @Override
                        public void onInstalled(String fileName, String appName) {

                        }
                    });
                }
            }
        }, AD_TIME_OUT);
    }

    private void goToMainActivity() {
        try {
            if (tagClass != null) {
                Intent intent = new Intent(weakReference.get(), tagClass);
                weakReference.get().startActivity(intent);
            } else Log.e(TAG, "not set tagActivity, can't new task");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
