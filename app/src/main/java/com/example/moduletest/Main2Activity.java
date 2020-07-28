package com.example.moduletest;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.shenxing.admanager.annotate.ADType;
import com.shenxing.admanager.control.manager.ADManager;
import com.shenxing.admanager.control.wm.BannerControllerWM;
import com.shenxing.admanager.control.wm.NativeControllerWm;
import com.shenxing.admanager.control.wm.RewardControllerWM;
import com.shenxing.admanager.control.wm.SplashControllerWM;

public class Main2Activity extends AppCompatActivity {

    public static final String TAG = "Main2Activity";

    private ViewGroup mContainer;
    private ViewGroup mBannerContainer;
    private ViewGroup mSplashContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        mContainer = findViewById(R.id.native_container);
        mBannerContainer = findViewById(R.id.banner_container);
        mSplashContainer = findViewById(R.id.splash_container);

//        testReward();
    }

    RewardControllerWM rewardwm;
    private void testReward() {
        rewardwm=ADManager.create(ADType.REWARD_WMAD);
    }

    SplashControllerWM splashwm;
    private void testSplash() {
        splashwm=ADManager.create(ADType.SPLASH_WMAD);
        splashwm.needFinish=true;
//      可选优先以Intent为准  splashwm.setTagIntent(new Intent(this,MainActivity.class).putExtra("test",2134));
        /**
         *开屏自动跳转，不处理回调
         **/
//        splashwm.loadSplash(this,mSplashContainer,WMID.splashId,MainActivity.class);

        /**
        *开屏手动跳转，自己处理回调
        **/
        /*splashwm.loadSplash(this, "we", new TTAdNative.SplashAdListener() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onTimeout() {

            }

            @Override
            public void onSplashAdLoad(TTSplashAd ttSplashAd) {
                //**** 省略 addView代码
                splashwm.bindActive(ttSplashAd);//务必设置Intent
            }
        });*/

        /**
         * 个性化开屏广告
         * */
//        splashwm.loadIndividuatSplash(this,mSplashContainer,WMID.splashId2,MainActivity.class);
        /**
         * 个性化开屏广告 自定义大小宽高
         * */
//        splashwm.loadIndividuatSplash(this,mSplashContainer,WMID.splashId2,300f,500f,MainActivity.class);
    }

    BannerControllerWM bannerwm;
    private void testBanner() {
        bannerwm = ADManager.create(ADType.BANNER_WMAD);
        /*bannerwm.width=100f; 可选
        bannerwm.height=200f;  可选*/
        /**
         * 加载一条banner，开发者不想处理回调。
         * */
//        bannerwm.loadBanner(this,WMID.bannerId3,mBannerContainer);

        /**
         * 加载一条banner，开发者自己处理回调。
         * **/
        /*bannerwm.loadBanner(this, WMID.bannerId1, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.d(TAG, "onNativeExpressAdLoad: ");
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                bannerwm.bindAdListener(mTTAd,mBannerContainer);
                mTTAd.render();
            }
        });*/

        /**
         * 加载2-3条banner，开发者自己处理回调。
         * **/
      /*  bannerwm.loadBannerMore(this, WMID.bannerId6, 2, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: ");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                Log.d(TAG, "onNativeExpressAdLoad: "+ads.size());
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                bannerwm.bindAdListener(mTTAd,mBannerContainer);
                mTTAd.render();
            }
        });*/


    }

    private NativeControllerWm controllerWm;
    private void testNative() {
        controllerWm = ADManager.create(ADType.NATIVE_WMAD);
        //1-3条展示 自行处理回调
//        controllerWm.width=250f;//非必传 适配建议传上
//        controllerWm.height=200f;//非必传
      /*  controllerWm.preNative(this, WMID.nativeId, 1,  new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                Log.d(TAG, "onError: "+i+"...."+s);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                Log.e(TAG, "onNativeExpressAdLoad: "+list.size());
                if (list == null || list.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd=list.get(0);
                controllerWm.bindAdListener(mTTAd,mContainer);
                mTTAd.render();//调用render开始渲染广告
            }
        });*/
        //自行处理回调

        //不关心回调,单条展示
        /*controllerWm.width=250f;//非必传
        controllerWm.height=200f;//非必传
        controllerWm.preAndShowNative(this,WMID.nativeId,mContainer);*/

        //调用多条数据,自行处理回调， 必须在页面 销毁时调用destroy();方法。 否则易内存泄露
        /*controllerWm.preNativeMore(this, WMID.nativeId, 50, new NativeLoadMoreListener<TTNativeExpressAd>() {
            @Override
            public void onAdLoad(List<TTNativeExpressAd> list) {//根据自己需求改
                Log.e(TAG, "onNativeExpressAdLoad: "+list.size());
                if (list == null || list.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd=list.get(0);
                controllerWm.bindAdListener(mTTAd,mContainer);
                mTTAd.render();//调用render开始渲染广告
            }

            @Override
            public void onLoadError(List<TTNativeExpressAd> list) {
                Log.e(TAG, "onNativeExpressAdLoad: " + list.size());
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (controllerWm != null) {
            controllerWm.destroy();//中断请求
        }
    }
}
