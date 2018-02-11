package com.brilliantzhao.baselibrary.base

import android.content.Context

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
interface BaseView {

    /**
     *  初始化view
     */
    fun initView()

    /**
     * 事件的绑定
     */
    fun initEvent()

    /**
     * 页面数据初始化
     */
    fun initData()

    /**
     * 显示一个loading
     *
     */
    fun showLoading()

    /**
     * 显示一个loading
     *
     */
    fun showLoading(content: String)

    /**
     * 关闭loading
     */
    fun dismissLoading()

    /**
     * 弹一个吐司提示
     *
     * @param msg  提示信息
     */
    fun showTipMessage(msg: String)

    /**
     * 弹一个吐司提示
     *
     * @param code code
     * @param msg  提示信息
     */
    fun showTipMessage(code: Int, msg: String)

    /**
     * 获得上下文对象
     *
     * @return 上下文对象
     */
    fun getContext(): Context

}