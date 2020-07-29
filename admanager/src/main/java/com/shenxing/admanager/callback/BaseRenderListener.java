package com.shenxing.admanager.callback;

import android.view.View;

/**
 * Created by zhaobinsir
 * on 2020/7/29.
 */
public interface BaseRenderListener {

    void onRenderFail(View view, String msg, int code);

    void onRenderSuccess(View view, float width, float height);
}
