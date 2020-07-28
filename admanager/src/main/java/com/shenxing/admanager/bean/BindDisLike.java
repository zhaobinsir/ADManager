package com.shenxing.admanager.bean;

import android.util.Log;
import android.view.ViewGroup;

import com.bytedance.sdk.openadsdk.TTAdDislike;

import java.lang.ref.WeakReference;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 绑定 dislike弹窗
 */
public class BindDisLike implements TTAdDislike.DislikeInteractionCallback {

    public static final String TAG="BindDisLike";

    WeakReference<ViewGroup> weakReference;

    public BindDisLike(WeakReference<ViewGroup> weakReference) {
        this.weakReference = weakReference;
    }

    @Override
    public void onSelected(int i, String s) {
        Log.d(TAG, "onSelected: ");
        if (weakReference != null) {
            weakReference.get().removeAllViews();
        }else Log.d(TAG, "onRenderSuccess: but ViewGroup null,it's maybe recycled");
    }

    @Override
    public void onCancel() {
        Log.d(TAG, "onCancel: ");
    }

    @Override
    public void onRefuse() {
        Log.d(TAG, "onRefuse: ");
    }
}
