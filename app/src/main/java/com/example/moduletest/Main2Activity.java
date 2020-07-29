package com.example.moduletest;

import android.os.Bundle;
import android.view.ViewGroup;

import androidx.appcompat.app.AppCompatActivity;

import com.shenxing.admanager.annotate.ADType;
import com.shenxing.admanager.control.manager.ADManager;
import com.shenxing.admanager.control.wm.BannerControllerWM;
import com.shenxing.admanager.control.wm.DrawNativeControllerWM;
import com.shenxing.admanager.control.wm.ExpressDrawControllerWM;
import com.shenxing.admanager.control.wm.FullScreenControllerWM;
import com.shenxing.admanager.control.wm.InteractControllerWM;
import com.shenxing.admanager.control.wm.NativeControllerWm;
import com.shenxing.admanager.control.wm.RewardControllerWM;
import com.shenxing.admanager.control.wm.SplashControllerWM;

public class Main2Activity extends AppCompatActivity {

    public static final String TAG = "MYTest";

    private ViewGroup mContainer;
    private ViewGroup mBannerContainer;
    private ViewGroup mSplashContainer;
    private ViewGroup mDrawNative;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main2);
        mContainer = findViewById(R.id.native_container);
        mBannerContainer = findViewById(R.id.banner_container);
        mSplashContainer = findViewById(R.id.splash_container);
        mDrawNative = findViewById(R.id.video_layout);

    }

    InteractControllerWM interWm;
    private void testInter() {//插屏测试
        interWm=ADManager.create(ADType.UNINTER_WMAD);
//        interWm.height=0f;
//        interWm.width=300f;
        /**
         * 加载一条插屏并展示，不关心回调
         **/
//        interWm.loadExpressAd(this,WMID.interId2);
        /**
         * 加载一条插屏并展示,简单回调
         * **/
       /* interWm.loadExpressAd(this, WMID.interId1, new InteractListener<TTNativeExpressAd>() {
            @Override
            public void onAdDismiss() {
                ILog.d(TAG, "onAdDismiss: ");
            }

            @Override
            public void onAdShow(View view, int type) {
                ILog.d(TAG, "onAdShow: ");
            }

            @Override
            public void onLoadError(int errorCode, String extraCode) {
                ILog.d(TAG, "onLoadError: "+extraCode+":"+extraCode);
            }

            @Override
            public void onAdLoad(TTNativeExpressAd ttNativeExpressAd) {//无需调用show
                ILog.d(TAG, "onAdLoad: ");
            }


            @Override
            public void onRenderFail(View view, String msg, int code) {
                ILog.d(TAG, "onRenderFail: ");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {//展示广告
                ILog.d(TAG, "onRenderSuccess: ");
            }
        });*/

        /**
         * 加载插屏，自己处理回调
         * **/
     /*   interWm.loadExpressAd(this, WMID.interId1,3, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int code, String error) {
                ILog.d(TAG, "onLoadError: "+code+":"+error);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                ILog.d(TAG, "onNativeExpressAdLoad: "+ads.size());
                if (ads == null || ads.size() == 0) {
                    return;
                }
                for (final TTNativeExpressAd ad : ads) {
                    interWm.bindAdListener(ad, new TTNativeExpressAd.AdInteractionListener() {
                        @Override
                        public void onAdDismiss() {

                        }

                        @Override
                        public void onAdClicked(View view, int i) {

                        }

                        @Override
                        public void onAdShow(View view, int i) {

                        }

                        @Override
                        public void onRenderFail(View view, String s, int i) {

                        }

                        @Override
                        public void onRenderSuccess(View view, float v, float v1) {
                            ad.showInteractionExpressAd(Main2Activity.this);
                        }
                    });
                    ad.render();
                }

            }
        });*/
    }


    ExpressDrawControllerWM expresswm;
    private void testExpress() {//个性化模板Draw信息流
        expresswm=ADManager.create(ADType.EXPRESS_DRAW);
        /**
         * 加载并展示 一条个性化模板
         **/
       /* expresswm.loadAndShowExpressAd(this, WMID.express_draw, mDrawNative, new ExpressDrawSimpleListener() {
            @Override
            public void onLoadError(int errorCode, String extraCode) {
                ILog.d(TAG, "onLoadError: "+errorCode+":"+extraCode);
            }

            @Override
            public void onVideoError(int errorCode, int extraCode) {
                ILog.d(TAG, "onVideoError: "+errorCode+":"+errorCode);
            }

            @Override
            public void onVideoAdComplete() {
                ILog.d(TAG, "onVideoAdComplete: ");
            }

            @Override
            public void onRenderFail(View view, String msg, int code) {
                ILog.d(TAG, "onRenderFail: ");
            }

            @Override
            public void onRenderSuccess(View view, float width, float height) {
                ILog.d(TAG, "onRenderSuccess: ");
            }

            @Override
            public void onAdShow(View view, int type) {
                ILog.d(TAG, "onAdShow: ");
            }
        });*/
        /**
         * 加载 多条个性化模板，自行展示。回调开发者自己处理
         **/
        /*expresswm.loadExpressAd(this, WMID.express_draw, 3, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {

            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                for (TTNativeExpressAd ad : ads) {
                    expresswm.bindAdListener(ads, new ExpressDrawSimpleListener() {
                        @Override
                        public void onLoadError(int errorCode, String extraCode) {

                        }

                        @Override
                        public void onVideoError(int errorCode, int extraCode) {

                        }

                        @Override
                        public void onVideoAdComplete() {

                        }

                        @Override
                        public void onRenderFail(View view, String msg, int code) {

                        }

                        @Override
                        public void onRenderSuccess(View view, float width, float height) {
//                      viewGroup.addView(view);
                        }

                        @Override
                        public void onAdShow(View view, int type) {

                        }
                    });
                    ad.render();
                }
            }
        });*/
    }

    DrawNativeControllerWM drawWm;
    private void testDraw() {//draw 信息流 若要替换暂停图片 请在drawable-xx中放入 dislike_icon.png
        drawWm=ADManager.create(ADType.Draw_WMAD);
        /**
         *加载并展示draw信息流
         **/
        /*drawWm.loadAndShowDrawAd(this, WMID.draw, mDrawNative, new DrawNativeSimpleListener() {
            @Override
            public void onError(int code, String error) {
                ILog.d(TAG, "onError: "+code+":"+error);
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> list) {
                ILog.d(TAG, "onDrawFeedAdLoad: "+list.size());
            }
        });*/
        /**
         *加载并展示draw信息流，自行处理展示
         **/
       /* drawWm.loadDrawAd(this, WMID.draw, 3, new DrawNativeSimpleListener() {
            @Override
            public void onError(int code, String error) {
                ILog.d(TAG, "onError: "+code+":"+error);
            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
                ILog.d(TAG, "onDrawFeedAdLoad: "+ads.size());
                mContainer.removeAllViews();
                mContainer.addView(ads.get(0).getAdView());
                drawWm.bindAdViewAndAction(ads.get(0),mContainer);
            }
        });*/
        /**
         *加载并展示draw信息流，自行处理回调
         **/
       /* drawWm.LoadDrawAd(this, WMID.draw, 3, new TTAdNative.DrawFeedAdListener() {
            @Override
            public void onError(int code, String error) {

            }

            @Override
            public void onDrawFeedAdLoad(List<TTDrawFeedAd> ads) {
                for (TTDrawFeedAd ad : ads) {
                    ad.setActivityForDownloadApp(Main2Activity.this);
                    //点击监听器必须在getAdView之前调
                    ad.setDrawVideoListener(new TTDrawFeedAd.DrawVideoListener() {
                        @Override
                        public void onClickRetry() {//点击重试按钮
                            ILog.d("drawss", "onClickRetry!");
                        }

                        @Override
                        public void onClick() {//点击查看详情或下载按钮
                            ILog.d("drawss", "onClick download or view detail page ! !");
                        }
                    });
                    ad.setCanInterruptVideoPlay(true);//点击视频是否暂停
                    ad.setPauseIcon(BitmapFactory.decodeResource(getResources(), com.shenxing.admanager.R.drawable.dislike_icon), 60);
                   drawWm.bindAdViewAndAction(ad,viewGroup);
                }
                //展示一条
//                container.addView(ads.get(0).getAdView());
            }
        });*/
    }

    FullScreenControllerWM fullScreenWM;
    private void testFullVideo() {//全屏广告
        fullScreenWM=ADManager.create(ADType.FULLSCREEN_WMAD);
//        fullScreenWM.isExpress=true; //是否为模板广澳
        /**
         *展示激励视频，处理简单回调
         **/
        /*fullScreenWM.preAndShowScreenAd(this, WMID.fullIdv,
                TTAdConstant.VERTICAL, new FullScreenSimpleListener<TTFullScreenVideoAd,String>() {
            @Override
            public void onAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                ILog.d(TAG, "onAdLoad: ");
            }

            @Override
            public void onAdError(String error) {
                ILog.d(TAG, "onAdError: "+error);
            }
            

            @Override
            public void onAdClose() {
                ILog.d(TAG, "onAdClose: ");
            }

            @Override
            public void onVideoComplete() {
                ILog.d(TAG, "onVideoComplete: ");
            }

            @Override
            public void onSkippedVideo() {
                ILog.d(TAG, "onSkippedVideo: ");
            }
        });*/

        /**
         *预加载激励视频，回调需要自行处理
         **/
        /*fullScreenWM.preFullScreenAd(this, WMID.fullIdv, TTAdConstant.VERTICAL,new FullScreenSimpleListener<TTFullScreenVideoAd,String>() {
            @Override
            public void onAdLoad(TTFullScreenVideoAd ttFullScreenVideoAd) {
                ILog.d(TAG, "onAdLoad: ");
//                ttFullScreenVideoAd.showFullScreenVideoAd(Main2Activity.this);
//                fullScreenWM.showAd();
                fullScreenWM.showAd(TTAdConstant.RitScenes.HOME_GET_BONUS,"Just write something");
            }

            @Override
            public void onAdError(String error) {
                ILog.d(TAG, "onAdError: ");
            }

            @Override
            public void onAdClose() {
                ILog.d(TAG, "onAdClose: ");
            }

            @Override
            public void onVideoComplete() {
                ILog.d(TAG, "onVideoComplete: ");
            }

            @Override
            public void onSkippedVideo() {
                ILog.d(TAG, "onSkippedVideo: ");
            }
        });*/
       //以下省略展示...
    }

    RewardControllerWM rewardwm;
    private void testReward() {//激励视频
        rewardwm=ADManager.create(ADType.REWARD_WMAD);
//        rewardwm.isExpress=true; //是否为模板广告
        /**
         *预加载激励视频，自行展示
         **/
        /*rewardwm.preRewardVideo(this, WMID.rewardIdv, new RewardSimpleListener<TTRewardVideoAd,String>() {
            @Override
            public void onAdLoad(TTRewardVideoAd ttRewardVideoAd) {//广告加载成功
                ILog.d(TAG, "onAdLoad: ");
//                ttRewardVideoAd.showRewardVideoAd(Main2Activity.this);
                rewardwm.showAd();
//                rewardwm.showAd(TTAdConstant.RitScenes.CUSTOMIZE_SCENES,"Test");
            }

            @Override
            public void onAdError(String error) {//广告加载错误
                ILog.d(TAG, "onAdError: "+error);
            }

            //激励播放完成，奖励回调
            @Override
            public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                String ILogString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                        " name:" + rewardName;
                ILog.e(TAG, "Callback --> " + ILogString);
            }

            @Override
            public void onAdClose() {//用户手动关闭广告
                ILog.d(TAG, "onAdClose: ");
            }

            @Override
            public void onVideoComplete() {//视频播放完成
                ILog.d(TAG, "onVideoComplete: ");
            }

            @Override
            public void onSkippedVideo() {//跳过广告
                ILog.d(TAG, "onSkippedVideo: ");
            }
        }, TTAdConstant.VERTICAL);*/
        
        /**
         * 不关心回调
         * **/
//        rewardwm.preAndShowRewardAd(this,WMID.rewardIdh, TTAdConstant.HORIZONTAL);
        /**
         * 加载完成后展示广告
         * **/
        /*rewardwm.preAndShowRewardAd(this, WMID.rewardIdh, TTAdConstant.HORIZONTAL, new RewardSimpleListener<TTRewardVideoAd,String>() {
            
            @Override
            public void onAdLoad(TTRewardVideoAd ttRewardVideoAd) {
                //此处不要再写展示了
                ILog.d(TAG, "onAdLoad: ");
            }

            @Override
            public void onAdError(String s) {
                ILog.d(TAG, "onAdError: ");
            }

            @Override
            public void onRewardVerify(boolean rewardVerify, int rewardAmount, String rewardName) {
                String ILogString = "verify:" + rewardVerify + " amount:" + rewardAmount +
                        " name:" + rewardName;
                ILog.e(TAG, "Callback --> " + ILogString);
            }

            @Override
            public void onAdClose() {
                ILog.d(TAG, "onAdClose: ");
            }

            @Override
            public void onVideoComplete() {
                ILog.d(TAG, "onVideoComplete: ");
            }

            @Override
            public void onSkippedVideo() {
                ILog.d(TAG, "onSkippedVideo: ");
            }
        });*/
    }

    SplashControllerWM splashwm;
    private void testSplash() {//开屏
        splashwm=ADManager.create(ADType.SPLASH_WMAD);
        splashwm.needFinish=true;//关闭当前界面
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
                View view = ttSplashAd.getSplashView();
                if (view != null && container != null && !isFinishing()) {
                    container.removeAllViews();
                    //把SplashView 添加到ViewGroup中,注意开屏广告view：width >=70%屏幕宽；height >=50%屏幕高
                    container.addView(view);
                    //设置不开启开屏广告倒计时功能以及不显示跳过按钮,如果这么设置，您需要自定义倒计时逻辑
                    //ad.setNotAllowSdkCountdown();
                }
                 splashwm.setTagIntent();
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
    private void testBanner() {//banner广告
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
                ILog.d(TAG, "onError: ");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                ILog.d(TAG, "onNativeExpressAdLoad: ");
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                bannerwm.bindAdListener(mTTAd,mBannerContainer);
                mTTAd.render();
            }
        });*/

        /**
         * 加载1-3条banner，开发者自己处理回调。
         * **/
      /*  bannerwm.loadBannerMore(this, WMID.bannerId6, 2, new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                ILog.d(TAG, "onError: ");
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> ads) {
                ILog.d(TAG, "onNativeExpressAdLoad: "+ads.size());
                if (ads == null || ads.size() == 0) {
                    return;
                }
                TTNativeExpressAd mTTAd = ads.get(0);
                bannerwm.bindAdListener(mTTAd,mBannerContainer);
                mTTAd.render();
            }
        });*/
    }

    private NativeControllerWm nativeWm;
    private void testNative() {//信息流原生
        nativeWm = ADManager.create(ADType.NATIVE_WMAD);
//        controllerWm.width=250f;//非必传 适配建议传上
//        controllerWm.height=200f;//非必传
        /**
         * 1-3条展示 自行处理回调
         **/
      /*  nativeWm.preNative(this, WMID.nativeId, 1,  new TTAdNative.NativeExpressAdListener() {
            @Override
            public void onError(int i, String s) {
                ILog.d(TAG, "onError: "+i+"...."+s);
            }

            @Override
            public void onNativeExpressAdLoad(List<TTNativeExpressAd> list) {
                ILog.e(TAG, "onNativeExpressAdLoad: "+list.size());
                if (list == null || list.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd=list.get(0);
                controllerWm.bindAdListener(mTTAd,mContainer);
                mTTAd.render();//调用render开始渲染广告
            }
        });*/

        /**
         * 不关心回调,单条展示
         **/
        /*nativeWm.width=250f;//非必传
        nativeWm.height=200f;//非必传
        nativeWm.preAndShowNative(this,WMID.nativeId,mContainer);*/

        /**
         * 调用多条数据,自行处理回调， 必须在页面 销毁时调用destroy();方法。 否则易内存泄露
         **/
        /*nativeWm.preNativeMore(this, WMID.nativeId, 50, new NativeLoadMoreListener<TTNativeExpressAd>() {
            @Override
            public void onAdLoad(List<TTNativeExpressAd> list) {//根据自己需求改
                ILog.e(TAG, "onNativeExpressAdLoad: "+list.size());
                if (list == null || list.size() == 0){
                    return;
                }
                TTNativeExpressAd mTTAd=list.get(0);
                controllerWm.bindAdListener(mTTAd,mContainer);
                mTTAd.render();//调用render开始渲染广告
            }

            @Override
            public void onLoadError(List<TTNativeExpressAd> list) {
                ILog.e(TAG, "onNativeExpressAdLoad: " + list.size());
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (nativeWm != null) {
            nativeWm.destroy();//中断请求
        }
    }
}
