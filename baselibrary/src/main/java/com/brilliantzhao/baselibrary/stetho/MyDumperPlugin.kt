package com.brilliantzhao.baselibrary.stetho

import android.content.Context
import com.facebook.stetho.dumpapp.DumpUsageException
import com.facebook.stetho.dumpapp.DumperContext
import com.facebook.stetho.dumpapp.DumperPlugin

/**
 * description:
 * Date: 2017/12/4 0004 08:05
 * User: BrilliantZhao
 */
class MyDumperPlugin(context: Context) : DumperPlugin {
    private val mAppContext: Context

    init {
        mAppContext = context.applicationContext
    }

    override fun getName(): String {
        return NAME
    }

    @Throws(DumpUsageException::class)
    override fun dump(dumpContext: DumperContext) {
        val writer = dumpContext.stdout
        val args = dumpContext.argsAsList

        val commandName = if (args.isEmpty()) "" else args.removeAt(0)

        if (commandName == "print") {
            //  doPrint(writer, args);
        } else if (commandName == "write") {
            // doWrite(args);
        } else {
            // doUsage(writer);
        }
    }

    companion object {

        private val XML_SUFFIX = ".xml"
        private val NAME = "prefs"
    }

    // 省略部分代码

}
