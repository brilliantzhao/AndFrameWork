package com.brilliantzhao.andframework.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import com.brilliantzhao.andframework.adapter.FuckGoodsAdapter
import com.brilliantzhao.andframework.component.FuckGoodsModule
import com.brilliantzhao.andframework.contract.FuckGoodsContract
import com.brilliantzhao.andframework.databinding.ViewRecyclerBinding
import com.brilliantzhao.andframework.getMainComponent
import com.brilliantzhao.andframework.presenter.FuckGoodsPresenter
import com.brilliantzhao.baselibrary.base.BaseBingingFragment
import com.brilliantzhao.baselibrary.projectbean.FuckGoods
import com.brilliantzhao.baselibrary.router.GankClientUri
import com.brilliantzhao.baselibrary.router.GankRouter
import java.net.URLEncoder
import java.util.*
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class AndroidFragment : BaseBingingFragment<ViewRecyclerBinding>(), FuckGoodsContract.View {

    private var mList = ArrayList<FuckGoods>()
    private lateinit var mAdapter: FuckGoodsAdapter
    private var mPage = 1
    @Inject
    lateinit var mPresenter: FuckGoodsPresenter

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
            GankRouter.router(context, GankClientUri.DETAIL + url)
        }

    }

    override fun setData(results: List<FuckGoods>) {
        mList.addAll(results)
        mAdapter.notifyDataSetChanged()
    }

    override fun onDestroy() {
        super.onDestroy()
        mPresenter.unSubscribe()
    }

    companion object {
        val ANDROID = "ANDROID"
        fun newInstance(): AndroidFragment {
            val fragment = AndroidFragment()
            val bundle = Bundle()
            fragment.arguments = bundle
            return fragment
        }
    }

}