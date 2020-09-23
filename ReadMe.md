
注意此版本为androidx项目才能引用
直接食用：implementation 'com.zbb.admanager:ADManager:1.1'

或者导入module方式，二选一
app中build.gradle 配置：repositories {
              flatDir {
                  dirs 'libs', '../admanager/libs'
              }
          }

#具体用法参考demo
##由于广告样式较多，不再采用单例实现,尽量一个页面，只保证有一个新闻类型实例存在，资源回收记得调用

初始化：   //初始化GDT WM
            ADConfig.initAD(getApplicationContext(),"gdtid","wmid");
            ADConfig.openDebug(BuildConfig.DEBUG);//是否开启debug


混淆配置
-keep class com.qq.**{*;}
-keep class com.bytedance.**{*;}
-keep class com.pgl.**{*;}
-keep class com.shenxing.**{*;}
-keep class com.ss.**{*;}
-keep class com.tencent.**{*;}
-keep class c.t.maploc.lite.tsa.**{*;}
-keep class yaq.gdtadv{*;}
-keep class cn.mmachina.JniClient{*;}

-keepattributes Signature

-dontwarn com.androidquery.**
-keep class com.androidquery.** { *;}

-dontwarn tv.danmaku.**
-keep class tv.danmaku.** { *;}

-dontwarn androidx.**

-keep class com.tencent.smtt.** { *; }
-dontwarn dalvik.**
-dontwarn com.tencent.smtt.**

//网盟（穿山甲）
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep class com.androidquery.callback.** {*;}
-keep class com.bytedance.sdk.openadsdk.service.TTDownloadProvider
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.bytedance.sdk.openadsdk.** { *; }
-keep public interface com.bytedance.sdk.openadsdk.downloadnew.** {*;}
-keep class com.pgl.sys.ces.* {*;}
-keep class com.ss.sys.ces.*{*;}

