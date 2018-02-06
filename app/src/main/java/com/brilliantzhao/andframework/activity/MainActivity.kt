package com.brilliantzhao.andframework.activity

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
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
import com.brilliantzhao.baselibrary.projectbean.FuckGoods
import com.brilliantzhao.baselibrary.router.GankClientUri
import com.brilliantzhao.baselibrary.router.GankRouter
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

    lateinit var mFragments: MutableList<Fragment>

    @Inject
    lateinit var mPresenter: RandomPresenter

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityMainBinding {
        return DataBindingUtil.setContentView(this, R.layout.activity_main)
    }

    override fun initView() {
        initFragments()
        getMainComponent().plus(RandomModule(this)).inject(this)
        viewPager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int) = mFragments[position]
            override fun getCount() = mFragments.size
        }

        viewPager.offscreenPageLimit = 4

        navigationView.setOnNavigationItemSelectedListener { item ->
            var tab = 0
            when (item.itemId) {
                R.id.menu_android -> tab = 0
                R.id.menu_ios -> tab = 1
                R.id.menu_girl -> tab = 2
                R.id.menu_about -> tab = 3
            }
            viewPager.currentItem = tab
            false
        }

        floatingButton.setOnClickListener {
            mPresenter.getRandom("Android")
        }

    }

    override fun onRandom(goods: FuckGoods) {
        val url = URLEncoder.encode(goods.url)
        GankRouter.router(this, GankClientUri.DETAIL + url)
    }

    private fun initFragments() {
        mFragments = ArrayList()
        mFragments.add(AndroidFragment.newInstance())
        mFragments.add(IOSFragment.newInstance())
        mFragments.add(GirlFragment.newInstance())
        mFragments.add(FragmentHolder())
    }

}
