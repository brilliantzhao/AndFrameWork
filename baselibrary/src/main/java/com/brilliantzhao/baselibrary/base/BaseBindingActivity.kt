package com.brilliantzhao.baselibrary.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.brilliantzhao.baselibrary.R
import kotlinx.android.synthetic.main.activity_base.*

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
abstract class BaseBindingActivity<B : ViewDataBinding> : AppCompatActivity(), View.OnClickListener,
        BaseView {

    //##########################  custom variables start ##########################################

    lateinit var mBinding: B

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //===
        mBinding = createDataBinding(savedInstanceState)
        //===
        setContentView(R.layout.activity_base)
        base_content_layout.addView(layoutInflater.inflate(getContentViewId(), null), 0)
        setSupportActionBar(base_toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayShowTitleEnabled(false)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        base_toolbar.setNavigationOnClickListener { finish() }
        //===
        initView()
        initEvent()
        initData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    //################################################################# loading的处理 start

    /**
     * 显示一个loading
     */
    override fun showLoading() {
        base_loading_layout.visibility = View.VISIBLE
    }

    /**
     * 显示一个loading
     */
    override fun showLoading(content: String) {
        base_loading_layout.visibility = View.VISIBLE
    }

    /**
     * 关闭loading
     */
    override fun dismissLoading() {
        base_loading_layout.visibility = View.GONE
    }

    //################################################################# loading的处理 end

    //################################################################# toast提示处理 start

    /**
     * 弹一个吐司提示
     *
     * @param msg  提示信息
     */
    override fun showTipMessage(msg: String) {
        Snackbar.make(base_layout, msg, Snackbar.LENGTH_SHORT).show()
    }

    /**
     * 弹一个吐司提示
     *
     * @param code code
     * @param msg  提示信息
     */
    override fun showTipMessage(code: Int, msg: String) {
        Snackbar.make(base_layout, msg, Snackbar.LENGTH_SHORT).show()
    }

    //################################################################# toast提示处理 end

    /**
     * 获得上下文对象
     *
     * @return 上下文对象
     */
    override fun getContext(): Context {
        return this
    }

    /**
     * 管理 网络请求生命周期的 key
     *
     * @return key
     */
    override fun getNetKey(): String {
        return javaClass.simpleName
    }

    /**
     * 因为token相关错误需要跳转到登录页面
     */
    override fun toLoginActBySessionError() {}

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    abstract fun getContentViewId(): Int

    abstract fun createDataBinding(savedInstanceState: Bundle?): B

    //################################################################# toolBar的处理 start

    fun setToolBarGone() {
        base_toolbar.visibility = View.GONE
    }

    /**
     * 设置标题
     */
    fun setToolBarTitle(title: String) {
        base_toolbar.visibility = View.VISIBLE
        base_toolbar.title = title
    }

    //################################################################# toolBar的处理 end

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    //######################   override third methods end  ########################################

}