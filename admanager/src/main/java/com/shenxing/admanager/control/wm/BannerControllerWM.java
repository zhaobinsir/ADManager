package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.FilterWord;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.shenxing.admanager.bean.BindDisLike;
import com.shenxing.admanager.bean.BindDownload;
import com.shenxing.admanager.bean.BindExpressInteract;
import com.shenxing.admanager.utils.DislikeDialog;
import com.shenxing.admanager.utils.UIUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 参数具体参考 https://ad.oceanengine.com/union/media/union/download/detail?id=3&docId=5de8d9b6b1afac001293310c&osType=android
 * banner广告
 */
public class BannerControllerWM {

    public static final String TAG = "BannerControllerWM";

    private TTAdNative mTTAdNative;
    private AdSlot adSlot;
    WeakReference<Activity> weakReference;
    private List<TTNativeExpressAd> uselessData=new ArrayList();//无用广告资源

    private int intervalTime = -1;//设置轮播时间

    public Float width;
    public Float height;
    private int bannerNum=-1;//暂时最多仅支持3条

    /**
     * 请求2-3条banner，回调需要开发者自行处理
     * @param context
     * @param codeId
     * @param bannerNum
     * @param listener
     */
    public void loadBannerMore(@NonNull Activity context,
                               @NonNull String codeId,
                               @IntRange(from = 1,to = 3) int bannerNum,
                               @NonNull TTAdNative.NativeExpressAdListener listener){
        this.bannerNum=bannerNum;
        loadBanner(context,codeId,null,listener);
    }

    /**
     *  请求banner 开发者懒得处理回调
     * @param context
     * @param codeId
     * @param container
     */
    public void loadBanner(@NonNull Activity context,
                           @NonNull String codeId,
                           @NonNull ViewGroup container
                          ) {
        loadBanner(context,codeId,container,null);
    }

    /**
     * 请求banner 开发者回调自己处理
     *
     * @param context
     * @param codeId
     */
    public void loadBanner(@NonNull Activity context,
                           @NonNull String codeId,
                           @NonNull TTAdNative.NativeExpressAdListener listener
    ) {
        loadBanner(context,  codeId, null,listener);
    }

    /**
     * 请求banner，不关心回调
     *
     * @param context
     * @param container 容器
     * @param codeId    广告id
     * @param listener  监听
     */
    private void loadBanner(@NonNull Activity context,
                            @NonNull String codeId,
                           final ViewGroup container,
                           TTAdNative.NativeExpressAdListener listener) {
        if (mTTAdNative == null||weakReference==null) {
            mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
            weakReference = new WeakReference<>(context);
        }
        Point screenSize= UIUtils.getScreenInfo(weakReference.get());
        Log.d(TAG, "loadBanner: screenSize " + screenSize.x);
        adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(bannerNum==-1?1:bannerNum) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(width == null ? screenSize.x : width, height == null ? 0 : height) //期望模板广告view的size,单位dp
                .build();
        if (listener == null) {
            bindBanner(container);
        } else {
            mTTAdNative.loadBannerExpressAd(adSlot, listener);
        }
    }

    private void bindBanner(final ViewGroup container) {
        mTTAdNative.loadBannerExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, "onError: code,message " + code + "," + message);
                container.removeAllViews();
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.d(TAG, "onNativeExpressAdLoad: ");
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                if (intervalTime != -1) {
                    mTTAd.setSlideIntervalTime(Math.max(intervalTime, 30 * 1000));
                }
                bindAdListener(mTTAd, container);
                mTTAd.render();
                uselessData.add(mTTAd);
            }
        });
    }

    //公开此方法
    public void bindAdListener(@NonNull TTNativeExpressAd ad, @NonNull final ViewGroup container) {
        ad.setExpressInteractionListener(new BindExpressInteract(new WeakReference<>(container)));
        //dislike设置
        bindDislike(ad, false, container);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new BindDownload());
    }

    /**
     * 设置广告的不喜欢, 注意：强烈建议设置该逻辑，如果不设置dislike处理逻辑，则模板广告中的 dislike区域不响应dislike事件。
     *
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
    private void bindDislike(@NonNull TTNativeExpressAd ad, boolean customStyle, @NonNull final ViewGroup container) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDialog = new DislikeDialog(weakReference.get(), words);
            dislikeDialog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告
                    //用户选择不喜欢原因后，移除广告展示
                    container.removeAllViews();
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback(weakReference.get(),new BindDisLike(new WeakReference<>(container)));
    }


    /**
     * 资源释放
     */
    public void release(){
        if (weakReference != null) {
            weakReference.clear();
        }
        try {
            for (TTNativeExpressAd nativeExpressAd : uselessData) {
                nativeExpressAd.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            uselessData.clear();
        }
    }


}
