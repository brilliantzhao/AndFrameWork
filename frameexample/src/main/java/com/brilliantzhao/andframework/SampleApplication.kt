package com.brilliantzhao.andframework

import android.annotation.TargetApi
import android.app.Application
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.multidex.MultiDex
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.blankj.utilcode.util.ProcessUtils
import com.brilliantzhao.andframework.component.ApiComponent
import com.brilliantzhao.andframework.component.DaggerApiComponent
import com.brilliantzhao.andframework.tinker.Log.MyLogImp
import com.brilliantzhao.andframework.tinker.util.SampleApplicationContext
import com.brilliantzhao.andframework.tinker.util.TinkerManager
import com.brilliantzhao.baselibrary.module.ApiModule
import com.brilliantzhao.baselibrary.module.AppModule
import com.brilliantzhao.baselibrary.stetho.ExtInspectorModulesProvider
import com.brilliantzhao.baselibrary.stetho.MyDumperPlugin
import com.brilliantzhao.baselibrary.util.SharedPrefUtils
import com.brilliantzhao.baselibrary.util.getAppIsLogShowLog
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import com.tencent.tinker.anno.DefaultLifeCycle
import com.tencent.tinker.lib.tinker.Tinker
import com.tencent.tinker.lib.tinker.TinkerInstaller
import com.tencent.tinker.loader.app.DefaultApplicationLike
import com.tencent.tinker.loader.shareutil.ShareConstants
import com.umeng.commonsdk.UMConfigure
import javax.inject.Inject

/**
 * because you can not use any other class in your application, we need to
 * move your implement of Application to {@link ApplicationLifeCycle}
 * As Application, all its direct reference class should be in the main dex.
 *
 * We use tinker-android-anno to make sure all your classes can be patched.
 *
 * application: if it is start with '.', we will add SampleApplicationLifeCycle's package name
 *
 * flags:
 * TINKER_ENABLE_ALL: support dex, lib and resource
 * TINKER_DEX_MASK: just support dex
 * TINKER_NATIVE_LIBRARY_MASK: just support lib
 * TINKER_RESOURCE_MASK: just support resource
 *
 * loaderClass: define the tinker loader class, we can just use the default TinkerLoader
 *
 * loadVerifyFlag: whether check files' md5 on the load time, defualt it is false.
 *
 * Created by zhangshaowen on 16/3/17.
 */

/**
 * description: manifest中的app name为：SampleApplication，需要和这里的SampleApplicationLike对应上
 * 经测试，这样修改application之后，会导致启动的时候有白屏页面，需要特殊处理
 * Date: 2018/2/9 15:42
 * User: BrilliantZhao
 */
@SuppressWarnings("unused")
@DefaultLifeCycle(application = "com.brilliantzhao.andframework.SampleApplication",
        flags = ShareConstants.TINKER_ENABLE_ALL, loadVerifyFlag = false)
