package com.shenxing.admanager.doc;


/**
 * Created by zhaobinsir
 * on 2020/7/22.
 * 网盟文档说明
 * 注意此类为说明类：来源官方字段说明
 */
public class WmDocument {

   /* 初始化自定义参数配置
   public static class TTAdConfig.Builder {
        private String mAppId;// 必选参数，设置应用的AppId
        private String mAppName;// 必选参数，设置应用名称
        private boolean mIsPaid = false;// 可选参数，设置是否为计费用户：true计费用户、false非计费用户。默认为false非计费用户。须征得用户同意才可传入该参数
        private String mKeywords;// 可选参数，设置用户画像的关键词列表 **不能超过为1000个字符**。须征得用户同意才可传入该参数
        private String mData;// 可选参数，设置额外的用户信息 **不能超过为1000个字符**
        private int mTitleBarTheme = TTAdConstant.TITLE_BAR_THEME_LIGHT;// 可选参数，设置落地页主题，默认为TTAdConstant#TITLE_BAR_THEME_LIGHT
        private boolean mAllowShowNotify = true;// 可选参数，设置是否允许SDK弹出通知：true允许、false禁止。默认为true允许
        private boolean mIsDebug = false;// 可选参数，是否打开debug调试信息输出：true打开、false关闭。默认false关闭
        private boolean mAllowShowPageWhenScreenLock = false;// 可选参数，设置是否允许落地页出现在锁屏上面：true允许、false禁止。默认为false禁止
        private int[] mDirectDownloadNetworkType;
        private boolean mIsUseTextureView = false;// 可选参数，设置是否使用texture播放视频：true使用、false不使用。默认为false不使用（使用的是surface）
        private boolean mIsSupportMultiProcess = false;// 可选参数，设置是否支持多进程：true支持、false不支持。默认为false不支持
        private IHttpStack mHttpStack;//可选参数，设置外部网络请求，默认为urlconnection
        private boolean mIsAsyncInit = false;//是否异步初始化sdk
        private TTCustomController mCustomController;//可选参数，可以设置隐私信息控制开关
    }*/


   /*隐私信息控制开关
    public abstract class TTCustomController {
        *//**
         * 是否允许SDK主动使用地理位置信息
         * @return true可以获取，false禁止获取。默认为true
         *//*
        public boolean isCanUseLocation() {return true}

        *//**
         * 当isCanUseLocation=false时，可传入地理位置信息，穿山甲sdk使用您传入的地理位置信息
         * @return 地理位置参数
         *//*
        public TTLocation getTTLocation() { return null;}

        *//**
         * 是否允许SDK主动使用手机硬件参数，如：imei
         * @return true可以使用，false禁止使用。默认为true
         *//*
        public boolean isCanUsePhoneState() {return true;}

        *//**
         * 当isCanUsePhoneState=false时，可传入imei信息，穿山甲sdk使用您传入的imei信息
         * @return imei信息
         *//*
        public String getDevImei() { return null;}

        *//**
         * 是否允许SDK主动使用ACCESS_WIFI_STATE权限
         * @return true可以使用，false禁止使用。默认为true
         *//*
        public boolean isCanUseWifiState() {  return true;}

        *//**
         * 是否允许SDK主动使用WRITE_EXTERNAL_STORAGE权限
         * @return true可以使用，false禁止使用。默认为true
         *//*
        public boolean isCanUseWriteExternal() {  return true;}

    }*/

}
