package com.brilliantzhao.baselibrary.webview

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.KeyEvent
import android.view.View
import com.blankj.utilcode.util.LogUtils
import com.brilliantzhao.baselibrary.R
import com.brilliantzhao.baselibrary.base.BaseBindingActivity
import com.brilliantzhao.baselibrary.constant.WEBVIEW_URL
import com.brilliantzhao.baselibrary.databinding.ActivityCommonWebviewBinding
import java.net.URLDecoder

/**
 * description:
 * Date: 2018/2/7 14:00
 * User: BrilliantZhao
 */
class CommonWebViewActivity : BaseBindingActivity<ActivityCommonWebviewBinding>() {

    //##########################  custom variables start ##########################################

    private var mFragmentManager: FragmentManager? = null

    private var mAgentWebFragment: BaseWebViewFragment? = null

    var webview_url = ""

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun getContentViewId(): Int {
        return R.layout.activity_common_webview
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityCommonWebviewBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
        webview_url = URLDecoder.decode(intent.extras.getString(WEBVIEW_URL))
        LogUtils.i(webview_url)

        /*唯品会*/ // https://m.vip.com/?source=www&jump_https=1
        /*下载文件*/ // http://android.myapp.com/
        /*input标签上传文件*/ // file:///android_asset/upload_file/uploadfile.html
        /*Js上传文件*/ // file:///android_asset/upload_file/jsuploadfile.html
        /*Js*/ // file:///android_asset/js_interaction/hello.html
        /*优酷*/ // http://m.youku.com/video/id_XODEzMjU1MTI4.html
        /*淘宝*/ // https://m.taobao.com/?sprefer=sypc00
        /*豌豆荚*/ // http://www.wandoujia.com/apps
        /*短信*/ // file:///android_asset/sms/sms.html
        /*地图*/ // https://map.baidu.com/mobile/webapp/index/index/#index/index/foo=bar/vt=map
        /*首屏秒开*/ // http://mc.vip.qq.com/demo/indexv3

        webview_url = "https://m.vip.com/?source=www&jump_https=1"

        setToolBarGone()
        mFragmentManager = this.supportFragmentManager
    }

    override fun initEvent() {
    }

    override fun initData() {
        openFragment()
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    fun openFragment() {
        val ft = mFragmentManager?.beginTransaction()
        var mBundle: Bundle? = Bundle()
        mAgentWebFragment = BaseWebViewFragment.newInstance(mBundle)
        ft?.add(R.id.container_framelayout, mAgentWebFragment,
                BaseWebViewFragment::class.java!!.getName())
        mBundle?.putString(WEBVIEW_URL, webview_url)
        ft?.commit()
    }
    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        //一定要保证 mAentWebFragemnt 回调
        mAgentWebFragment?.onActivityResult(requestCode, resultCode, data)
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent): Boolean {
        val mAgentWebFragment = this.mAgentWebFragment
        if (mAgentWebFragment != null) {
            val mFragmentKeyDown = mAgentWebFragment
            return if (mFragmentKeyDown.onFragmentKeyDown(keyCode, event))
                true
            else
                super.onKeyDown(keyCode, event)
        }
        return super.onKeyDown(keyCode, event)
    }

    //######################   override third methods end  ########################################

}