package com.shenxing.admanager.control.gdt;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import com.qq.e.ads.splash.SplashAD;
import com.qq.e.ads.splash.SplashADListener;
import com.shenxing.admanager.doc.GDTDocument;

/**
 * Created by zhaobinsir
 * on 2020/7/22.
 * 具体方法参数意义 参考：https://developers.adnet.qq.com/doc/android/union/union_splash
 */
public class SplashADController {

    private SplashAD splashAD;//有效期30min

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
    public void fetchSplashADShow(Activity activity, ViewGroup adContainer, View skipContainer,
                               String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, skipContainer, posId, adListener, fetchDelay);
        splashAD.fetchAndShowIn(adContainer);
    }

    //预加载，不展示广告
    public void fetchSplashADOnly(Activity activity, View skipContainer,
                                  String posId, SplashADListener adListener, int fetchDelay) {
        splashAD = new SplashAD(activity, skipContainer, posId, adListener, fetchDelay);
        splashAD.fetchAdOnly();
    }

    /**
     * 注意注意：必须要预调用加载 fetchSplashAD()，函数
     * @param view
     */
    public void showSplashAD(ViewGroup view){
        if (splashAD != null) {
            splashAD.showAd(view);
        }
    }

}

