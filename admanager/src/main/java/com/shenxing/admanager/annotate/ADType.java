package com.shenxing.admanager.annotate;

import androidx.annotation.StringDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.CONSTRUCTOR;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.LOCAL_VARIABLE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.PACKAGE;
import static java.lang.annotation.ElementType.PARAMETER;
import static java.lang.annotation.ElementType.TYPE;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 定义广告类型
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = {TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE, PACKAGE})
@StringDef({ADType.SPLASH_AD, ADType.BANNER2_AD, ADType.UNINTER2_AD, ADType.NATIVE_AD, ADType.NATIVE2_AD, ADType.NATIVEUN_AD, ADType.REWARD_AD, ADType.H5_AD
        , ADType.SPLASH_WMAD, ADType.BANNER_WMAD, ADType.UNINTER_WMAD, ADType.NATIVE_WMAD, ADType.REWARD_WMAD, ADType.FULLSCREEN_WMAD, ADType.Draw_WMAD})
public @interface ADType {

    /**
     * 开屏 gdt$wm
     */
    String SPLASH_AD = "SplashGDT";

    /**
     * banner2.0 gdt$wm
     */
    String BANNER2_AD = "Banner2GDT";

    /**
     * 插屏2.0 gdt$wm
     */
    String UNINTER2_AD = "UnifiedInterstitialADGDT";

    /**
     * 原生广告 gdt$wm
     */
    String NATIVE_AD = "NativeExpressADGDT";

    /**
     * 原生广告2.0
     */
    String NATIVE2_AD = "NativeExpressAD2GDT";

    /**
     * 自渲染 2.0 暂不实现
     */
    String NATIVEUN_AD = "NativeUnifiedADGDT";

    /**
     * 激励视频 gdt$wm
     */
    String REWARD_AD = "RewardVideoADGDT";

    /**
     * H5 激励视频 暂无法实现
     */
    String H5_AD = "HybridADGDT";

    /**
     * 开屏 wm
     */
    String SPLASH_WMAD = "SplashWM";

    /**
     * banner
     */
    String BANNER_WMAD = "BannerWM";

    /**
     * 插屏
     */
    String UNINTER_WMAD = "UnifiedInterstitialADWM";
    /**
     * 信息流（原生）
     */
    String NATIVE_WMAD = "NativeExpressADWM";
    /**
     * 激励视频
     */
    String REWARD_WMAD = "RewardVideoADWM";

    //全屏视频（TTFullScreenVideoAd）
    String FULLSCREEN_WMAD = "FullScreenVideoAdWM";

    //    Draw 信息流 个性化模板Draw信息流
    String Draw_WMAD = "TTDrawFeedAdWM";

}
