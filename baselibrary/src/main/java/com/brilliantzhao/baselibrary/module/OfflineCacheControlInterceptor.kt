package com.brilliantzhao.baselibrary.module


import com.blankj.utilcode.util.EncryptUtils
import com.blankj.utilcode.util.NetworkUtils
import com.blankj.utilcode.util.ScreenUtils
import com.blankj.utilcode.util.TimeUtils
import com.brilliantzhao.baselibrary.constant.*
import okhttp3.Interceptor
import okhttp3.Response
import java.io.IOException

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class OfflineCacheControlInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        //=== 动态请求的头部数据
        val sessiontoken = ""
        val timestamp = TimeUtils.getNowMills().toString() + ""
        val sign = getSign(sessiontoken, timestamp)
        //===
        val request = chain.request().newBuilder()
                //=== 添加固定head参数
                .addHeader(APP_APP_VERSION, "")
                .addHeader(APP_CHANNEL, "")
                .addHeader(APP_HEIGHT, ScreenUtils.getScreenHeight().toString() + "")
                .addHeader(APP_WIDTH, ScreenUtils.getScreenWidth().toString() + "")
                .addHeader(APP_MAC_ADDRESS, "")
                .addHeader(APP_OS_VERSION, "")
                .addHeader(APP_PHONE_MODEL, "")
                .addHeader(APP_PLATFORM, "Android")
                .addHeader(APP_CLIENT_IP, "")
                .addHeader(APP_SERVICE_PROVIDER, "")
                .addHeader(APP_IMEI, "")
                //===
                .addHeader(APP_NETWORK_TYPE, NetworkUtils.getNetworkType().name)
                .addHeader(APP_SESSION_TOKEN, sessiontoken)
                .addHeader(APP_TIMESTAMP, timestamp)
                .addHeader(APP_SIGN, sign ?: "")
                //===
                .build()

        return chain.proceed(request)
    }

    companion object {

        /**
         * 时间戳和 token的MD5加密串
         *
         * @param session_token
         * @param timestamp
         * @return
         */
        fun getSign(session_token: String, timestamp: String): String? {
            return EncryptUtils.encryptMD5ToString(session_token + timestamp)
        }
    }

}

