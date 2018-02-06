package com.brilliantzhao.baselibrary.router

import android.content.Context
import android.content.Intent
import android.net.Uri

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
object GankRouter {
    fun router(context: Context, uri: String) {
        val intent = Intent()
        intent.data = Uri.parse(uri)
        intent.action = Intent.ACTION_VIEW
        context.startActivity(intent)
    }

}