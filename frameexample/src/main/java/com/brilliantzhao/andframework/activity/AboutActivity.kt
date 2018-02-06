package com.brilliantzhao.andframework.activity

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.airbnb.deeplinkdispatch.DeepLink
import com.brilliantzhao.andframework.R
import com.brilliantzhao.baselibrary.router.GankClientUri

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@DeepLink(GankClientUri.ABOUT)
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
    }
}
