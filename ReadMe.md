
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

