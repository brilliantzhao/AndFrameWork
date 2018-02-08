package com.brilliantzhao.baselibrary.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.umeng.analytics.MobclickAgent

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
abstract class BaseBingingFragment<B : ViewDataBinding> : Fragment(), View.OnClickListener,
        BaseView {

    //##########################  custom variables start ##########################################

    lateinit var mBinding: B

    val className = this.javaClass.simpleName

    //=== 懒加载实现
    protected var mIsLoadedData = false

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        //===
        mBinding = createDataBinding(inflater, container, savedInstanceState)
        //===
        initView()
        initEvent()
        return mBinding.root
    }

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

    /**
     *
     */
    override fun getContext(): Context {
        return activity as Context
    }

    /**
     * 懒加载一次。如果只想在对用户可见时才加载数据，并且只加载一次数据，在子类中重写该方法
     */
    abstract fun onLazyLoadOnce()

    /**
     * 对用户可见时触发该方法。如果只想在对用户可见时才加载数据，在子类中重写该方法
     */
    abstract fun onVisibleToUser()

    /**
     * 对用户不可见时触发该方法
     */
    abstract fun onInvisibleToUser()

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    abstract fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?,
                                   savedInstanceState: Bundle?): B

    fun getColorByContext(colorId: Int): Int {
        return ContextCompat.getColor(context, colorId)
    }

    /**
     * 处理对用户是否可见
     *
     * @param isVisibleToUser
     */
    private fun handleOnVisibilityChangedToUser(isVisibleToUser: Boolean) {
        if (isVisibleToUser) {
            // 对用户可见
            if (!mIsLoadedData) {
                mIsLoadedData = true
                onLazyLoadOnce()
            }
            onVisibleToUser()
        } else {
            // 对用户不可见
            onInvisibleToUser()
        }
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        super.setUserVisibleHint(isVisibleToUser)
        if (isResumed) {
            handleOnVisibilityChangedToUser(isVisibleToUser)
        }
    }

    override fun onResume() {
        super.onResume()
        if (userVisibleHint) {
            handleOnVisibilityChangedToUser(true)
        }
        // UMeng Session统计
        MobclickAgent.onPageStart(className)
    }

    override fun onPause() {
        super.onPause()
        if (userVisibleHint) {
            handleOnVisibilityChangedToUser(false)
        }
        // UMeng Session统计
        MobclickAgent.onPageEnd(className)
    }

    //######################   override third methods end  ########################################

}