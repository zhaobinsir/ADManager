package com.shenxing.admanager.callback;

import android.view.View;

/**
 * Created by zhaobinsir
 * on 2020/7/29.
 * 网盟 插屏简单回调
 */
public interface InteractListener<T> extends BaseRenderListener{

    void onAdDismiss();

    void onAdShow(View view, int type);

    void onLoadError(int errorCode, String extraCode);

    void onAdLoad(T t);
}
