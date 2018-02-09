package com.brilliantzhao.baselibrary.api

import com.brilliantzhao.baselibrary.constant.APP_URL_ENVIRONMENT_RELEASE
import com.brilliantzhao.baselibrary.constant.APP_URL_ENVIRONMENT_SIT
import com.brilliantzhao.baselibrary.constant.APP_URL_ENVIRONMENT_UAT
import com.brilliantzhao.baselibrary.util.getAppUrlEnvironment

/**
 * description:
 * Date: 2018/2/9 10:12
 * User: BrilliantZhao
 */

private val REQUEST_HEAD_SIT = "http://gank.io/api"

private val REQUEST_HEAD_UAT = "http://gank.io/api"

private val REQUEST_HEAD_RELEASE = "http://gank.io/api"

private val APP_URL_ENVIRONMENT = getAppUrlEnvironment()

/**
 * 根据当前的模式获取对应的请求头部
 */
fun getRequestHead(): String {
    // 默认为release环境
    var rootHttp = REQUEST_HEAD_RELEASE + "/"

    when (APP_URL_ENVIRONMENT) {
        APP_URL_ENVIRONMENT_SIT -> {
            rootHttp = REQUEST_HEAD_SIT + "/"
        }
        APP_URL_ENVIRONMENT_UAT -> {
            rootHttp = REQUEST_HEAD_UAT + "/"
        }
        APP_URL_ENVIRONMENT_RELEASE -> {
            rootHttp = REQUEST_HEAD_RELEASE + "/"
        }
    }
    return rootHttp
}