class SampleApplicationLike(application: Application?, tinkerFlags: Int, tinkerLoadVerifyFlag: Boolean,
                            applicationStartElapsedTime: Long, applicationStartMillisTime: Long,
                            tinkerResultIntent: Intent?) : DefaultApplicationLike(application, tinkerFlags,
        tinkerLoadVerifyFlag, applicationStartElapsedTime, applicationStartMillisTime, tinkerResultIntent) {

    //##########################  custom variables start ##########################################

    init {
        instance = this
    }

    @Inject
    lateinit var apiComponent: ApiComponent

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        lateinit var instance: SampleApplicationLike
    }

    /**
     *
    注意： 如果项目的Manifest文件中已经配置友盟的appkey和channel，则使用该方法初始化，同时不必再次传入appkey和channel两个参数
    参数1:上下文，必须的参数，不能为空
    参数2:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
    参数3:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
     */
    fun initUMeng(base: Context?) {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(base, UMConfigure.DEVICE_TYPE_PHONE, "")
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true)
        /**
         * 设置日志加密
         * 参数：boolean 默认为false（不加密）
         */
        UMConfigure.setEncryptEnabled(true)
    }

    /**
     * blankj 的工具类初始化
     */
    fun initUtilCode(context: Context) {
        //=== init AndroidUtilCode
        // init it in the function of onCreate in ur Application
        com.blankj.utilcode.util.Utils.init(application)
        //===
        val config = LogUtils.getConfig()
                .setLogSwitch(getAppIsLogShowLog(context))// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(getAppIsLogShowLog(context))// 设置是否输出到控制台开关，默认开
                .setGlobalTag(null)// 设置log全局标签，默认为空
                // 当全局标签不为空时，我们输出的log全部为该tag，
                // 为空时，如果传入的tag为空那就显示类名，否则显示tag
                .setLogHeadSwitch(true)// 设置log头信息开关，默认为开
                .setLog2FileSwitch(false)// 打印log时是否存到文件的开关，默认关
                .setDir("")// 当自定义路径为空时，写入应用的/cache/log/目录中
                .setFilePrefix("")// 当文件前缀为空时，默认为"util"，即写入文件为"util-MM-dd.txt"
                .setBorderSwitch(true)// 输出日志是否带边框开关，默认开
                .setConsoleFilter(LogUtils.V)// log的控制台过滤器，和logcat过滤器同理，默认Verbose
                .setFileFilter(LogUtils.V)// log文件过滤器，和logcat过滤器同理，默认Verbose
                .setStackDeep(1)// log栈深度，默认为1
        LogUtils.d(config.toString())
        //===
        CrashUtils.init { e ->
            e.printStackTrace()
            LogUtils.e(e.message)
        }
    }

    /**
     * 初始化Stetho应用调试
     *
     * @param context
     */
    private fun initStetho(context: Context, packageName: String) {
        Stetho.initialize(Stetho.newInitializerBuilder(context)
                .enableWebKitInspector(ExtInspectorModulesProvider(context, packageName))
                .enableDumpapp {
                    Stetho.DefaultDumperPluginsBuilder(context)
                            .provide(MyDumperPlugin(context))
                            .finish()
                }
                .build())
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    fun registerActivityLifecycleCallbacks(callback: Application.ActivityLifecycleCallbacks) {
        application.registerActivityLifecycleCallbacks(callback)
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onCreate() {
        super.onCreate()
        // 代码中没有错误，编译成功才会生成 DaggerxxxComponent 文件,如遇到该文件没有自动生成，请先确认代码可以成功编译
        DaggerApiComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(application.applicationContext))
                .build()
                .inject(this)

        // 工具初始化，后面需要使用，所以初始化需要放在前面
        initUtilCode(application.applicationContext)

        val packageName = AppUtils.getAppPackageName()
        val curPorcessName = ProcessUtils.getForegroundProcessName()
        if (packageName == curPorcessName) {
            initUMeng(application.applicationContext)
            //=== init Hawk
            Hawk.init(application.applicationContext).build()
            //=== init SharedPrefUtils
            SharedPrefUtils.init(application.applicationContext)
            //=== init stetho,只在debug模式下使用 Stetho
            initStetho(application.applicationContext, packageName)
        }
        LogUtils.i("packageName-->" + packageName + "\ncurPorcessName-->" + curPorcessName)
    }

    override fun onBaseContextAttached(base: Context?) {
        super.onBaseContextAttached(base)
        //you must install multiDex whatever tinker is installed!
        MultiDex.install(base)

        SampleApplicationContext.application = application
        SampleApplicationContext.context = application
        TinkerManager.setTinkerApplicationLike(this)

        TinkerManager.initFastCrashProtect()
        //should set before tinker is installed
        TinkerManager.setUpgradeRetryEnable(true)

        //optional set logIml, or you can use default debug log
        TinkerInstaller.setLogIml(MyLogImp())

        //installTinker after load multiDex
        //or you can put com.tencent.tinker.** to main dex
        TinkerManager.installTinker(this)
        val tinker = Tinker.with(application)
    }

    //######################   override third methods end  ########################################

}