package com.shenxing.admanager.callback;

import android.view.View;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 个性化模板Draw信息流
 */
public interface ExpressDrawSimpleListener {

    void onLoadError(int errorCode, String extraCode);

    void onVideoError(int errorCode, int extraCode);

    void onVideoAdComplete();

    void onRenderFail(View view, String msg, int code);

    void onRenderSuccess(View view, float width, float height);

    void onAdShow(View view, int type);
}
