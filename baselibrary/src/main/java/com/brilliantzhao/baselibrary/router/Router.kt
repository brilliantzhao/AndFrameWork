package com.brilliantzhao.baselibrary.router

import android.content.Context
import android.content.Intent
import android.net.Uri
import com.brilliantzhao.baselibrary.constant.WEBVIEW_URL
import com.brilliantzhao.baselibrary.webview.BaseWebViewActivity
import com.brilliantzhao.baselibrary.webview.CommonWebViewActivity

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
object Router {

    /**
     *
     */
    fun router(context: Context, uri: String) {
        val intent = Intent()
        intent.data = Uri.parse(uri)
        intent.action = Intent.ACTION_VIEW
        context.startActivity(intent)
    }

    /**
     * 启动BaseWebViewActivity
     */
    fun startBaseWebViewActivity(context: Context, url: String) {
        var intent = Intent(context, BaseWebViewActivity::class.java)
        intent.putExtra(WEBVIEW_URL, url)
        context.startActivity(intent)
    }

    /**
     * 启动CommonWebViewActivity
     */
    fun startCommonWebViewActivity(context: Context, url: String) {
        var intent = Intent(context, CommonWebViewActivity::class.java)
        intent.putExtra(WEBVIEW_URL, url)
        context.startActivity(intent)
    }

}