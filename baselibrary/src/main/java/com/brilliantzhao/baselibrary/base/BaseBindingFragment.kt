package com.brilliantzhao.baselibrary.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
abstract class BaseBingingFragment<B : ViewDataBinding> : Fragment(), BaseView {

    lateinit var mBinding: B

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //===
        mBinding = createDataBinding(inflater, container, savedInstanceState)
        //===
        initView()
        initEvent()
        return mBinding.root
    }

    abstract fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): B

    override fun initEvent() {}

    /**
     * 显示一个loading
     *
     */
    override fun showLoading() {
    }

    /**
     * 显示一个loading
     */
    override fun showLoading(content: String) {
    }

    /**
     * 关闭loading
     */
    override fun dismissLoading() {
    }

    /**
     * 弹一个吐司提示
     *
     * @param msg  提示信息
     */
    override fun showTipMessage(msg: String) {
    }

    /**
     * 弹一个吐司提示
     *
     * @param code code
     * @param msg  提示信息
     */
    override fun showTipMessage(code: Int, msg: String) {
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

    override fun getContext(): Context {
        return activity
    }

    fun getColorByContext(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

}