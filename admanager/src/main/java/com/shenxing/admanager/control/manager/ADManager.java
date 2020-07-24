package com.shenxing.admanager.control.manager;

import com.shenxing.admanager.annotate.ADType;
import com.shenxing.admanager.control.gdt.Banner2Controller;
import com.shenxing.admanager.control.gdt.Interstitial2Controller;
import com.shenxing.admanager.control.gdt.Native2Controller;
import com.shenxing.admanager.control.gdt.NativeController;
import com.shenxing.admanager.control.gdt.NativeUnifiedController;
import com.shenxing.admanager.control.gdt.RewardVideoController;
import com.shenxing.admanager.control.gdt.SplashADController;

/**
 * Created by zhaobinsir
 * on 2020/7/22.
 * 广告类型管理类
 */
public class ADManager{

   /* private static ADManager instance;

    private T t;//广告类型泛型

    private void ADManager(){}

    public static synchronized ADManager getInstance() {
        if (instance==null) {
            instance=new ADManager();
        }
        return instance;
    }*/

    /**
     * @param adtype 根据adtype 返回对应的广告类型
     * @return
     */
    public static synchronized <T> T create(@ADType String adtype){
        T adObject=null;
        try {
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
                    adObject= (T) new NativeController();
                    break;
                case ADType.NATIVE2_AD:
                    adObject= (T) new Native2Controller();
                    break;
                case ADType.NATIVEUN_AD:
                    adObject= (T) new NativeUnifiedController();
                    break;
                case ADType.REWARD_AD:
                    adObject= (T) new RewardVideoController();
                    break;
                case ADType.H5_AD:
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return adObject;
    }

}
