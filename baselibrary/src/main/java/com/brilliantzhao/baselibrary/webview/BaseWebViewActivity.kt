package com.brilliantzhao.baselibrary.webview

import android.content.Intent
import android.databinding.DataBindingUtil
import android.graphics.Bitmap
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.LinearLayout
import com.blankj.utilcode.util.LogUtils
import com.brilliantzhao.baselibrary.R
import com.brilliantzhao.baselibrary.base.BaseBindingActivity
import com.brilliantzhao.baselibrary.databinding.ActivityBaseWebviewBinding
import com.brilliantzhao.baselibrary.widget.WebLayout
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import kotlinx.android.synthetic.main.activity_base_webview.*
import java.net.URLDecoder

/**
 * description: 基于AgentWeb封装的webview公共类
 * Date: 2018/2/7 14:00
 * User: BrilliantZhao
 */
class BaseWebViewActivity : BaseBindingActivity<ActivityBaseWebviewBinding>() {

    //##########################  custom variables start ##########################################

    protected var mAgentWeb: AgentWeb? = null

    var url = ""

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun getContentViewId(): Int {
        return R.layout.activity_base_webview
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityBaseWebviewBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
        url = URLDecoder.decode(intent.extras.getString("URL"))
        LogUtils.i(url)
    }

    override fun initEvent() {
    }

    override fun initData() {
        initAgentWebView()
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    private fun initAgentWebView() {
        mAgentWeb = AgentWeb.with(this)
                //传入AgentWeb 的父控件，如果父控件为 RelativeLayout,那么第二参数需要传入
                // RelativeLayout.LayoutParams,第一个参数和第二个参数应该对应。
                .setAgentWebParent(container, LinearLayout.LayoutParams(-1, -1))//
                .useDefaultIndicator() //使用默认进度条
                .setWebChromeClient(mWebChromeClient)
                .setWebViewClient(mWebViewClient)
                .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
                .setSecurityType(AgentWeb.SecurityType.STRICT_CHECK)
                .setWebLayout(WebLayout(this))
                .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)//打开其他应用时，弹窗咨询用户是否前往其他应用
                .interceptUnkownScheme() //拦截找不到相关页面的Scheme
                .createAgentWeb() //
                .ready()
                .go(url)
    }

    private val mWebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView, request: WebResourceRequest): Boolean {
            return super.shouldOverrideUrlLoading(view, request)
        }

        override fun onPageStarted(view: WebView, url: String, favicon: Bitmap?) {
            //do your work
            LogUtils.i("BaseWebActivity onPageStarted")
        }
    }

    private val mWebChromeClient = object : WebChromeClient() {

        override fun onProgressChanged(view: WebView, newProgress: Int) {
            //do your work
            LogUtils.i("Info", "progress:" + newProgress)
        }

        override fun onReceivedTitle(view: WebView, title: String) {
            super.onReceivedTitle(view, title)
            // 设置标题
            setToolBarTitle(title)
        }
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        return if (mAgentWeb?.handleKeyEvent(keyCode, event)!!) {
            true
        } else super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        mAgentWeb?.getWebLifeCycle()?.onPause()
        super.onPause()
    }

    override fun onResume() {
        mAgentWeb?.getWebLifeCycle()?.onResume()
        super.onResume()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        LogUtils.i("result:$requestCode result:$resultCode")
        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        mAgentWeb?.getWebLifeCycle()?.onDestroy()
    }

    //######################   override third methods end  ########################################

}