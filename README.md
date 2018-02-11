# AndFrameWork
android快速开发框架，组件化的MVP架构，使用最新的技术，包括 Kotlin+Retrofit2+okhttp3+RxJava+Dagger2,
页面跳转使用deeplinkdispatch路由，代码简洁，结构清晰，同时预置了常用的第三方库，方便快速上手

### 备注：
- 已集成了UMeng,用来做统计;
- 已集成stetho，方便使用Chrome调试(推荐，甩开各种抓包工具几条街);
- 已集成AndResGuard，用来做资源混淆，同时可有效减小 apk 1M左右大小;
- 已添加代码混淆;
- 通过gradle.properties中添加配置来满足个性化需求;
- 使用hawk配合sharedPreference满足大多数场景的数据存储;
- 项目的刷新根据不同需求，分别使用swipRefreshLayout,RecycleView,practicalrecyclerview;
- 热修复使用的是Tinker,配合AndResGuard使用，很方便;
- 持续集成使用蒲公英(打包成功之后通过通过插件上传到蒲公英，然后打开应用就可以看到升级弹框了)，没有使用jenkins的
  原因是项目大了之后太慢了，并且也没有方便到哪去;
- 代码中添加了大量注释，方便理解和使用，使新手也能快速上手。

### 本工程结构如下：
    
