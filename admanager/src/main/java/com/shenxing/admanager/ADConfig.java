package com.shenxing.admanager;

import android.content.Context;

import androidx.annotation.NonNull;

import com.bytedance.sdk.openadsdk.TTAdConfig;
import com.bytedance.sdk.openadsdk.TTAdConstant;
import com.bytedance.sdk.openadsdk.TTAdSdk;
import com.qq.e.ads.cfg.MultiProcessFlag;
import com.qq.e.comm.managers.GDTADManager;
import com.qq.e.comm.managers.setting.GlobalSetting;

/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 用于广告初始化
 * 此处不使用用单例模式，防止资源占用
 */
public class ADConfig {

    /**
     * 广告默认初始化
     * @param context
     * @param gdtId 广点通id
     * @param wmId 网盟id
     */
    public static void initAD(@NonNull Context context, @NonNull String gdtId,@NonNull String wmId){
        GDTADManager.getInstance().initWith(context, gdtId);
        TTAdSdk.init(context,
                new TTAdConfig.Builder()
                        .appId(wmId)
                        .useTextureView(true) //使用TextureView控件播放视频,默认为SurfaceView,当有SurfaceView冲突的场景，可以使用TextureView
                        .appName(context.getString(R.string.app_name))
                        .titleBarTheme(TTAdConstant.TITLE_BAR_THEME_DARK)
                        .allowShowNotify(true) //是否允许sdk展示通知栏提示
                        .allowShowPageWhenScreenLock(true) //是否在锁屏场景支持展示广告落地页
                        .debug(true) //测试阶段打开，可以通过日志排查问题，上线时去除该调用
                        .directDownloadNetworkType(TTAdConstant.NETWORK_STATE_WIFI) //允许直接下载的网络状态集合
                        .supportMultiProcess(true) //是否支持多进程，true支持
                        //.httpStack(new MyOkStack3())//自定义网络库，demo中给出了okhttp3版本的样例，其余请自行开发或者咨询工作人员。
                        .build());
    }

    /**
     * 广告自定义初始化
     * @param context
     * @param gdtId 广点通id
     * @param config 网盟广告商的具体配置，可自定义 参考如上说明配置
     */
    public static void initAD(@NonNull Context context, @NonNull String gdtId,@NonNull String wmId,@NonNull TTAdConfig.Builder config){
        GDTADManager.getInstance().initWith(context, gdtId);
        config.appId(wmId);
        TTAdSdk.init(context, config.build());
    }
    /**
     * 广点通 设置渠道，可选
     * @param channel
     */
    public static void setGDTChannel(int channel){
        GlobalSetting.setChannel(channel);
    }

    /**
     * 广点通
     * @param ismulti 是否支持多进程
     */
    public static void setGDTMultiProcess(boolean ismulti){
        MultiProcessFlag.setMultiProcess(ismulti);
    }
}
