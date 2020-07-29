package com.shenxing.admanager.callback;

import android.view.View;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 个性化模板Draw信息流
 */
public interface ExpressDrawSimpleListener extends BaseRenderListener {

    void onLoadError(int errorCode, String extraCode);

    void onVideoError(int errorCode, int extraCode);

    void onVideoAdComplete();

    void onAdShow(View view, int type);
}
