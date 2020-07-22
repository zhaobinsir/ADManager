package com.shenxing.admanager.control.manager;

import com.shenxing.admanager.annotate.ADType;
import com.shenxing.admanager.control.Banner2Controller;
import com.shenxing.admanager.control.Interstitial2Controller;
import com.shenxing.admanager.control.SplashADController;

/**
 * Created by zhaobinsir
 * on 2020/7/22.
 * 广告类型管理类
 */
public class ADManager<T> {

    /**
     * @param adtype 根据adtype 返回对应的广告类型
     * @return
     */
    public  synchronized T create(@ADType String adtype){
        T adObject=null;
        switch (adtype) {
            case ADType.SPLASH_AD:
                adObject= (T) new SplashADController();
                break;
            case ADType.BANNER2_AD:
                adObject= (T) new Banner2Controller();
                break;
            case ADType.UNINTER2_AD:
                adObject= (T) new Interstitial2Controller();
                break;
            case ADType.NATIVE_AD:
                break;
            case ADType.NATIVE2_AD:
                break;
            case ADType.NATIVEUN_AD:
                break;
            case ADType.REWARD_AD:
                break;
            case ADType.H5_AD:
                break;
            default:
                break;
        }
        return adObject;
    }

}
