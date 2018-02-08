package com.brilliantzhao.baselibrary.stetho

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.widget.Toast

import com.brilliantzhao.baselibrary.R
import com.facebook.stetho.InspectorModulesProvider
import com.facebook.stetho.Stetho
import com.facebook.stetho.inspector.protocol.ChromeDevtoolsDomain
import com.facebook.stetho.rhino.JsRuntimeReplFactoryBuilder

import org.mozilla.javascript.BaseFunction
import org.mozilla.javascript.Scriptable

import java.util.concurrent.atomic.AtomicBoolean

/**
 * description:
 * Date: 2017/12/4 0004 08:18
 * User: BrilliantZhao
 */
class ExtInspectorModulesProvider(private val mContext: Context, private val packageName: String) : InspectorModulesProvider {

    private val handler = Handler(Looper.getMainLooper())

    override fun get(): Iterable<ChromeDevtoolsDomain> {
        return Stetho.DefaultInspectorModulesBuilder(mContext)
                .runtimeRepl(JsRuntimeReplFactoryBuilder(mContext)
                        // 添加变量
                        .addVariable("test", AtomicBoolean(true))
                        // 添加类
                        .importClass(R::class.java)
                        // 添加包
                        .importPackage(packageName)
                        // 添加方法到 javascript: void toast(String)
                        .addFunction("toast", object : BaseFunction() {
                            override fun call(cx: org.mozilla.javascript.Context?, scope: Scriptable?, thisObj: Scriptable?, args: Array<Any>?): Any {

                                // javascript 传递的参数在 varags
                                val message = args!![0].toString()
                                handler.post { Toast.makeText(mContext, message, Toast.LENGTH_LONG).show() }

                                // 在 javascript 返回 undefined
                                return org.mozilla.javascript.Context.getUndefinedValue()
                            }
                        })
                        .build())
                .finish()
    }
}