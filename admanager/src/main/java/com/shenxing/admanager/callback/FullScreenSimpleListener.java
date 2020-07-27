package com.shenxing.admanager.callback;

/**
 * Created by zhaobinsir
 * on 2020/7/27.
 * 全屏简单回调
 */
public interface FullScreenSimpleListener<T,M> extends SimpleListener{

    /**
     * 广告缓存完成
     */
    void onAdLoad(T t);

    /**
     * 广告发生错误
     */
    void onAdError(M m);
}
