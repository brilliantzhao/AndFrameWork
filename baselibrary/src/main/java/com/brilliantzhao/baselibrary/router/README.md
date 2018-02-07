# 什么是路由

根据路由表将页面请求分发到指定页面
使用场景

    App接收到一个通知，点击通知打开App的某个页面
    浏览器App中点击某个链接打开App的某个页面
    运营活动需求，动态把原生的页面替换成H5页面
    打开页面需要某些条件，先验证完条件，再去打开那个页面
    不合法的打开App的页面被屏蔽掉
    H5打开链接在所有平台都一样，方便统一跳转
    App存在就打开页面，不存在就去下载页面下载,只有Google的App Link支持

# 为什么要有路由

Android原生已经支持AndroidManifest去管理App跳转，为什么要有路由库，这可能是大部分人接触到Android
各种Router库不太明白的地方，这里我讲一下我的理解

    显示Intent：项目庞大以后，类依赖耦合太大，不适合组件化拆分
    隐式Intent：协作困难，调用时候不知道调什么参数
    每个注册了Scheme的Activity都可以直接打开，有安全风险
    AndroidMainfest集中式管理比较臃肿
    无法动态修改路由，如果页面出错，无法动态降级
    无法动态拦截跳转，譬如未登录的情况下，打开登录页面，登录成功后接着打开刚才想打开的页面
    H5、Android、iOS地址不一样，不利于统一跳转

# 怎么样的路由才算好路由

路由说到底还是为了解决开发者遇到的各种奇葩需求，使用简单、侵入性低、维护方便是首要条件，不影响你原来的
代码，写入代码也很少，这里就要说说我的OkDeepLink的五大功能了，五大功能瞬间击中你的各种痛点，早点下班
不是梦。

    编译时注解，实现静态路由表,不再需要在臃肿的AndroidManifest中找到那个Actvity写Scheme和Intent Filter
    异步拦截器，实现动态路由，安全拦截、动态降级难不倒你
    模仿Retrofit接口式调用，实现方式用apt，不耗性能，参数调用不再是问题
    HookOnActivityResult,支持RxJava响应式调用,不再需要进行requestCode判断
    参数依赖注入，自动保存，不再需要手动写onSaveInstance、onCreate(SaveInstace)、onNewIntent(Intent)、getQueryParamer

# 用法

Google官方给了一个样例：search-samples
以下根据Android官方的deep-linking的样例来说明如何使用。

    <activity
        android:name="com.example.android.GizmosActivity"
        android:label="@string/title_gizmos" >
        <intent-filter android:label="@string/filter_view_http_gizmos">
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <!-- Accepts URIs that begin with "http://www.example.com/gizmos” -->
            <data android:scheme="http"
                  android:host="www.example.com"
                  android:pathPrefix="/gizmos" />
            <!-- note that the leading "/" is required for pathPrefix-->
        </intent-filter>
        <intent-filter android:label="@string/filter_view_example_gizmos">
            <action android:name="android.intent.action.VIEW" />
            <category android:name="android.intent.category.DEFAULT" />
            <category android:name="android.intent.category.BROWSABLE" />
            <!-- Accepts URIs that begin with "example://gizmos” -->
            <data android:scheme="example"
                  android:host="gizmos" />
        </intent-filter>
    </activity>

在上面有两个<intent-filter ..>这两个<intent-filter ..>只是在<data ..>上有所区别，
但是官方仍然建议我们分开写。比如：

    <intent-filter>
      ...
      <data android:scheme="https" android:host="www.example.com" />
      <data android:scheme="app" android:host="open.my.app" />
    </intent-filter>

上面在同一个<intent-filter ..>里面写的两个<data ..>，他们除了组合https://www.example.com和app://open.my.app外app://www.example.com和 https://open.my.app也是满足上面的<intent-filter ..>的。而分开写的时候，不存在上面的问题。
当你添加了上面的<intent-filter..>当你的Activity上面时，其他App，就可以通过一个intent去调起你的应用，官方这样说到：
Once you've added intent filters with URIs for activity content to your app manifest, Android is able to route any Intent that has matching URIs to your app at runtime.
当注册了<intent-filter..>后，便可以在Activity的中获取其他应用传过来的intent值，具体调用如下：

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
    
        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();
    }

getIntent可以在Activity的生命周期的任何时段进行获取，不过一般别人应用要调你应用，肯定都是希望进入你的应用某个界面，或实现某个功能。其他应用会把该传的信息都传给你，最好的解析地方肯定是onCreate（或onStart但onStart还是会晚一些）。对于这个官方给了以下建议：

