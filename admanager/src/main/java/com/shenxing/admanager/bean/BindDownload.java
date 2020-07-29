package com.shenxing.admanager.bean;

import android.util.Log;

import com.bytedance.sdk.openadsdk.TTAppDownloadListener;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 下载监听绑定
 */
public class BindDownload implements TTAppDownloadListener {

    public static final String TAG="BindDownload";

    @Override
    public void onIdle() {
        Log.d(TAG, "onIdle: ");
    }

    @Override
    public void onDownloadActive(long l, long l1, String s, String s1) {
        Log.d(TAG, "onDownloadActive: ");
    }

    @Override
    public void onDownloadPaused(long l, long l1, String s, String s1) {
        Log.d(TAG, "onDownloadPaused: ");
    }

    @Override
    public void onDownloadFailed(long l, long l1, String s, String s1) {
        Log.d(TAG, "onDownloadFailed: ");
    }

    @Override
    public void onDownloadFinished(long l, String s, String s1) {
        Log.d(TAG, "onDownloadFinished: ");
    }

    @Override
    public void onInstalled(String s, String s1) {
        Log.d(TAG, "onInstalled: ");
    }
}
