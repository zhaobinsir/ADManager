package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.graphics.Point;
import android.util.Log;
import android.view.View;

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
import com.shenxing.admanager.callback.InteractListener;
import com.shenxing.admanager.utils.DislikeDialog;
import com.shenxing.admanager.utils.UIUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 插屏广告
 */
public class InteractControllerWM {

    public static final String TAG = "InteractControllerWM";

    private TTAdNative mTTAdNative;
    private TTNativeExpressAd mTTAd;
    private AdSlot adSlot;
    private List<TTNativeExpressAd> uselessData = new ArrayList();//无用广告资源
    private WeakReference<Activity> weakReference;

    public Float width;
    public Float height;
    private int adNum = -1;//暂时最多仅支持3条

    private boolean needShow;//是否需要展示插屏

    private InteractListener simpleListener;

    /**
     * 请求2-3条插屏，回调需要开发者自行处理
     *
     * @param context
     * @param codeId
     * @param adNum
     * @param listener
     */
    public void loadExpressAd(@NonNull Activity context,
                              @NonNull String codeId,
                              @IntRange(from = 1, to = 3) int adNum,
                              @NonNull TTAdNative.NativeExpressAdListener listener) {
        this.adNum = adNum;
        needShow=false;
        simpleListener=null;
        defloadExpressAd(context, codeId, listener);
    }


    /**
     * 加载插屏，开发者不关心回调
     *
     * @param context
     * @param codeId
     */
    public void loadExpressAd(@NonNull Activity context,
                              @NonNull String codeId
    ) {
        simpleListener=null;
        needShow=true;
        defloadExpressAd(context, codeId, null);
    }

    /**
     * 加载插屏，开发者处理回调
     */
    public void loadExpressAd(@NonNull Activity context,
                              @NonNull String codeId,
                              @NonNull InteractListener simpleListener
    ) {
        this.simpleListener = simpleListener;
        needShow=true;
        defloadExpressAd(context, codeId, null);
    }

    /**
     * 加载插屏，开发者自己处理回调
     *
     * @param context
     * @param codeId   广告id
     * @param listener 插屏回调
     */
    private void defloadExpressAd(@NonNull Activity context,
                                  @NonNull String codeId,
                                  TTAdNative.NativeExpressAdListener listener
    ) {
        if (mTTAdNative == null || weakReference == null) {
            mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
            weakReference = new WeakReference<>(context);
        }
        Point screenSize = UIUtils.getScreenInfo(weakReference.get());
        adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(adNum == -1 ? 1 : adNum) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(width == null ? screenSize.x : width, height == null ? 0 : height) //期望模板广告view的size,单位dp
                .setImageAcceptedSize(640, 320)//这个参数设置即可，不影响个性化模板广告的size
                .build();
        reSetAdNum();
        //step5:请求广告，对请求回调的广告作渲染处理
        if (listener == null) {
            bindAd();
        } else {
            mTTAdNative.loadInteractionExpressAd(adSlot, listener);
        }
    }
    //当自定义回调，或 只有一条广告时，执行此方法
    private void bindAd() {
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "load error : " + code + ", " + message);
                if (simpleListener != null) {
                    simpleListener.onLoadError(code, message);
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.d(TAG, "onNativeExpressAdLoad: ");
                if (ads == null || ads.size() == 0) {
                    return;
                }
                if (simpleListener != null) {
                    simpleListener.onAdLoad(ads.get(0));
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd, null);
                mTTAd.render();
                uselessData.add(mTTAd);
            }
        });
    }

    /**
     * 在 NativeExpressAdListener。onNativeExpressAdLoad中 绑定广告监听，
     *
     * @param ad
     */
    public void bindAdListener(final TTNativeExpressAd ad, TTNativeExpressAd.AdInteractionListener listener) {
        if (listener == null) {
            ad.setExpressInteractionListener(getInterListener(ad));
        } else {
            ad.setExpressInteractionListener(listener);
        }
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new BindDownload());
    }

    private TTNativeExpressAd.AdInteractionListener getInterListener(final TTNativeExpressAd ad) {
        return new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                Log.d(TAG, "onAdDismiss: ADClose");
                if (simpleListener != null) {
                    simpleListener.onAdDismiss();
                }
            }

            @Override
            public void onAdClicked(View view, int type) {
                Log.d(TAG, "onAdClicked");
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.d(TAG, "onAdShow");
                if (simpleListener != null) {
                    simpleListener.onAdShow(view, type);
                }
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:");
                if (simpleListener != null) {
                    simpleListener.onRenderFail(view, msg, code);
                }
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                if (simpleListener != null) {
                    simpleListener.onRenderSuccess(view, width, height);
                }
                Log.e("ExpressView", "render suc:");
                //返回view的宽高 单位 dp
                if (needShow) {
                    ad.showInteractionExpressAd(weakReference.get());
                }
            }
        };
    }


    private void bindDislike(TTNativeExpressAd ad, boolean customStyle) {
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
                }
            });
            ad.setDislikeDialog(dislikeDialog);
            return;
        }
        //使用默认模板中默认dislike弹出样式
        ad.setDislikeCallback(weakReference.get(), new BindDisLike(null));
    }


    /**
     * 资源释放
     */
    public void release() {
        if (weakReference != null) {
            weakReference.clear();
        }
        try {
            for (TTNativeExpressAd nativeExpressAd : uselessData) {
                nativeExpressAd.destroy();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            uselessData.clear();
        }
    }

    private void reSetAdNum() {
        adNum = -1;
    }
}
