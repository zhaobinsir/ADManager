package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.graphics.Point;
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
import com.shenxing.admanager.callback.NativeLoadMoreListener;
import com.shenxing.admanager.utils.DislikeDialog;
import com.shenxing.admanager.utils.ILog;
import com.shenxing.admanager.utils.UIUtils;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 穿山甲
 * 模板渲染
 */
public class NativeControllerWm  {

    public static final String TAG="NativeControllerWm";

    private TTAdNative mTTAdNative;
    private ViewGroup mContainer;
    private AdSlot adSlot;
    private WeakReference<Activity> weakReference;
    private boolean customStyle;//dislike 自定义样式 ，暂不实现预留
    private List<TTNativeExpressAd> uselessData=new ArrayList();//无用广告资源

    private int adCount;//请求的广告数量
    private List<TTNativeExpressAd> adList=new ArrayList<>();
    private NativeLoadMoreListener loadMoreListener;
    private boolean loadMoreAd;//加载更多广告中
    private boolean interrupt;//中断请求和广告回调，防止数据加载过多 页面退出时仍发生回调

    public Float width;
    public Float height;

    //预加载native 自己折腾AdSlot
    public void preNative(@NonNull Activity context,
                          @NonNull AdSlot adSlot,
                          TTAdNative.NativeExpressAdListener listener){
        if (weakReference != null) {
            weakReference.clear();
        }
        weakReference=new WeakReference<>(context);
        mTTAdNative =TTAdSdk.getAdManager().createAdNative(context);
        this.adSlot=adSlot;
        mTTAdNative.loadNativeExpressAd(adSlot, listener);
    }
    //不传宽高
    public void preNative(@NonNull Activity context,
                          @NonNull String coidid,
                          @IntRange(from = 1,to = 3) int adcount,
                          TTAdNative.NativeExpressAdListener listener){
        defPreNative(context,coidid,adcount,listener);
    }

    //展示一条native
    public void preAndShowNative(@NonNull Activity context,
                                 @NonNull String coidid,
                                 @NonNull ViewGroup viewGroup
                                ){
        mContainer=viewGroup;
        defPreNative(context,coidid,1,null);
    }
    //加载多条native，最大加载100条
    public synchronized void preNativeMore(@NonNull Activity context,
                              @NonNull String coidid,
                              @IntRange(from = 4,to = 100) int adcount,
                              @NonNull NativeLoadMoreListener listener){
        if (loadMoreAd==true) {
            ILog.d(TAG, "loadNativeAdMore: not load over,please wait");
            return;
        }
        loadMoreAd=true;
        adList.clear();
        adCount=adcount;
        this.loadMoreListener=listener;
        if (weakReference != null) {
            weakReference.clear();
        }
        if (weakReference==null||mTTAdNative==null) {
            weakReference=new WeakReference<>(context);
            mTTAdNative =TTAdSdk.getAdManager().createAdNative(context);
        }
        Point screenSize= UIUtils.getScreenInfo(weakReference.get());
        adSlot=  new AdSlot.Builder()
                .setCodeId(coidid) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(adcount) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(width==null?screenSize.x:width
                        ,height==null?0:height) //必填：期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(640,320) //这个参数设置即可，不影响个性化模板广告的size
                .build();
        //加载广告
        onRqCount();
    }

    /**
     * 预加载native
     * @param context
     * @param coidid 广告id
     * @param adcount 请求广告数量
     * @param listener 回调
     */
    private void defPreNative(@NonNull Activity context,
                          @NonNull String coidid,
                           @IntRange(from = 1,to = 3) int adcount,
                           TTAdNative.NativeExpressAdListener listener){
        if(weakReference==null||mTTAdNative==null){
            weakReference=new WeakReference<>(context);
            mTTAdNative =TTAdSdk.getAdManager().createAdNative(context);
        }
        Point screenSize= UIUtils.getScreenInfo(weakReference.get());
        adSlot=  new AdSlot.Builder()
                .setCodeId(coidid) //广告位id
                .setSupportDeepLink(true)
                .setAdCount(adcount) //请求广告数量为1到3条
                .setExpressViewAcceptedSize(width==null?screenSize.x:width
                        ,height==null?0:height) //必填：期望个性化模板广告view的size,单位dp
                .setImageAcceptedSize(640,320) //这个参数设置即可，不影响个性化模板广告的size
                .build();
        //加载广告
        mTTAdNative.loadNativeExpressAd(adSlot, listener==null?getListener():listener);
    }