![image](https://github.com/BrillantZhao/AndFrameWork/blob/master/img/frame_architecture.png)
    
## 项目用到的github开源库

            //=== android support
            appcompatV7              : "com.android.support:appcompat-v7:26.1.0",
            supportV4                : "com.android.support:support-v4:26.1.0",
            supportV13               : "com.android.support:support-v13:26.1.0",

            //=== android widget
            constraintLayout         : "com.android.support.constraint:constraint-layout:1.0.2",
            cardviewV7               : "com.android.support:cardview-v7:26.1.0",
            design                   : "com.android.support:design:26.1.0",
            recyclerview             : "com.android.support:recyclerview-v7:26.1.0",
            gridlayoutV7             : "com.android.support:gridlayout-v7:26.1.0",
            customtabs               : "com.android.support:customtabs:26.1.0",

            //=== android
            supportAnnotations       : "com.android.support:support-annotations:26.1.0",

            //=== android test
            junit                    : "junit:junit:4.12",
            runner                   : "com.android.support.test:runner:1.0.1",
            espressoCore             : "com.android.support.test.espresso:espresso-core:3.0.1",

            //=== kotlin
            kotlinStdlib             : "org.jetbrains.kotlin:kotlin-stdlib:1.2.21",
            kotlinReflect            : "org.jetbrains.kotlin:kotlin-reflect:1.2.21",

            //=== deeplinkdispatch 路由跳转 (https://github.com/airbnb/DeepLinkDispatch)
            deeplinkdispatch         : "com.airbnb:deeplinkdispatch:3.1.1",
            deeplinkdispatchProcessor: "com.airbnb:deeplinkdispatch-processor:3.1.1",

            //=== Dagger2 Google的依赖注入框架 (https://github.com/google/dagger)
            daggerCompiler           : "com.google.dagger:dagger-compiler:2.14.1",
            dagger                   : "com.google.dagger:dagger:2.14.1",

            //=== javaxAnnotation ()
            // dagger2会在编译的时候生成一些.java文件，里面会有个@Generated注解，这个注解是javax.annotation包中的
            javaxAnnotation          : 'org.glassfish:javax.annotation:10.0-b28',

            //=== Rx (https://github.com/ReactiveX/RxAndroid)
            rxJava                   : "io.reactivex:rxjava:1.2.0",
            rxAndroid                : "io.reactivex:rxandroid:1.2.1",

            //=== retrofit (https://github.com/square/retrofit)
            // 和okhttp3配套，都升级到最新版本之后，会报错
            retrofit                 : "com.squareup.retrofit2:retrofit:2.1.0",
            retrofitGsonConverter    : "com.squareup.retrofit2:converter-gson:2.1.0",
            retrofitRxjavaAdapter    : "com.squareup.retrofit2:adapter-rxjava:2.1.0",
            retrofitRxjava2Adapter   : 'com.jakewharton.retrofit:retrofit2-rxjava2-adapter:1.0.0-RC2',

            //=== okhttp3  (https://github.com/square/okhttp)
            okhttp3                  : "com.squareup.okhttp3:okhttp:3.4.2",
            okhttp3_ws               : "com.squareup.okhttp3:okhttp-ws:3.4.2",
            okhttpLoggingInterceptor : "com.squareup.okhttp3:logging-interceptor:3.4.2",

            //=== bga-photopicker 图片选择 (https://github.com/bingoogolapple/BGAPhotoPicker-Android)
            bgaPhotopicker           : "cn.bingoogolapple:bga-photopicker:1.2.8@aar",
            //=== bga-adapter
            bgaAdapter               : "cn.bingoogolapple:bga-baseadapter:1.2.7@aar",

            //=== RxPermissions 基于RxJava的RxPermissions (https://github.com/tbruyelle/RxPermissions)
            rxpermissions            : "com.tbruyelle.rxpermissions2:rxpermissions:0.9.5@aar",

            //=== picasso (https://github.com/square/picasso)
            picasso                  : "com.squareup.picasso:picasso:2.5.2",

            //=== glide ()
            glide                    : "com.github.bumptech.glide:glide:3.7.0",

            //=== hawk (https://github.com/orhanobut/hawk)
            hawk                     : "com.orhanobut:hawk:2.0.1",

            //=== AndroidUtilCode  (https://github.com/Blankj/AndroidUtilCode)
            utilcode                 : "com.blankj:utilcode:1.12.5",

            //=== stetho (https://github.com/facebook/stetho)
            // 可以查看网络请求，数据库，SharedPreference数据等，相对于抓包更加有优势
            // 运行App, 打开Chrome输入 chrome://inspect/#devices（别忘了用数据线把手机和电脑连起来哦）
            stetho                   : "com.facebook.stetho:stetho:1.5.0",
            stethoOkhttp3            : "com.facebook.stetho:stetho-okhttp3:1.5.0",
            stethoJsRhino            : "com.facebook.stetho:stetho-js-rhino:1.5.0",

            //=== SuperTextView (https://github.com/lygttpod/SuperTextView) 满足日常大部分布局样式
            superTextView            : "com.github.lygttpod:SuperTextView:2.1.5",

            //=== eventbus (https://github.com/greenrobot/EventBus)
            eventbus                 : "org.greenrobot:eventbus:3.1.1",

            //=== gson (https://github.com/google/gson)
            gson                     : "com.google.code.gson:gson:2.8.2",

            //=== fastjson  ()
            fastjson                 : "com.alibaba:fastjson:1.1.55.android",

            //=== Luban 图片压缩工具，仿微信朋友圈压缩策略 (https://github.com/Curzibn/Luban)
            Luban                    : "top.zibin:Luban:1.1.3",

            //=== ByeBurger
            ByeBurger                : "com.github.githubwing:ByeBurger:1.2.2",

            //=== databinding
            // databinding complier的版本也就是Project下的gradle版本是一样的，当然了可以不一样,为了方便管理实际工作中是这样配置的
            databinding              : "com.android.databinding:compiler:$gradle_version",

            //=== practicalrecyclerview (https://github.com/BrillantZhao/PracticalRecyclerView)
            // 基于MaterialDesign内置了上拉下拉，以及各种错误页面，支持header和footer,方便个性化定制
            practicalrecyclerview    : "zlc.season:practicalrecyclerview:1.1.6",

## MVP 
  通过契约类Contract管理View Model Presenter接口。

  * Model -- 主要处理业务，用于数据的获取(如网络、本地缓存)。
  * View -- 用于把数据展示，并且提供交互。
  * Presenter -- View和Model交互的桥梁，二者通过Presenter建立联系。

  主要流程如下： 用户与View交互，View得知用户需要加载数据，告知Presenter，Presenter则告知Model，Model拿到数据
  反交于Prsenter，Presenter将数据交给View进行展示。
  
## Kotlin

  让你的代码量大大减少，函数式编程让你爽到飞上天！同时避免大部分的NPE错误，也不需要使用ButterKnife,直接使用id
  如果你想学习Kotlin，本项目应该会给你不少帮助。

## Dagger2

安卓应用在初始化对象的时候经常需要处理各种依赖关系。比如说网络访问中使用Retrofit，Gson，本地存储中使用
shared preference。无一例外，我们都都需要在使用它们的地方进行实例对象构建，而且其中还可能存在着各种各样的
继承依赖关系。
依赖注入（Dependency Injection，简称DI）是用于削减计算机程序的耦合问题的一个法则。对象在被创建的时候，
由一个调控系统内所有对象的外界实体将其所依赖的对象的引用传递给它。也可以说，依赖被注入到对象中。
降低耦合，创建可复用可互换的模块。

    注解 	    用法
    @Module 	Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的
                时候，就知道从哪里去找到需要的 依赖。modules的一个重要特征是它们设计为分区并组合在一起（比如说，
                在我们的app中可以有多个组成在一起的modules
    @Provide 	在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
    @Singleton 	当前提供的对象将是单例模式 ,一般配合@Provides一起出现
    @Component 	用于接口，这个接口被Dagger2用于生成用于模块注入的代码
    @Inject 	在需要依赖的地方使用这个注解。（你用它告诉Dagger这个 构造方法，成员变量或者函数方法需要依赖注入。
                这样，Dagger就会构造一个这个类的实例并满足他们的依赖。）
    @Scope 	    Scopes可是非常的有用，Dagger2可以通过自定义注解限定注解作用域。

  项目中,主要进行presenter、model、retrofit Api等类的注入操作。
  
  **ApiComponent** 
    主Component、用于注入AppComponent、便于提供子Component依赖。
     
     依赖于：
     1.ApiModule(提供okhttpClient、Retrofit、Api等)
     2.AppModule(提供context对象(okhttp拦截器所需))
      
  **FuckGoodsComponent** 
    父Component为ApiComponent 用于注入FuckGoodsPresenter
     
     依赖于: FuckGoodsModule(提供FuckGoodsView)
     
  **RandomComponent** 
    父Component为ApiComponent 用于注入RandomPresenter
    
    依赖于 : RandomModule(提供RandomView) 
    
## Rxjava + Retrofit2 + okhttp3

  主要用于网络访问。
  
## DeepLinkDispatch

  基于路由进行页面转发。
  
   **xxxClientUri** 定义一些路由规则、URI等
   **Router**       统一由此进行路由操作

## GSON

  用于json的解析操作。
  
## Glide

  用于图片的加载。
  
## ByeBurGer

  用于导航栏以及悬浮按钮滑动隐藏。
  
## 更多功能将会继续开发和完善

  若您对此项目有一些自己的想法 , 欢迎来提Pull Request. 同时欢迎star和fork

## 关于我

  若您对该项目有疑问,请联系我:
QQ  : 137148873
Mail: 137148873@qq.com

## License

> ```
> Copyright 2016 BrillantZhao(https://github.com/BrillantZhao/AndFrameWork)
>
> Licensed under the Apache License, Version 2.0 (the "License");
> you may not use this file except in compliance with the License.
> You may obtain a copy of the License at
>
>    http://www.apache.org/licenses/LICENSE-2.0
>
> Unless required by applicable law or agreed to in writing, software
> distributed under the License is distributed on an "AS IS" BASIS,
> WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
> See the License for the specific language governing permissions and
> limitations under the License.
> ```