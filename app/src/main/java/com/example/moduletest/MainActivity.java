package com.example.moduletest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.qq.e.ads.nativ.NativeExpressADView;
import com.shenxing.admanager.annotate.ADType;
import com.shenxing.admanager.callback.NativeLoadMoreListener;
import com.shenxing.admanager.control.gdt.Banner2Controller;
import com.shenxing.admanager.control.gdt.Interstitial2Controller;
import com.shenxing.admanager.control.gdt.NativeController;
import com.shenxing.admanager.control.gdt.RewardVideoController;
import com.shenxing.admanager.control.gdt.SplashADController;
import com.shenxing.admanager.control.manager.ADManager;

import java.util.List;

import hugo.weaving.DebugLog;

import static com.example.moduletest.PositionId.NATIVE_EXPRESS_POS_ID_VIDEO;
import static com.example.moduletest.PositionId.UNIFIED_BANNER_POS_ID;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MYTest";
    private ViewGroup adBanner;
    private ViewGroup adNative;

    private ViewGroup adContainer;
    private TextView skipTv;
    private static final String SKIP_TEXT = "点击跳过 %d";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        adBanner = findViewById(R.id.ad_banner);
        adNative = findViewById(R.id.ad_native);
        skipTv = findViewById(R.id.skip_view);
        adContainer = findViewById(R.id.ad_coutainer);
    }
    //开屏展示
    SplashADController splash;
    private void splash() {
        splash= ADManager.create(ADType.SPLASH_AD);
        splash.setTagIntent(new Intent(this,Main2Activity.class));
        /**
         * 非预加载 开发者不关心回调
         **/
//        splash.fetchSplashADShow(this, adContainer, skipTv, SPLASH_POS_ID,  3000);

        /**
         * 预加载 开发者不关心回调
         **/
        /*splash.fetchSplashADOnly(this, skipTv, SPLASH_POS_ID,  3000);
        adContainer.postDelayed(new Runnable() {
            @Override
            public void run() {
                splash.showSplashAD(adContainer);
            }
        }, 3000);*/
        //非预加载，关心回调
       /* splash.fetchSplashADShow(this, adContainer, skipTv, SPLASH_POS_ID,  3000,new SplashADListener() {
            @Override
            public void onADDismissed() {//点击跳过按钮
                Log.d(TAG, "onADDismissed: ");
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.d(TAG, "onNoAD: ");
            }

            @Override
            public void onADPresent() {
                Log.d(TAG, "onADPresent: ");
            }

            @Override
            public void onADClicked() {
                Log.d(TAG, "onADClicked: ");
            }

            @Override
            public void onADTick(long millisUntilFinished) {
                Log.d(TAG, "onADTick: ");
                if (skipTv != null) {
                    skipTv.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
                }
            }

            @Override
            public void onADExposure() {
                Log.d(TAG, "onADExposure: ");
            }

            @Override
            public void onADLoaded(long l) {
                Log.d(TAG, "onADLoaded: ");
            }
        });*/
        //预加载，关心回调
        /*splash.fetchSplashADOnly(this, skipTv, SPLASH_POS_ID, 3000,,new SplashADListener() {
            @Override
            public void onADDismissed() {
                Log.d(TAG, "onADDismissed: ");
            }

            @Override
            public void onNoAD(AdError adError) {
                Log.d(TAG, "onNoAD: ");
            }

            @Override
            public void onADPresent() {
                Log.d(TAG, "onADPresent: ");
            }

            @Override
            public void onADClicked() {
                Log.d(TAG, "onADClicked: ");
            }

            @Override
            public void onADTick(long l) {
                Log.d(TAG, "onADTick: ");
            }

            @Override
            public void onADExposure() {
                Log.d(TAG, "onADExposure: ");
            }

            @Override
            public void onADLoaded(long l) {
                Log.d(TAG, "onADLoaded: ");
                splash.showSplashAD(adContainer);
            }
        });*/
    }

    //激励视频
    private void rewardTest() {
        //----------注意先后调用顺序
        final RewardVideoController rewardVideo= ADManager.create(ADType.REWARD_AD);
//      无回掉
//        rewardVideo.preAndShow(this,"6040295592058680",false);
        //预加载
//        rewardVideo.onPrepareAd(this,"6040295592058680",true, listener);
        //直接展示广告
//        rewardVideo.preAndShow(this,"6040295592058680",true, listener);

    }

    //插屏
    private void inters() {
        //插屏2.0
        final Interstitial2Controller interstitial2 = ADManager.create(ADType.UNINTER2_AD);
        //简单用法 非全屏
//        interstitial2.preAndShowAD(this,UNIFIED_VIDEO_VIDEO_ID_LARGE_VERTICAL);
        //简单用法 全屏
//        interstitial2.preAndShowFullScreenAD(this,UNIFIED_VIDEO_VIDEO_ID_LARGE_VERTICAL);

        // 回调onADReceive-》 调用 showInterAD
        //加载非全屏
//        interstitial2.prepareAD(this, UNIFIED_VIDEO_VIDEO_ID_LARGE_VERTICAL, listener);

        // 回调onADReceive-》 调用 showFullScreenAD
        // 加载全屏2.0
//        interstitial2.prepareFullScreenAD(this,UNIFIED_VIDEO_VIDEO_ID_LARGE_VERTICAL,listener);
    }

    private void banner_native() {
        //加载banner
        Banner2Controller banner2 = ADManager.create(ADType.BANNER2_AD);
        //加载native
        NativeController natives = ADManager.create(ADType.NATIVE_AD);

        if (banner2 != null) {
//            banner2.setBannerSize(500,600);//
//            简单用法
            banner2.preAndShowBanner(this, adBanner, UNIFIED_BANNER_POS_ID);
        }

        if (natives != null) {
            //仅加载一条native
//            natives.loadAndShowNativeAd(this,adNative,NATIVE_EXPRESS_POS_ID_VIDEO);
            //加载多条，必须实现回调
//            natives.loadNativeAd(this, NATIVE_EXPRESS_POS_ID_VIDEO, 10, listener);
            //加载更多广告 记得调用 natives.destroy(); 在activiy stop/onDestory中
            natives.loadNativeAdMore(this, NATIVE_EXPRESS_POS_ID_VIDEO, 55, new NativeLoadMoreListener<NativeExpressADView>() {
                @DebugLog
                @Override
                public void onAdLoad(List<NativeExpressADView> list) {
                    Log.e(TAG, "onAdLoad: " + list.size());
                    adNative.addView(list.get(0));
                    list.get(0).render();
                }

                @Override
                public void onLoadError(List<NativeExpressADView> list) {
                    Log.e(TAG, "onNoAd: " + list.size());
                }
            });
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