*   The deep link should take users directly to the content, without any prompts, interstitial pages, or logins. Make sure that users can see the app content even if they never previously opened the application. It is okay to prompt users on subsequent interactions or when they open the app from the Launcher. This is the same principle as the [first click free](https://support.google.com/webmasters/answer/74536?hl=en) experience for web sites.
*   Follow the design guidance described in [Navigation with Back and Up](https://developer.android.com/design/patterns/navigation.html) so that your app matches users' expectations for backward navigation after they enter your app through a deep link

意思就是：

    打开应用后应该直接到内容，不要有任何提示，间接的页面，或登录。确保用户可以看到应用程序的内容，即使他们以前从未打开过应用程序。可以在随后的交互中提示用户，或者在启动程序中打开应用程序。这与网站第一次点击免费体验的原理是相同的。
    遵循导航与后退和向上描述的设计指南，使您的应用程序与用户通过向后链接进入您的应用程序的深度导航的期望相符。

# Dispatch框架使用例子

    @DeepLink("foo://example.com/deepLink/{id}")
    public class MainActivity extends Activity {
      @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
          Bundle parameters = intent.getExtras();
          String idString = parameters.getString("id");
          // Do something with idString
        }
      }
    }

多个<intent-filter..>的注解

    //多filter的注解
    @DeepLink({"foo://example.com/deepLink/{id}", "foo://example.com/anotherDeepLink"})
    public class MainActivity extends Activity {
      @Override protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
          Bundle parameters = intent.getExtras();
          String idString = parameters.getString("id");
          // Do something with idString
        }
      }
    }

某个方法的注解：

    @DeepLink("foo://example.com/methodDeepLink/{param1}")
    public static Intent intentForDeepLinkMethod(Context context, Bundle extras) {
      Uri.Builder uri = Uri.parse(extras.getString(DeepLink.URI)).buildUpon();
      return new Intent(context, MainActivity.class)
          .setData(uri.appendQueryParameter("bar", "baz").build())
          .setAction(ACTION_DEEP_LINK_METHOD);
    }

## 上面的注解相当于DeepLink中在manifest中的Activity标签下注册的<intent-filter..>，在DeepLinkDispatch中还可以注册一个广播接收者来接收分发的DeepLink字符串。

    public class DeepLinkReceiver extends BroadcastReceiver {
      private static final String TAG = "DeepLinkReceiver";
    
      @Override public void onReceive(Context context, Intent intent) {
        String deepLinkUri = intent.getStringExtra(DeepLinkHandler.EXTRA_URI);
        if (intent.getBooleanExtra(DeepLinkHandler.EXTRA_SUCCESSFUL, false)) {
          Log.i(TAG, "Success deep linking: " + deepLinkUri);
        } else {
          String errorMessage = intent.getStringExtra(DeepLinkHandler.EXTRA_ERROR_MESSAGE);
          Log.e(TAG, "Error deep linking: " + deepLinkUri + " with error message +" + errorMessage);
        }
      }
    }
    
    public class YourApplication extends Application {
      @Override public void onCreate() {
        super.onCreate();
        IntentFilter intentFilter = new IntentFilter(DeepLinkHandler.ACTION);
        
    //使用应用内广播注册的，不用担心其他应用收到
    LocalBroadcastManager.getInstance(this).registerReceiver(new DeepLinkReceiver(), intentFilter);
      }
    }

# 总结

1. DeepLink实现了网页直接和App直接跳转。之前手机上的每个App都相当于一个个孤岛，没有办法和广泛的网站实现直接的跳转。
现在比如你在浏览微博的时候看到某个App上面有精彩的内容，你就可以直接点击链接跳转到App里面（甚至可以判断如果按照了App
就进入App里面，如果不安装那么就进入应用市场的该App下载界面），这样的交互很方便，很好的将App连接到了整个网络世界，
以后有个浏览器就能随意的跳转。
2. DeepLink完全可以在搜索中使用，目前的搜索都是搜到了内容还是调网页。以后如果开发者把自己的DeepLink链接提交给搜索公司，
那么在搜索到对应的结果的时候就可以直接点击搜到的结果跳转到自己的App了。这个还能应用到广告上去。推广自己的App就更容易了。
3. DeepLink使得大企业的众多App之间相互拉活，相互跳转。假如某公司有个超级App，那么想推广自己的其他App就可以使用
DeepLink在开启自己某个子页面的时候，把这个子页面交给其他App进行处理。这样就拉活了自己的其他App了。
4. 在DeepLink的基础上，Google又新出了一个AppLinks，AppLinks就是你自己的网站和你自己的App相互关联了。
比如用户在短信中点击了你的网站，那么就可以直接跳转到你的App，而不会出现选择对话框

Notes

    Starting with DeepLinkDispatch v3, you have to always provide your own Activity class and annotate it with @DeepLinkHandler. It's no longer automatically generated by the annotation processor.
    Intent filters may only contain a single data element for a URI pattern. Create separate intent filters to capture additional URI patterns.
    Please refer to the sample app for an example of how to use the library.


