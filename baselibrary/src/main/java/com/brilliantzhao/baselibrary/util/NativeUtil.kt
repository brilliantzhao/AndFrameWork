package com.brilliantzhao.baselibrary.util

import android.app.ActivityManager
import android.app.Application
import android.content.Context
import android.os.Environment
import android.os.Process
import com.brilliantzhao.baselibrary.R
import com.brilliantzhao.baselibrary.base.BaseApplication
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
fun getAppIsOnlineVersion(): Boolean {
    return "TRUE" == BaseApplication.instance.getResources().getString(R.string.APP_IS_ONLINE_VERSION)
}

/**
 * 判断日志是否展示
 *
 * @return
 */
fun getAppIsLogShowLog(): Boolean {
    return "TRUE" == BaseApplication.instance.getResources().getString(R.string.APP_IS_SHOW_LOG)
}

/**
 * 获取app的url环境，在gradle.properties中配置
 *
 * @return
 */
fun getAppUrlEnvironment(): String {
    return BaseApplication.instance.getResources().getString(R.string.APP_URL_ENVIRONMENT)
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