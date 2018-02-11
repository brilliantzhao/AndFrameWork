package com.brilliantzhao.baselibrary.webview

import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.view.KeyEvent
import android.view.View
import com.brilliantzhao.baselibrary.R
import com.brilliantzhao.baselibrary.base.BaseBindingActivity
import com.brilliantzhao.baselibrary.databinding.ActivityModelBinding

/**
 * description:
 * Date: 2018/2/7 14:00
 * User: BrilliantZhao
 */
class CommonWebViewActivity : BaseBindingActivity<ActivityModelBinding>() {

    //##########################  custom variables start ##########################################

    private var mFragmentManager: FragmentManager? = null

    private var mAgentWebFragment: BaseWebViewFragment? = null

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun getContentViewId(): Int {
        return R.layout.activity_model
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityModelBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
        mFragmentManager = this.supportFragmentManager
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    fun openFragment() {
        val ft = mFragmentManager?.beginTransaction()
        var mBundle: Bundle? = Bundle()
        mAgentWebFragment = BaseWebViewFragment.newInstance(mBundle)
        ft?.add(R.id.container_framelayout, mAgentWebFragment,
                BaseWebViewFragment::class.java!!.getName())
        mBundle?.putString("URL", "https://m.vip.com/?source=www&jump_https=1")
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