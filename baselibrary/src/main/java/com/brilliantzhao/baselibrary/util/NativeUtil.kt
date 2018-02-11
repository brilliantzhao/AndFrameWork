package com.brilliantzhao.baselibrary.util

import android.app.Activity
import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Process
import android.support.v7.app.AlertDialog
import android.text.TextUtils
import com.brilliantzhao.baselibrary.R
import com.pgyersdk.update.PgyUpdateManager
import com.pgyersdk.update.UpdateManagerListener
import java.io.File

/**
 * description: 自定义本地工具类
 * Date: 2018/2/8 10:15
 * User: BrilliantZhao
 */

/**
 * 获取app当前是否是ONLINE状态，即正式的线上发布状态，此时需要关闭日志
 *
 * @return
 */
fun getAppIsOnlineVersion(context: Context): Boolean {
    return "TRUE" == context.getResources().getString(R.string.APP_IS_ONLINE_VERSION)
}

/**
 * 判断日志是否展示
 *
 * @return
 */
fun getAppIsLogShowLog(context: Context): Boolean {
    return "TRUE" == context.getResources().getString(R.string.APP_IS_SHOW_LOG)
}

/**
 * 获取app的url环境，在gradle.properties中配置
 *
 * @return
 */
fun getAppUrlEnvironment(context: Context): String {
    return context.getResources().getString(R.string.APP_URL_ENVIRONMENT)
}

/**
 * 判断给定字符串是否空白串。 空白串是指由空格、制表符、回车符、换行符组成的字符串
 *
 * @param input
 * @return boolean
 */
fun isEmpty(input: String?): Boolean {
    if (input == null || "" == input) {
        return true
    }
    for (i in 0 until input.length) {
        val c = input[i]
        if (c != ' ' && c != '\t' && c != '\r' && c != '\n') {
            return false
        }
    }
    return true
}


/**
 * 获取当前进程名称
 *
 * @return processName
 */
fun getCurrentProcessName(application: Application): String? {
    val currentProcessId = Process.myPid()
    val activityManager = application.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
    val runningAppProcesses = activityManager.runningAppProcesses
    for (runningAppProcess in runningAppProcesses) {
        if (runningAppProcess.pid == currentProcessId) {
            return runningAppProcess.processName
        }
    }
    return null
}

/**
 * SD卡是否可用.
 */
fun sdCardIsAvailable(): Boolean {
    if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED) {
        val sd = File(Environment.getExternalStorageDirectory().path)
        return sd.canWrite()
    } else {
        return false
    }
}

/**
 * 得到SD卡根目录.
 */
fun getRootPath(): File? {
    var path: File? = null
    if (sdCardIsAvailable()) {
        path = Environment.getExternalStorageDirectory() // 取得sdcard文件路径
    } else {
        path = Environment.getDataDirectory()
    }
    return path
}

/**
 * 获取application中指定的meta-data
 *
 * @return 如果没有获取成功(没有对应值，或者异常)，则返回值为空
 */
fun getAppMetaData(ctx: Context?, key: String): String? {
    if (ctx == null || TextUtils.isEmpty(key)) {
        return null
    }
    var resultData: String? = null
    try {
        val packageManager = ctx.packageManager
        if (packageManager != null) {
            val applicationInfo = packageManager.getApplicationInfo(ctx.packageName, PackageManager.GET_META_DATA)
            if (applicationInfo != null) {
                if (applicationInfo.metaData != null) {
                    resultData = applicationInfo.metaData.get(key)!!.toString() + ""
                }
            }
        }
    } catch (e: PackageManager.NameNotFoundException) {
        e.printStackTrace()
    }

    return resultData
}

/**
 * 只有在测试环境下才弹出蒲公英的升级弹框
 */
fun dealPGYTestUpdate(activity: Activity, appVerCode: String, appVerName: String) {
    if (!getAppIsOnlineVersion(activity)) {
        PgyUpdateManager.setIsForced(false) //设置是否强制更新。true为强制更新；false为不强制更新（默认值）
        PgyUpdateManager.register(activity, object : UpdateManagerListener() {

            override fun onUpdateAvailable(result: String) {
                // 将新版本信息封装到AppBean中
                val appBean = UpdateManagerListener.getAppBeanFromString(result)
                AlertDialog.Builder(activity)
                        .setTitle("测试版本有更新!")
                        .setMessage("环境：" + getAppUrlEnvironment(activity) + " - " + appVerCode + " - "
                                + appVerName + " - " + getAppMetaData(activity, "UMENG_CHANNEL") + "\n\n"
                                + "修复问题：\n" + appBean.releaseNote + "\n\n"
                                + "该升级只会出现在测试版本上，线上版本不受影响;\n该版本号为测试版本号，与线上版本号不保持同步;\n测试版本和正式版本升级有冲突，如要测试正式版本升级，请联系开发人员。")
                        .setNegativeButton("取消") { dialog, which -> dialog.dismiss() }
                        .setPositiveButton("立即升级") { dialog, which -> UpdateManagerListener.startDownloadTask(activity, appBean.downloadURL) }
                        .show()
            }

            override fun onNoUpdateAvailable() {}
        })
    }

}
