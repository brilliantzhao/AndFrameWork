package com.brilliantzhao.baselibrary.base

import android.app.Application
import android.content.Context
import com.blankj.utilcode.util.CrashUtils
import com.blankj.utilcode.util.LogUtils
import com.brilliantzhao.baselibrary.stetho.ExtInspectorModulesProvider
import com.brilliantzhao.baselibrary.stetho.MyDumperPlugin
import com.brilliantzhao.baselibrary.util.SharedPrefUtils
import com.brilliantzhao.baselibrary.util.getAppIsLogShowLog
import com.brilliantzhao.baselibrary.util.getCurrentProcessName
import com.facebook.stetho.Stetho
import com.orhanobut.hawk.Hawk
import com.umeng.commonsdk.UMConfigure

/**
 * description:
 * Date: 2018/2/8 09:43
 * User: BrilliantZhao
 */
open class BaseApplication : Application() {

    //##########################  custom variables start ##########################################

    init {
        instance = this
    }

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun onCreate() {
        super.onCreate()
        val packageName = applicationInfo.packageName
        val curPorcessName = getCurrentProcessName(this)
        if (packageName == curPorcessName) {
            initUtilCode()
            initUMeng()
            //=== init Hawk
            Hawk.init(this).build()
            //=== init SharedPrefUtils
            SharedPrefUtils.init(applicationContext)
            //=== init stetho,只在debug模式下使用 Stetho
            initStetho(applicationContext, packageName)
        }
        LogUtils.i("packageName-->" + packageName + "\ncurPorcessName-->" + curPorcessName)
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        lateinit var instance: BaseApplication
    }

    /**
     *
    注意： 如果项目的Manifest文件中已经配置友盟的appkey和channel，则使用该方法初始化，同时不必再次传入appkey和channel两个参数
    参数1:上下文，必须的参数，不能为空
    参数2:设备类型，必须参数，传参数为UMConfigure.DEVICE_TYPE_PHONE则表示手机；传参数为UMConfigure.DEVICE_TYPE_BOX则表示盒子；默认为手机
    参数3:Push推送业务的secret，需要集成Push功能时必须传入Push的secret，否则传空
     */
    fun initUMeng() {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数3:Push推送业务的secret
         */
        UMConfigure.init(this, UMConfigure.DEVICE_TYPE_PHONE, "")
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
    fun initUtilCode() {
        //=== init AndroidUtilCode
        // init it in the function of onCreate in ur Application
        com.blankj.utilcode.util.Utils.init(this)
        //===
        val config = LogUtils.getConfig()
                .setLogSwitch(getAppIsLogShowLog())// 设置log总开关，包括输出到控制台和文件，默认开
                .setConsoleSwitch(getAppIsLogShowLog())// 设置是否输出到控制台开关，默认开
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

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    //######################   override third methods end  ########################################
}