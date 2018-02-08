package com.brilliantzhao.andframework.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.blankj.utilcode.util.LogUtils
import com.brilliantzhao.andframework.adapter.FuckGoodsAdapter
import com.brilliantzhao.andframework.component.FuckGoodsModule
import com.brilliantzhao.andframework.contract.FuckGoodsContract
import com.brilliantzhao.andframework.databinding.ViewRecyclerBinding
import com.brilliantzhao.andframework.getMainComponent
import com.brilliantzhao.andframework.presenter.FuckGoodsPresenter
import com.brilliantzhao.baselibrary.base.BaseBingingFragment
import com.brilliantzhao.baselibrary.examplebean.FuckGoods
import com.brilliantzhao.baselibrary.router.ExampleClientUri
import com.brilliantzhao.baselibrary.router.Router
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class AndroidFragment : BaseBingingFragment<ViewRecyclerBinding>(), FuckGoodsContract.View {

    //##########################  custom variables start ##########################################

    private var mList = ArrayList<FuckGoods>()

    private lateinit var mAdapter: FuckGoodsAdapter

    private var mPage = 1

    @Inject
    lateinit var mPresenter: FuckGoodsPresenter

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?,
                                   savedInstanceState: Bundle?): ViewRecyclerBinding {
        return ViewRecyclerBinding.inflate(inflater!!, container, false)
    }

    override fun initView() {
        mAdapter = FuckGoodsAdapter(mList)
        context.getMainComponent().plus(FuckGoodsModule(this)).inject(this)
        with(mBinding!!) {
            recyclerView.adapter = mAdapter
            recyclerView.layoutManager = LinearLayoutManager(context)

            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView?.canScrollVertically(1)!!) {
                        mPresenter.getData(++mPage, ANDROID)
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
        }

        mPresenter.getData(mPage, ANDROID)

        mAdapter.setOnItemClickListener { pos ->
            val url = URLEncoder.encode(mList[pos].url)
            Router.router(context, ExampleClientUri.DETAIL + url)
        }
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun onLazyLoadOnce() {
        LogUtils.i(className + "-->onLazyLoadOnce")
    }

    override fun onVisibleToUser() {
        LogUtils.i(className + "-->onVisibleToUser")
    }

    override fun onInvisibleToUser() {
        LogUtils.i(className + "-->onInvisibleToUser")
    }

    override fun setData(results: List<FuckGoods>) {
        mList.addAll(results)
        mAdapter.notifyDataSetChanged()
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        val ANDROID = "ANDROID"
        fun newInstance(): AndroidFragment {
            val fragment = AndroidFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscribe()
    }

    //######################   override third methods end  ########################################

}