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
import com.bytedance.sdk.openadsdk.TTAdDislike;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.shenxing.admanager.utils.DislikeDialog;
import com.shenxing.admanager.utils.UIUtils;

import java.lang.ref.WeakReference;
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
    private WeakReference<Activity> weakReference;

    public Integer width;
    public Integer height;
    private int bannerNum = -1;//暂时最多仅支持3条

    /**
     * 请求2-3条插屏，回调需要开发者自行处理
     *
     * @param context
     * @param codeId
     * @param bannerNum
     * @param listener
     */
    public void loadExpressAdMore(@NonNull Activity context,
                                  @NonNull String codeId,
                                  @IntRange(from = 2, to = 3) int bannerNum,
                                  @NonNull TTAdNative.NativeExpressAdListener listener) {
        this.bannerNum = bannerNum;
        loadExpressAd(context, codeId, listener);
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
        loadExpressAd(context, codeId, null);
    }

    /**
     * 加载插屏，开发者自己处理回调
     *
     * @param context
     * @param codeId   广告id
     * @param listener 插屏回调
     */
    public void loadExpressAd(@NonNull Activity context,
                              @NonNull String codeId,
                              TTAdNative.NativeExpressAdListener listener
    ) {
        if (mTTAdNative==null||weakReference==null) {
            mTTAdNative = TTAdSdk.getAdManager().createAdNative(context);
            weakReference = new WeakReference<>(context);
        }
        Point screenSize = UIUtils.getScreenInfo(weakReference.get());
        adSlot = new AdSlot.Builder()
                .setCodeId(codeId) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(bannerNum == -1 ? 1 : bannerNum) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(width == null ? screenSize.x : width, height == null ? 0 : height) //期望模板广告view的size,单位dp
                .setImageAcceptedSize(640, 320)//这个参数设置即可，不影响个性化模板广告的size
                .build();
        //step5:请求广告，对请求回调的广告作渲染处理
        if (listener == null) {
            bindAd();
        } else {
            mTTAdNative.loadInteractionExpressAd(adSlot, listener);
        }

    }

    private void bindAd() {
        mTTAdNative.loadInteractionExpressAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.e(TAG, "load error : " + code + ", " + message);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                if (ads == null || ads.size() == 0) {
                    return;
                }
                mTTAd = ads.get(0);
                bindAdListener(mTTAd);
                mTTAd.render();
            }
        });
    }

    /**
     * 在 NativeExpressAdListener。onNativeExpressAdLoad中 绑定广告监听，
     * @param ad
     */
    public void bindAdListener(TTNativeExpressAd ad) {
        ad.setExpressInteractionListener(new TTNativeExpressAd.AdInteractionListener() {
            @Override
            public void onAdDismiss() {
                Log.d(TAG, "onAdDismiss: ADClose");
            }

            @Override
            public void onAdClicked(View view, int type) {
                Log.d(TAG, "onAdClicked");
            }

            @Override
            public void onAdShow(View view, int type) {
                Log.d(TAG, "onAdShow");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                Log.e("ExpressView", "render fail:");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                Log.e("ExpressView", "render suc:");
                //返回view的宽高 单位 dp
                mTTAd.showInteractionExpressAd(weakReference.get());
            }
        });
        bindDislike(ad, false);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD) {
            return;
        }
        ad.setDownloadListener(new TTAppDownloadListener() {
            @Override
            public void onIdle() {
                Log.d(TAG, "onIdle: ");
            }

            @Override
            public void onDownloadActive(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "onDownloadActive: ");
            }

            @Override
            public void onDownloadPaused(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "onDownloadPaused: ");
            }

            @Override
            public void onDownloadFailed(long totalBytes, long currBytes, String fileName, String appName) {
                Log.d(TAG, "onDownloadFailed: ");
            }

            @Override
            public void onInstalled(String fileName, String appName) {
                Log.d(TAG, "onInstalled: ");
            }

            @Override
            public void onDownloadFinished(long totalBytes, String fileName, String appName) {
                Log.d(TAG, "onDownloadFinished: ");

            }
        });
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
        ad.setDislikeCallback(weakReference.get(), new TTAdDislike.DislikeInteractionCallback() {
            @Override
            public void onSelected(int position, String value) {
                //TToast.show(mContext, "反馈了 " + value);
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onRefuse() {

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
        if (mTTAd != null) {
            mTTAd.destroy();
        }
    }
}
