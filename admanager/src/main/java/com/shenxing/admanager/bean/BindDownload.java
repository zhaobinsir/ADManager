package com.shenxing.admanager.bean;


import com.bytedance.sdk.openadsdk.TTAppDownloadListener;
import com.shenxing.admanager.utils.ILog;

/**
 * Created by zhaobinsir
 * on 2020/7/28.
 * 下载监听绑定
 */
public class BindDownload implements TTAppDownloadListener {

    public static final String TAG="BindDownload";

    @Override
    public void onIdle() {
        ILog.d(TAG, "onIdle: ");
    }

    @Override
    public void onDownloadActive(long l, long l1, String s, String s1) {
        ILog.d(TAG, "onDownloadActive: ");
    }

    @Override
    public void onDownloadPaused(long l, long l1, String s, String s1) {
        ILog.d(TAG, "onDownloadPaused: ");
    }

    @Override
    public void onDownloadFailed(long l, long l1, String s, String s1) {
        ILog.d(TAG, "onDownloadFailed: ");
    }

    @Override
    public void onDownloadFinished(long l, String s, String s1) {
        ILog.d(TAG, "onDownloadFinished: ");
    }

    @Override
    public void onInstalled(String s, String s1) {
        ILog.d(TAG, "onInstalled: ");
    }
}
