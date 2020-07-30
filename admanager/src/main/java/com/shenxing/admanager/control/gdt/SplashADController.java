package com.shenxing.admanager.control.gdt;

import android.app.Activity;
import android.content.Intent;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.qq.e.comm.util.AdError;
import com.shenxing.admanager.doc.GDTDocument;
import com.shenxing.admanager.utils.ILog;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 具体方法参数意义 参考：https://developers.adnet.qq.com/doc/android/union/union_splash
 */
public class SplashADController {

    public static final String TAG="SplashADController";

    private SplashAD splashAD;//有效期30min
    private Intent tagIntent;
    private WeakReference<Activity> weakReference;

    private TextView skipTv;
    private static final String SKIP_TEXT = "点击跳过 %d";

    /**
     * 拉取开屏广告，开屏广告的构造方法有3种，详细说明请参考开发者文档。
     *
     * @param activity      展示广告的activity
     * @param adContainer   展示广告的大容器
     * @param skipContainer 自定义的跳过按钮：传入该view给SDK后，SDK会自动给它绑定点击跳过事件。SkipView的样式可以由开发者自由定制，其尺寸限制请参考activity_splash.xml或者接入文档中的说明。
     * ---skipContainer 必须可见，xml宽高大小必须大于3dp
     * @param posId         广告位ID
     * @param adListener    广告状态监听器
     * @see GDTDocument listener解释 -》122colum
     * @param fetchDelay    拉取广告的超时时长：取值范围[3000, 5000]，设为0表示使用广点通SDK默认的超时时长。
     *
     */
    public void fetchSplashADShow(@NonNull Activity activity, @NonNull ViewGroup adContainer, @NonNull TextView skipContainer,
                               @NonNull String posId,  @NonNull int fetchDelay,@NonNull SplashADListener adListener) {
        deffetchSplashAD(activity,adContainer,skipContainer,posId,fetchDelay,adListener);
    }

    //预加载，不展示广告
    public void fetchSplashADOnly(@NonNull Activity activity, @NonNull TextView skipContainer,
                                  @NonNull String posId, @NonNull int fetchDelay,@NonNull SplashADListener adListener) {
        deffetchSplashAD(activity,null,skipContainer,posId,fetchDelay,adListener);
    }

    //以下为简单用法
    public void fetchSplashADShow(@NonNull Activity activity, @NonNull ViewGroup adContainer, @NonNull TextView skipContainer,
                                  @NonNull String posId,  @NonNull int fetchDelay) {
        deffetchSplashAD(activity,adContainer,skipContainer,posId,fetchDelay,null);
    }

    public void fetchSplashADOnly(@NonNull Activity activity, @NonNull TextView skipContainer,
                                  @NonNull String posId, @NonNull int fetchDelay) {
        deffetchSplashAD(activity,null,skipContainer,posId,fetchDelay,null);
    }

    //基类
    private void deffetchSplashAD(@NonNull Activity activity,  ViewGroup adContainer, @NonNull TextView skipContainer,
                                  @NonNull String posId,  @NonNull int fetchDelay, SplashADListener adListener){
        if (weakReference==null) {
            weakReference=new WeakReference<>(activity);
        }
        skipTv=skipContainer;
        splashAD = new SplashAD(activity, skipContainer, posId, adListener==null?getSplashListener():adListener, fetchDelay);
        if (adContainer!=null) {
            splashAD.fetchAndShowIn(adContainer);
        }else {
            splashAD.fetchAdOnly();
        }
    }

//    用于跳转
    public void setTagIntent(Intent tagIntent) {
        this.tagIntent = tagIntent;
    }

    private SplashADListener getSplashListener(){
        return new SplashADListener() {
            @Override
            public void onADDismissed() {
                ILog.d(TAG, "onADDismissed: ");
                goToMainActivity();
            }

            @Override
            public void onNoAD(AdError adError) {
                ILog.d(TAG, "onNoAD: "+adError.getErrorMsg());
                goToMainActivity();
            }

            @Override
            public void onADPresent() {
                ILog.d(TAG, "onADPresent: ");
            }

            @Override
            public void onADClicked() {
                ILog.d(TAG, "onADClicked: ");
            }

            @Override
            public void onADTick(long millisUntilFinished) {
                ILog.d(TAG, "onADTick: ");
                if (skipTv != null) {
                    skipTv.setText(String.format(SKIP_TEXT, Math.round(millisUntilFinished / 1000f)));
                }
            }

            @Override
            public void onADExposure() {
                ILog.d(TAG, "onADExposure: ");
            }

            @Override
            public void onADLoaded(long l) {
                ILog.d(TAG, "onADLoaded: ");
            }
        };
    }

    private void goToMainActivity() {
        try {
            if (tagIntent != null) {//优先以Intent为准
                weakReference.get().startActivity(tagIntent);
                needFinish();
                return;
            }
            else ILog.e(TAG, "not set intent, can't new task");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //是否需要关闭当前页面
    private void needFinish() {
        if (tagIntent!=null
                && weakReference != null
                && weakReference.get() != null
        ) {
            weakReference.get().finish();
            weakReference.clear();
        }
    }


    /**
     * 注意注意：必须要预调用加载 fetchSplashAD()，函数
     * @param view
     */
    public void showSplashAD(ViewGroup view){
        if (splashAD != null&&view!=null) {
            splashAD.showAd(view);
        }
    }

    public void release(){
        if (weakReference != null) {
            weakReference.clear();
        }
    }

}

