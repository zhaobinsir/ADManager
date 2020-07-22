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
 * on 2020/7/22.
 * 定义广告类型
 */
@Retention(RetentionPolicy.SOURCE)
@Target(value = {TYPE, FIELD, METHOD, PARAMETER, CONSTRUCTOR, LOCAL_VARIABLE, ANNOTATION_TYPE,PACKAGE})
@StringDef({ADType.SPLASH_AD, ADType.BANNER2_AD, ADType.UNINTER2_AD, ADType.NATIVE_AD, ADType.NATIVE2_AD, ADType.NATIVEUN_AD,ADType.REWARD_AD,ADType.H5_AD})
public @interface ADType {

    /**
     * 开屏
     */
    String SPLASH_AD="Splash";

    /**
     * banner2.0
     */
    String BANNER2_AD="Banner2";

    /**
     * 插屏2.0
     */
    String UNINTER2_AD="UnifiedInterstitialAD";

    /**
     * 原生广告
     */
    String NATIVE_AD="NativeExpressAD";

    /**
     * 原生广告2.0
     */
    String NATIVE2_AD="NativeExpressAD2";

    /**
     * 自渲染 2.0
     */
    String NATIVEUN_AD="NativeUnifiedAD";

    /**
     * 激励视频
     */
    String REWARD_AD="RewardVideoAD";

    /**
     * H5 激励视频
     */
    String H5_AD="HybridAD";
}
