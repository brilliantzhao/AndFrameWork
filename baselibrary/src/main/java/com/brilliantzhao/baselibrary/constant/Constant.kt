package com.brilliantzhao.baselibrary.constant

import com.brilliantzhao.baselibrary.util.getRootPath
import java.io.File

/**
 * description:
 * Date: 2018/2/9 09:38
 * User: BrilliantZhao
 */

//=== 当前的服务器环境
val APP_URL_ENVIRONMENT_SIT = "SIT"

val APP_URL_ENVIRONMENT_UAT = "UAT"

val APP_URL_ENVIRONMENT_RELEASE = "RELEASE"

/**
 * json request headers 参数
 */
val APP_APP_VERSION = "appversion"// 软件版本 软件版本 v1.0_beta 1.0
val APP_CHANNEL = "channel"// 渠道
val APP_CLIENT_IP = "clientip"// 客户端IP
val APP_HEIGHT = "height"// 手机屏幕的高度
val APP_WIDTH = "width"// 手机屏幕的宽度
val APP_MAC_ADDRESS = "macAddress"// 手机的MAC地址
val APP_NETWORK_TYPE = "networktype"// 网络类型 1:wifi 2:3g 3:gprs 4:其他
val APP_OS_VERSION = "osversion"// 系统版本 4.2.1
val APP_PHONE_MODEL = "phonemodel"// 手机型号 MI-ONE C1/HTC T329t
val APP_PLATFORM = "platform"// 平台类型 //IOS,WAP,WEB,Android
val APP_SERVICE_PROVIDER = "serviceprovider"// 运营商 运营商1：移动 2：电信3：联通4：其他
val APP_SESSION_TOKEN = "sessiontoken"// token
val APP_SIGN = "sign"// 时间戳和 token的MD5加密串
val APP_TIMESTAMP = "timestamp"// 时间戳
val APP_IMEI = "imei"// 设备ID

/**
 * App根目录
 *
 * @return
 */
fun getAppPathRoot(): String {
    return (getRootPath()?.absolutePath ?: "") + File.separator + "AndFrame" + File.separator
}

//=== 默认存放图片的路径
val DEFAULT_SAVE_IMAGE_PATH = (getAppPathRoot()
        + "image"
        + File.separator)

//=== 默认存放安装包下载的路径
val DEFAULT_SAVE_FILE_PATH = (getAppPathRoot()
        + "download"
        + File.separator)

//=== 默认存放异常日志文件的路径
val DEFAULT_CRASH_FILE_PATH = (getAppPathRoot()
        + "crash"
        + File.separator)

//=== 默认存放缓存文件的路径
val DEFAULT_CACHE_FILE_PATH = (getAppPathRoot()
        + "cache"
        + File.separator)

//===
val WEBVIEW_URL = "URL" // webview加载的url