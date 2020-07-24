package com.shenxing.admanager.doc;


/**
 * Created by zhaobinsir
 * on 2020/7/24.
 * 网盟文档说明
 * 注意此类为说明类：来源官方字段说明
 */
public class WmDocument {

   /* 初始化自定义参数配置说明
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


//    以下为错误码
 /*错误码
20000	成功
40000	http content type错误
40001	http request pb错误
40002	source_type=‘app’, 请求app不能为空
40003	source_type=‘wap’, 请求wap不能为空
40004	广告位不能为空
40005	广告位尺寸不能为空
40006	广告位ID不合法
40007	广告数量错误
40008	图片尺寸错误
40009	媒体ID不合法
40010	媒体类型不合法
40011	广告类型不合法
40013	代码位id小于9亿，但是adType不是开屏
40014	redirect参数不正确
40015	媒体整改超过期限，请求非法
40016	slot_id 与 app_id对应关系不合法
40017	媒体接入类型不合法 API/SDK
40018	媒体包名与录入不一致
40019	媒体配置adtype和请求不一致
40020	开发注册新上线广告位超出日请求量限制
40021	apk签名sha1值与媒体平台录入不一致
40022	媒体请求素材是否原生与媒体平台录入不一致
40023	os字段填的不对
40024	sdk 版本过低不返回广告
40025	渲染异常，分为两种情况： 1.Android版本，媒体使用了非该应用所属账号下的SDK版本导致，请媒体到该代码位所属账号下工具-文档下载展示的SDK版本去进行接入。或者so库冲突，请对照文档进行调整。 2.iOS版本，媒体使用2100之前的版本可能渲染异常，请媒体更新到最新版本接入即可解决该问题。
            50001	服务器错误
60001	show event处理错误
60002	click event处理错误
60007	激励视频验证服务器异常或处理失败
    isValid	校验结果
-1	数据解析失败
-2	网络错误
-3	解析数据没有ad
-4	返回数据缺少必要字段
-5	bannerAd加载图片失败
-6	插屏广告图片加载失败
-7	开屏广告图片加载失败
-8	频繁请求
-9	请求实体为空
-10	缓存解析失败
-11	缓存过期
-12	缓存中没有开屏广告
101	渲染结果数据解析失败
102	主模板无效
103	模板差量无效
104	物料数据异常
105	模板数据解析异常
106	渲染异常
107	渲染超时未回调
40026	使用海外ip请求国内服务器导致，请确认使用的是国内ip请求广告。
 40028	ios老设备（涉及设备 iPad 4G/iPad 3G/iPhone 5/iPhone 5C/iPad Mini 1G/iPad 2G/iPhone 4S）被屏蔽，会不返回广告。
 在2310版本后放开了限制，媒体可以更新到2310或者之后的版本。
 40029	两种情况： 1. SDK版本低：您使用的sdk版本不得低于2.5.0.0，麻烦升级到平台最新版本sdk。
  2. 接口使用错误：创建的代码位类型是模板渲染/非模板渲染，但是请求方法是非模板渲染/模板渲染的方法。解决办法：使用模板渲染的方法去请求模板渲染类型或者使用非模板渲染的方法去请求非模板类型的广告，如果代码位在平台上是模板渲染，可以参考文档中个性化模板XX广告的部分，demo中参考带有express部分的代码。如果代码位不是模板渲染，则不要调用含有express字样的接口。 参考文档：https://partner.oceanengine.com/doc?id=5ecc8a5bec6540000eabbad2*/

}
