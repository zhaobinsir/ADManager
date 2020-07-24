package com.shenxing.admanager.control.gdt;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.qq.e.ads.cfg.VideoOption;
import com.qq.e.ads.nativ.ADSize;
import com.qq.e.ads.nativ.NativeExpressAD;
import com.qq.e.ads.nativ.NativeExpressADView;
import com.qq.e.comm.util.AdError;
import com.shenxing.admanager.callback.NativeLoadMoreListener;
import com.shenxing.admanager.callback.NativeStatusAdListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 原生广告
 * 广告回调参数说明参考： https://developers.adnet.qq.com/doc/android/union/union_native_express
 */
public class NativeController implements NativeExpressAD.NativeExpressADListener{

    public static final String TAG ="NativeController";

    private NativeExpressAD nativeExpressAD;
    private boolean loadMoreAd;//加载更多广告中
    private List<NativeExpressADView> adList=new ArrayList<>();
    private int adCount;
    private NativeLoadMoreListener loadMoreListener;
    private ViewGroup container;
    private NativeStatusAdListener statusAdListener;//预留接口
    private boolean interrupt;//中断请求和广告回调，防止数据加载过多 页面退出时仍发生回调

    /**
     * 加载native 默认宽高
     * @param context
     * @param posid    广告id
     * @param listener 广告加载回调
     */
    public void loadNativeAd(@NonNull Activity context,@NonNull String posid, @IntRange(from = 1,to = 10) int adcount, @NonNull NativeExpressAD.NativeExpressADListener listener) {
        nativeExpressAD = new NativeExpressAD(context, getADSize(), posid, listener);
        setVdieoConfig();
        nativeExpressAD.loadAD(adcount);
    }

    /**
     * 加载native 自定义宽高
     * @param context
     * @param posid    广告id
     * @param adSize   new ADSize(w, h); 自定义宽高
     * @param listener 广告加载回调
     */
    public void loadNativeAd(@NonNull Activity context, @NonNull String posid, @IntRange(from = 1,to = 10)int adcount,@NonNull ADSize adSize, @NonNull NativeExpressAD.NativeExpressADListener listener) {
        nativeExpressAD = new NativeExpressAD(context, adSize, posid, listener);
        setVdieoConfig();
        nativeExpressAD.loadAD(adcount);
    }

    //默认直接展示广告 单条广告使用
    public void loadAndShowNativeAd(@NonNull Activity context,@NonNull ViewGroup viewGroup ,@NonNull String posid) {
        this.container=viewGroup;
        nativeExpressAD = new NativeExpressAD(context, getADSize(), posid, getStatusListener());
        setVdieoConfig();
        nativeExpressAD.loadAD(1);
    }



    private NativeExpressAD.NativeExpressADListener getStatusListener() {
        return new NativeExpressAD.NativeExpressADListener() {
            @Override
            public void onNoAD(AdError adError) {

            }

            @Override
            public void onADLoaded(List<NativeExpressADView> list) {
                if (list.size()==0) {
                    Log.e(TAG, "onADLoaded: List<NativeExpressADView> list size 0");
                    return;
                }
                if (container.getVisibility() != View.VISIBLE) {
                    container.setVisibility(View.VISIBLE);
                }

                if (container.getChildCount() > 0) {
                    container.removeAllViews();
                }
                NativeExpressADView  nativeAd = list.get(0);
                container.addView(nativeAd);
                nativeAd.render();
            }

            @Override
            public void onRenderFail(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADExposure(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClicked(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADClosed(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

            }

            @Override
            public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

            }
        };
    }

    /**
     *加载10条以上的native 最大限制100条
     * @param context
     * @param posid
     * @param adcount
     */
    public synchronized void loadNativeAdMore(@NonNull Activity context,@NonNull String posid, @IntRange(from = 11,to = 100) int adcount,@NonNull NativeLoadMoreListener listener){
        if (loadMoreAd==true) {
            Log.d(TAG, "loadNativeAdMore: not load over,please wait");
            return;
        }
        loadMoreAd=true;
        adList.clear();
        adCount=adcount;
        this.loadMoreListener=listener;
        nativeExpressAD = new NativeExpressAD(context, getADSize(), posid, this);
        ///参数设置
        setVdieoConfig();
        //
        onRqCount();
    }

    /**
     * 请求广告
     */
    private void onRqCount() {
        if (adCount>=10) {
            adCount=adCount-10;
            nativeExpressAD.loadAD(10);
        }else if(adCount>0&&adCount<10){
            nativeExpressAD.loadAD(adCount);
            adCount=0;
        }else if(adCount==0){
            return;
        }
    }


    private void setVdieoConfig() {
          nativeExpressAD.setVideoOption(initVideoOption());
//        nativeExpressAD.setMinVideoDuration(getMinVideoDuration());
//        nativeExpressAD.setMaxVideoDuration(getMaxVideoDuration());
        nativeExpressAD.setVideoPlayPolicy(VideoOption.AutoPlayPolicy.ALWAYS);
    }


    /**
     * 可选，不设置会使用默认
     *
     * @return 设置视频渲染的配置
     */
    private VideoOption initVideoOption() {
        VideoOption.Builder builder = new VideoOption.Builder();
        builder.setAutoPlayPolicy(1);
        builder.setAutoPlayMuted(true);
        builder.setDetailPageMuted(true);
        return builder.build();
    }

    /**
     * @return 广告宽高自适应
     */
    private ADSize getADSize() {
        int w = ADSize.FULL_WIDTH;
        int h = ADSize.AUTO_HEIGHT;
        return new ADSize(w, h);
    }

    //广点通广告

    @Override
    public void onADLoaded(List<NativeExpressADView> list) {
        if (interrupt) {
            Log.e(TAG, "onADLoaded: reqeust interrupt... will clear data");
            return;
        }
        adList.addAll(list);
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

    /**
     * 重置状态
     */
    private void reset() {
        loadMoreAd = false;
        adCount = 0;
    }

    @Override
    public void onRenderFail(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onRenderSuccess(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADExposure(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClicked(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADClosed(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADLeftApplication(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADOpenOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onADCloseOverlay(NativeExpressADView nativeExpressADView) {

    }

    @Override
    public void onNoAD(AdError adError) {
        reset();
        if (loadMoreListener != null) {
            loadMoreListener.onNoAd(adList);//存在之前加载成功的数据，的可能性
            adList.clear();
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
    }

}
