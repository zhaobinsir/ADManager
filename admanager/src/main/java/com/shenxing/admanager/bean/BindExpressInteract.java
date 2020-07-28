package com.shenxing.admanager.bean;

import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTNativeExpressAd;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 广告点击互动绑定
 */
public class BindExpressInteract implements TTNativeExpressAd.ExpressAdInteractionListener{

    public static final String TAG="BindExpressInteract";

    WeakReference<ViewGroup> weakReference;

    public BindExpressInteract(WeakReference<ViewGroup> weakReference) {
        this.weakReference = weakReference;
    }

    @Override
    public void onAdClicked(View view, int i) {
        Log.d(TAG, "onAdClicked: ");
    }

    @Override
    public void onAdShow(View view, int i) {
        Log.d(TAG, "onAdShow: ");
    }

    @Override
    public void onRenderFail(View view, String s, int i) {
        Log.d(TAG, "onRenderFail: ");
    }

    @Override
    public void onRenderSuccess(View view, float v, float v1) {
        Log.d(TAG, "onRenderSuccess: ");
        if (weakReference != null) {
            weakReference.get().removeAllViews();
            weakReference.get().addView(view);
        }else Log.d(TAG, "onRenderSuccess: but ViewGroup null,it's maybe recycled");
    }
}
