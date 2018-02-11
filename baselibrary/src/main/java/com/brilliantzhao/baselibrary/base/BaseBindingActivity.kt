package com.brilliantzhao.baselibrary.base

import android.content.Context
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.brilliantzhao.baselibrary.R
import com.umeng.analytics.MobclickAgent
import kotlinx.android.synthetic.main.activity_base.*
import kotlinx.android.synthetic.main.layout_toolbar_style0.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
abstract class BaseBindingActivity<B : ViewDataBinding> : AppCompatActivity(), View.OnClickListener,
        BaseView {

    //##########################  custom variables start ##########################################

    lateinit var mBinding: B

    val className = this.javaClass.simpleName

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
        //=== EventBus
        EventBus.getDefault().register(this)
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

    // eventbus
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        /* Do something */
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {
        }
    }

    override fun onResume() {
        super.onResume()
        // UMeng Session统计
        MobclickAgent.onResume(this)
    }

    override fun onPause() {
        super.onPause()
        // UMeng Session统计
        MobclickAgent.onPause(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        // EventBus
        EventBus.getDefault().unregister(this)
    }

    //######################   override third methods end  ########################################

}