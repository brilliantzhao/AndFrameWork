package com.brilliantzhao.andframework.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import com.airbnb.deeplinkdispatch.DeepLink
import com.brilliantzhao.andframework.R
import com.brilliantzhao.andframework.databinding.ActivityDetailBinding
import com.brilliantzhao.baselibrary.base.BaseBindingActivity
import com.brilliantzhao.baselibrary.router.ExampleClientUri
import kotlinx.android.synthetic.main.activity_detail.*
import java.net.URLDecoder

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@DeepLink("gank://androidwing.net/detail/{${ExampleClientUri.DETAIL_PARAM_URL}}")
class DetailActivity : BaseBindingActivity<ActivityDetailBinding>() {

    //##########################  custom variables start ##########################################

    var url = ""

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun getContentViewId(): Int {
        return R.layout.activity_detail
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityDetailBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
        if (intent.getBooleanExtra(DeepLink.IS_DEEP_LINK, false)) {
            url = URLDecoder.decode(intent.extras.getString(ExampleClientUri.DETAIL_PARAM_URL))
        }

        webView.loadUrl(url)
        webView.setWebViewClient(object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
        )
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    //######################   override third methods end  ########################################

}
