package com.shenxing.admanager.control.wm;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.IntRange;
import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.AdSlot;
import com.bytedance.sdk.openadsdk.TTAdNative;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.bytedance.sdk.openadsdk.TTDrawFeedAd;
import com.bytedance.sdk.openadsdk.TTNativeAd;
import com.shenxing.admanager.R;
import com.shenxing.admanager.callback.DrawNativeSimpleListener;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * draw 信息流 ，仅支持竖屏
 */
public class DrawNativeControllerWM {

    public static final String TAG="DrawNativeControllerWM";

    private WeakReference<Activity> weakReference;
    private ViewGroup container;
    private AdSlot adSlot;
    private TTAdNative mTTAdNative;
    private DrawNativeSimpleListener simpleListener;
    private int adNum=-1;//暂时最多仅支持3条

    /*public void setSimpleListener(DrawNativeSimpleListener simpleListener) {
        this.simpleListener = simpleListener;
    }*/

    //1 有自定义回调返回，2 公开绑定，3 原生回调绑定

    /**
     * action1
     * 简单回调 加载一条
     * @param context
     * @param container
     * @param simpleListener
     */
    public void loadAndShowDrawAd(@NonNull Activity context,
                           @NonNull String coidId,
                            ViewGroup container,
                           DrawNativeSimpleListener simpleListener){
        this.simpleListener=simpleListener;
        reSetAdNum();
        defLoadDrawAd(context,coidId,container,null);
    }

    /**
     * action2
     * 简单回调 多条数据回调
     * @param context
     * @param adNum
     * @param simpleListener
     */
    public void loadDrawAd(@NonNull Activity context,
                           @NonNull String coidId,
                           @IntRange(from = 2,to = 3) int adNum,
                           DrawNativeSimpleListener simpleListener){
        this.simpleListener=simpleListener;
        this.adNum=adNum;
        defLoadDrawAd(context,coidId,null,null);

    }

    public void LoadDrawAd(@NonNull Activity context,
                               @NonNull String coidId,
                               @IntRange(from = 1,to = 3) int adNum,
                               TTAdNative.DrawFeedAdListener listener){
        this.adNum=adNum;
        defLoadDrawAd(context,coidId,null,listener);
    }

    /**
     * action3
     * 基类 回调自行处理，仅支持一条
     * @param context
     * @param container
     * @param listener
     */
    private void defLoadDrawAd(@NonNull Activity context,
                              @NonNull String coidId,
                             ViewGroup container,
                           TTAdNative.DrawFeedAdListener listener){
        if (weakReference==null||container==null) {
            weakReference=new WeakReference<>(context);
            this.container=container;
            mTTAdNative= TTAdSdk.getAdManager().createAdNative(weakReference.get());
        }
         adSlot = new AdSlot.Builder()
                .setCodeId(coidId)
                .setSupportDeepLink(true)
                .setImageAcceptedSize(1080, 1920)
                .setAdCount(adNum==-1?1:adNum) //请求广告数量为1到3条
                .build();
        if (listener==null) {
            //加载绑定
            bindAd();
        }else {
            mTTAdNative.loadDrawFeedAd(adSlot, listener);
        }
    }

    private void bindAd() {
        mTTAdNative.loadDrawFeedAd(adSlot, new TTAdNative.DrawFeedAdListener() {
            @Override
            public void onError(int code, String message) {
                Log.d(TAG, message);
                reSetAdNum();
                if (simpleListener != null) {
                    simpleListener.onError(code,message);
                }
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
                reSetAdNum();
                if (ads == null || ads.isEmpty()) {
                    return;
                }

                for (TTDrawFeedAd ad : ads) {
                    ad.setActivityForDownloadApp(weakReference.get());
                    //点击监听器必须在getAdView之前调
                    ad.setDrawVideoListener(new TTDrawFeedAd.DrawVideoListener() {
                        @Override
                        public void onClickRetry() {//点击重试按钮
                            Log.d("drawss", "onClickRetry!");
                        }

                        @Override
                        public void onClick() {//点击查看详情或下载按钮
                            Log.d("drawss", "onClick download or view detail page ! !");
                        }
                    });
                    ad.setCanInterruptVideoPlay(true);
                    ad.setPauseIcon(BitmapFactory.decodeResource(weakReference.get().getResources(), R.drawable.dislike_icon), 60);
                }
                if (simpleListener != null) {// 注意非空ads判断
                    simpleListener.onDrawFeedAdLoad(ads);
                }
                //获取广告视频播放的view并放入广告容器中
                if (container != null) {
                    container.addView(ads.get(0).getAdView());
                    bindAdViewAndAction(ads.get(0),container);
                }
            }
        });
    }

    /**
     * 重置广告请求数量，封装成方法，为了方便理解
     */
    private void reSetAdNum() {
        adNum = -1;
    }

    //绑定广告行为, 公开此方法，注意当请求多条时需要自行调用此方法
    public void bindAdViewAndAction(TTDrawFeedAd ad,ViewGroup mAdContainer){

        View actionView = weakReference.get().getLayoutInflater().inflate(R.layout.native_draw_action_item,null,false);
        TextView title = actionView.findViewById(R.id.tv_title);
        title.setText(ad.getTitle());

        TextView desc = actionView.findViewById(R.id.tv_desc);
        desc.setText(ad.getDescription());

        Button action = actionView.findViewById(R.id.button_creative);
        action.setText(ad.getButtonText());

        int margin = (int) dip2Px(weakReference.get(), 10);
        FrameLayout.LayoutParams lp1 = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp1.gravity = Gravity.START | Gravity.BOTTOM;
        lp1.leftMargin = margin;
        lp1.bottomMargin = margin;
        mAdContainer.addView(actionView, lp1);

        //响应点击区域的设置，分为普通的区域clickViews和创意区域creativeViews
        //clickViews中的view被点击会尝试打开广告落地页；creativeViews中的view被点击会根据广告类型
        //响应对应行为，如下载类广告直接下载，打开落地页类广告直接打开落地页。
        //注意：ad.getAdView()获取的view请勿放入这两个区域中。
        List<View> clickViews = new ArrayList<>();
        clickViews.add(title);
        clickViews.add(desc);
        List<View> creativeViews = new ArrayList<>();
        creativeViews.add(action);
        ad.registerViewForInteraction(mAdContainer, clickViews, creativeViews, new TTNativeAd.AdInteractionListener() {
            @Override
            public void onAdClicked(View view, TTNativeAd ad) {
                Log.d(TAG, "onAdClicked: ");
            }

            @Override
            public void onAdCreativeClick(View view, TTNativeAd ad) {
                Log.d(TAG, "onAdCreativeClick: ");
            }

            @Override
            public void onAdShow(TTNativeAd ad) {
                Log.d(TAG, "bind onAdShow: ");
            }
        });
    }

    private float dip2Px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return dipValue * scale + 0.5f;
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
