package com.brilliantzhao.andframework.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.content.ContextCompat
import android.view.View
import com.brilliantzhao.andframework.R
import com.brilliantzhao.andframework.component.RandomModule
import com.brilliantzhao.andframework.contract.RandomContract
import com.brilliantzhao.andframework.databinding.ActivityMainBinding
import com.brilliantzhao.andframework.fragment.AndroidFragment
import com.brilliantzhao.andframework.fragment.FragmentHolder
import com.brilliantzhao.andframework.fragment.GirlFragment
import com.brilliantzhao.andframework.fragment.IOSFragment
import com.brilliantzhao.andframework.getMainComponent
import com.brilliantzhao.andframework.presenter.RandomPresenter
import com.brilliantzhao.baselibrary.base.BaseBindingActivity
import com.brilliantzhao.baselibrary.examplebean.FuckGoods
import com.brilliantzhao.baselibrary.router.ExampleClientUri
import com.brilliantzhao.baselibrary.router.Router
import com.brilliantzhao.baselibrary.util.dealPGYTestUpdate
import com.pgyersdk.update.PgyUpdateManager
import kotlinx.android.synthetic.main.activity_main.*
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class MainActivity : BaseBindingActivity<ActivityMainBinding>(), RandomContract.View {

    //##########################  custom variables start ##########################################

    private var lastSelectedPosition = 0

    lateinit var mFragments: MutableList<Fragment>

    @Inject
    lateinit var mPresenter: RandomPresenter

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun getContentViewId(): Int {
        return R.layout.activity_main
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
        setToolBarGone()
    }

    override fun initEvent() {
        findViewById<View>(R.id.ll_item0).setOnClickListener(this)
        findViewById<View>(R.id.ll_item1).setOnClickListener(this)
        findViewById<View>(R.id.ll_item2).setOnClickListener(this)
        findViewById<View>(R.id.ll_item3).setOnClickListener(this)
        findViewById<View>(R.id.floatingButton).setOnClickListener(this)
    }

    override fun initData() {
        initFragments()
        getMainComponent().plus(RandomModule(this)).inject(this)
        //viewpager配置
        initViewPage()
        // 默认选中
        setViewPageDisPosition(lastSelectedPosition)
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    private fun initFragments() {
        mFragments = ArrayList()
        mFragments.add(AndroidFragment.newInstance())
        mFragments.add(IOSFragment.newInstance())
        mFragments.add(GirlFragment.newInstance())
        mFragments.add(FragmentHolder())
    }

    private fun initViewPage() {
        viewPager.isPagingEnabled = false //viewpager滑动控制
        viewPager.offscreenPageLimit = 4
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
        }
    }

    /**
     * 底部的tab项的点击事件
     *
     * @param position
     */
    private fun setViewPageDisPosition(position: Int) {
        viewPager.currentItem = position
        false
        iv_item0?.setImageResource(R.mipmap.icon_android)
        iv_item1?.setImageResource(R.mipmap.icon_android)
        iv_item2?.setImageResource(R.mipmap.icon_android)
        iv_item3?.setImageResource(R.mipmap.icon_android)
        tv_item0?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_8792A5))
        tv_item1?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_8792A5))
        tv_item2?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_8792A5))
        tv_item3?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_8792A5))

        if (position == 0) {
            iv_item0?.setImageResource(R.mipmap.ic_launcher)
            tv_item0?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_12151C))
        } else if (position == 1) {
            iv_item1?.setImageResource(R.mipmap.ic_launcher)
            tv_item1?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_12151C))
        } else if (position == 2) {
            iv_item2?.setImageResource(R.mipmap.ic_launcher)
            tv_item2?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_12151C))
        } else if (position == 3) {
            iv_item3?.setImageResource(R.mipmap.ic_launcher)
            tv_item3?.setTextColor(ContextCompat.getColor(getContext(), R.color.base_item_12151C))
        }
    }


    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {
            R.id.ll_item0 -> {
                lastSelectedPosition = 0
                setViewPageDisPosition(lastSelectedPosition)
            }
            R.id.ll_item1 -> {
                lastSelectedPosition = 1
                setViewPageDisPosition(lastSelectedPosition)
            }
            R.id.ll_item2 -> {
                lastSelectedPosition = 2
                setViewPageDisPosition(lastSelectedPosition)
            }
            R.id.ll_item3 -> {
                lastSelectedPosition = 3
                setViewPageDisPosition(lastSelectedPosition)
            }
            R.id.floatingButton -> {
                mPresenter.getRandom("Android")
            }
        }
    }

    override fun onRandom(goods: FuckGoods) {
        val url = URLEncoder.encode(goods.url)
        Router.router(this, ExampleClientUri.DETAIL + url)
    }

    override fun onResume() {
        super.onResume()
        // 处理蒲公英的持续集成
        dealPGYTestUpdate(this, getResources().getString(R.string.APP_VERCODE_FRAMEEXAMPLE_DEV),
                getResources().getString(R.string.APP_VERSION_FRAMEEXAMPLE_DEV))
    }

    override fun onDestroy() {
        super.onDestroy()
        // 蒲公英解除注册
        PgyUpdateManager.unregister()
    }

    //######################   override third methods end  ########################################
}