    private TTAdNative.NativeExpressAdListener getListener(){
        return new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                if (list == null || list.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd=list.get(0);
                bindAdListener(mTTAd,mContainer);
                mTTAd.render();//调用render开始渲染广告
                uselessData.add(mTTAd);
            }
        };
    }

    //绑定广告行为
    public void bindAdListener(TTNativeExpressAd ad, final ViewGroup container) {
        ad.setExpressInteractionListener(new BindExpressInteract(new WeakReference<>(container)));
        //dislike设置 默认不使用
        bindDislike(ad, customStyle,container);
        if (ad.getInteractionType() != TTAdConstant.INTERACTION_TYPE_DOWNLOAD){
            return;
        }
        //可选，下载监听设置
        ad.setDownloadListener(new BindDownload());
    }

    /**
     * 设置广告的不喜欢，开发者可自定义样式
     * @param ad
     * @param customStyle 是否自定义样式，true:样式自定义
     */
    private void bindDislike(TTNativeExpressAd ad, boolean customStyle, final ViewGroup container) {
        if (customStyle) {
            //使用自定义样式
            List<FilterWord> words = ad.getFilterWords();
            if (words == null || words.isEmpty()) {
                return;
            }

            final DislikeDialog dislikeDiaILog = new DislikeDialog(weakReference.get(), words);
            dislikeDiaILog.setOnDislikeItemClick(new DislikeDialog.OnDislikeItemClick() {
                @Override
                public void onItemClick(FilterWord filterWord) {
                    //屏蔽广告
                    //用户选择不喜欢原因后，移除广告展示
                    container.removeAllViews();
                }
            });
            ad.setDislikeDialog(dislikeDiaILog);
            return;
        }
        //使用默认个性化模板中默认dislike弹出样式
        ad.setDislikeCallback(weakReference.get(), new BindDisLike(new WeakReference<>(container)));
    }


    /**
     * 请求广告
     */
    private void onRqCount() {
        if (adCount>=3) {
            adCount=adCount-3;
            adSlot.setAdCount(3);
            mTTAdNative.loadNativeExpressAd(adSlot,new LoadMoreListener());
        }else if(adCount>0&&adCount<3){
            adSlot.setAdCount(adCount);
            adCount=0;
            mTTAdNative.loadNativeExpressAd(adSlot,new LoadMoreListener());
        }else if(adCount==0){
            return;
        }
    }

    //在合适的时机，释放广告的资源 注意：单个native时调用
    protected void release() {
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

    /**
     * 若调用了 loadNativeAdMore，建议调用此函数
     */
    public void destroy(){
        interrupt=true;
        adCount=0;
        loadMoreAd=false;
        adList.clear();
        release();
    }

    /**
     * 重置状态
     */
    private void reset() {
        loadMoreAd = false;
        adCount = 0;
    }

    //加载更多的回调
    class LoadMoreListener implements TTAdNative.NativeExpressAdListener{

        @Override
        public void onError(int i, String s) {
            reset();
            if (loadMoreListener != null) {
                loadMoreListener.onLoadError(adList);//存在之前加载成功的数据，的可能性
                adList.clear();
            }
        }

        @Override
        public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
            if (interrupt) {
                ILog.e(TAG, "onADLoaded: reqeust interrupt... will clear data");
                return;
            }
            adList.addAll(list);
            ILog.d(TAG, "onNativeExpressAdLoad: "+adCount);
            if (adCount>0) {
                onRqCount();
            }else {//回调
                reset();
                if (loadMoreListener != null) {
                    loadMoreListener.onAdLoad(adList);
                    adList.clear();
                }
            }
        }
    }
}
