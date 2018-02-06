# ANDFrame
android 基础快速开发框架，MVP架构，使用最新的Kotlin+Retrofit2+okhttp3+RxJava+Dagger2,页面跳转使用路由

风格采用了Material Design. 多数控件都是design包里面的。

## 项目模式

* [Kotlin](https://github.com/JetBrains/kotlin)
* MVP
* [Dagger2](https://github.com/square/dagger)
* [Rxjava](https://github.com/ReactiveX/RxJava)
* DataBinding
* [Retrofit](https://github.com/square/retrofit)
* [Okhttp3](https://github.com/square/okhttp)
* [DeepLinkDispatch](https://github.com/airbnb/DeepLinkDispatch)
* [Gson](https://github.com/google/gson)
* [Glide](https://github.com/bumptech/glide)
* [ByeBurger](https://github.com/githubwing/ByeBurger)

## Kotlin

  让你的代码量大大减少，函数式编程让你爽到飞上天！如果你想学习Kotlin，本项目应该会给你不少帮助。

## MVP 
  通过契约类Contract管理View Model Presenter接口。

  * Model -- 主要处理业务，用于数据的获取(如网络、本地缓存)。
  * View -- 用于把数据展示，并且提供交互。
  * Presenter -- View和Model交互的桥梁，二者通过Presenter建立联系。

  主要流程如下： 用户与View交互，View得知用户需要加载数据，告知Presenter，Presenter则告知Model，Model拿到数据反交于Prsenter，Presenter将数据交给View进行展示。

## Dagger2
    安卓应用在初始化对象的时候经常需要处理各种依赖关系。比如说网络访问中使用Retrofit，Gson，本地存储中使用
    shared preference。无一例外，我们都都需要在使用它们的地方进行实例对象构建，而且其中还可能存在着各种各样的
    继承依赖关系。
    依赖注入（Dependency Injection，简称DI）是用于削减计算机程序的耦合问题的一个法则。对象在被创建的时候，
    由一个调控系统内所有对象的外界实体将其所依赖的对象的引用传递给它。也可以说，依赖被注入到对象中。
    Dagger2 正是一个依赖注入框架，使用代码自动生成创建依赖关系需要的代码。减少很多公式化代码，更容易测试，
    降低耦合，创建可复用可互换的模块。

注解 	    用法
@Module 	Modules类里面的方法专门提供依赖，所以我们定义一个类，用@Module注解，这样Dagger在构造类的实例的时候，就知道从哪里去找到需要的 依赖。modules的一个重要特征是它们设计为分区并组合在一起（比如说，在我们的app中可以有多个组成在一起的modules
@Provide 	在modules中，我们定义的方法是用这个注解，以此来告诉Dagger我们想要构造对象并提供这些依赖。
@Singleton 	当前提供的对象将是单例模式 ,一般配合@Provides一起出现
@Component 	用于接口，这个接口被Dagger2用于生成用于模块注入的代码
@Inject 	在需要依赖的地方使用这个注解。（你用它告诉Dagger这个 构造方法，成员变量或者函数方法需要依赖注入。这样，Dagger就会构造一个这个类的实例并满足他们的依赖。）
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
    
    
  
## Rxjava + Retrofit + okhttp3
  主要用于网络访问。
  
## DeepLinkDispatch
  基于路由进行页面转发。
  
   **GankClientUri** 定义一些路由规则、URI等
   
   **GankRouter** 统一由此进行路由操作

## GSON
  用于json的解析操作。
  
## Glide
  用于图片的加载。
  
## ByeBurGer
  用于导航栏以及悬浮按钮滑动隐藏。