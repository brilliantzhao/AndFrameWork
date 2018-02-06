package com.brilliantzhao.andframework

import android.content.Context
import android.widget.Toast

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
fun Context.getMainComponent() = App.instance.apiComponent

fun Context.toast(msg: String, length: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, msg, length).show()
}