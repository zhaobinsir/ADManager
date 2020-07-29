package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTNativeExpressAd;
import com.shenxing.admanager.callback.ExpressDrawSimpleListener;
import com.shenxing.admanager.utils.UIUtils;

import java.lang.ref.WeakReference;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 个性化模板Draw信息流
 * 注意：个性化模板Draw视频广告高度不能设置为0,
 */
public class ExpressDrawControllerWM {

    public static final String TAG="ExpressDrawControllerWM";

    private WeakReference<Activity> weakReference;
    private AdSlot adSlot;
    private TTAdNative mTTAdNative;
    private Float expressWidth;//期望模板广告view的size,单位dp
    private Float expressHeight;
    private ViewGroup container;
    //接口回调
    private ExpressDrawSimpleListener simpleListener;
    private int adNum=-1;


    /**
     * 一条数据 开发者只关心简单回调
     * @param context
     * @param coidId 广告id
     * @param container 添加广告的容器
     * @param simpleListener 简单回调
     * @see ExpressDrawSimpleListener
     */
    public void loadAndShowExpressAd(@NonNull Activity context,
                              @NonNull String coidId,
                              @NonNull ViewGroup container,
                              @NonNull ExpressDrawSimpleListener simpleListener){
        this.simpleListener=simpleListener;
        defLoadExpressAd(context,coidId,container,null);
    }

    /**
     * 多条数据 不传container
     * @param context
     * @param coidId
     * @param adNum 广告数量，暂不支持3条以上
     * @param listener 原生回调，请注意:必须在onNativeExpressAdLoad调用bindAdListener()
     */
    public void loadExpressAd(@NonNull Activity context,
                              @NonNull String coidId,
                              @IntRange(from = 1,to = 3) int adNum,
                              @NonNull TTAdNative.NativeExpressAdListener listener){
        this.adNum=adNum;
        defLoadExpressAd(context,coidId,null,listener);
    }
    
    //基类
    private void defLoadExpressAd(@NonNull Activity context,
                                 @NonNull String coidId,
                                 ViewGroup container,
                                 TTAdNative.NativeExpressAdListener listener){
        if (weakReference==null||mTTAdNative==null) {
            weakReference=new WeakReference<>(context);
            mTTAdNative= TTAdSdk.getAdManager().createAdNative(weakReference.get());
        }
        if (expressWidth==null&&expressHeight==null) {
             expressWidth = UIUtils.getScreenWidthDp(weakReference.get());
             expressHeight = UIUtils.getHeight(weakReference.get());
        }
        this.container=container;
         adSlot = new AdSlot.Builder()
                .setCodeId(coidId)
                .setSupportDeepLink(true)
                .setExpressViewAcceptedSize(expressWidth, expressHeight) //期望模板广告view的size,单位dp
                .setAdCount(adNum==-1?1:adNum) //请求广告数量为1到3条
                .build();

        if (listener==null) {
            addListener();
        }else {
            mTTAdNative.loadExpressDrawFeedAd(adSlot, listener);
        }
        reSetAdNum();
    }

    private void addListener(){
        mTTAdNative.loadExpressDrawFeedAd(adSlot, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: "+i+":"+s);
                if (simpleListener != null) {
                    simpleListener.onLoadError(i,s);
                }
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.d(TAG, "onNativeExpressAdLoad: ");
                if (ads == null || ads.isEmpty()) {
                    Log.e(TAG, "onNativeExpressAdLoad: ad size 0 retrun" );
                    return;
                }
                bindAdListener(ads,simpleListener);//一般仅有一条数据时的回调
            }
        });
    }

    //绑定广告回调
    public void bindAdListener(@NonNull final List<TTNativeExpressAd> ads, final ExpressDrawSimpleListener simpleListener){
        for (final TTNativeExpressAd ad : ads) {
            //点击监听器必须在getAdView之前调
            ad.setVideoAdListener(new TTNativeExpressAd.ExpressVideoAdListener() {
                @Override
                public void onVideoLoad() {
                    Log.d(TAG, "onVideoLoad: ");
                }

                @Override
                public void onVideoError(int errorCode, int extraCode) {
                    Log.d(TAG, "onVideoError: ");
                    if (simpleListener!=null) {
                        simpleListener.onVideoError(errorCode,extraCode);
                    }
                }

                @Override
                public void onVideoAdStartPlay() {
                    Log.d(TAG, "onVideoAdStartPlay: ");
                }

                @Override
                public void onVideoAdPaused() {
                    Log.d(TAG, "onVideoAdPaused: ");
                }

                @Override
                public void onVideoAdContinuePlay() {
                    Log.d(TAG, "onVideoAdContinuePlay: ");
                }

                @Override
                public void onProgressUpdate(long current, long duration) {
                    Log.d(TAG, "onProgressUpdate: ");
                }

                @Override
                public void onVideoAdComplete() {
                    Log.d(TAG, "onVideoAdComplete: ");
                    if (simpleListener!=null) {
                        simpleListener.onVideoAdComplete();
                    }
                }

                @Override
                public void onClickRetry() {
                    Log.d(TAG, "onClickRetry!");
                }
            });
            ad.setCanInterruptVideoPlay(true);//是否允许点击暂停视频播放
            ad.setExpressInteractionListener(new TTNativeExpressAd.ExpressAdInteractionListener() {
                @Override
                public void onAdClicked(View view, int type) {
                    Log.d(TAG, "onAdClicked: ");
                }

                @Override
                public void onAdShow(View view, int type) {
                    Log.d(TAG, "onAdShow: ");
                    if (simpleListener!=null) {
                        simpleListener.onAdShow(view,type);
                    }
                }

                @Override
                public void onRenderFail(View view, String msg, int code) {
                    Log.d(TAG, "onRenderFail: ");
                    if (simpleListener!=null) {
                        simpleListener.onRenderFail(view,msg,code);
                    }
                }

                @Override
                public void onRenderSuccess(View view, float width, float height) {
                    Log.d(TAG, "onRenderSuccess: ");
                    if (simpleListener!=null) {
                        simpleListener.onRenderSuccess(view,width,height);
                    }
                    //仅有一条数据时添加
                    if (ads.size()==1&&container!=null) {
                        container.removeAllViews();
                        container.addView(view);
                        container=null;
                    }
                }
            });
            ad.render();
        }
    }

    /**
     * 重置广告请求数量，封装成方法，为了方便理解
     */
    private void reSetAdNum() {
        adNum = -1;
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
