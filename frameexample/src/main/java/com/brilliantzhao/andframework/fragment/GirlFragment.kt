package com.brilliantzhao.andframework.fragment

import android.os.Bundle
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import com.brilliantzhao.andframework.R
import com.brilliantzhao.andframework.activity.ImageActivity
import com.brilliantzhao.andframework.adapter.GirlAdapter
import com.brilliantzhao.andframework.component.FuckGoodsModule
import com.brilliantzhao.andframework.contract.FuckGoodsContract
import com.brilliantzhao.andframework.databinding.ViewRecyclerBinding
import com.brilliantzhao.andframework.getMainComponent
import com.brilliantzhao.andframework.presenter.FuckGoodsPresenter
import com.brilliantzhao.baselibrary.base.BaseBingingFragment
import com.brilliantzhao.baselibrary.examplebean.FuckGoods
import kotlinx.android.synthetic.main.view_recycler.*
import java.util.*
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class GirlFragment : BaseBingingFragment<ViewRecyclerBinding>(), FuckGoodsContract.View {

    //##########################  custom variables start ##########################################

    private lateinit var mRecyclerView: RecyclerView

    private var mList = ArrayList<FuckGoods>()

    private lateinit var mAdapter: GirlAdapter

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
        mAdapter = GirlAdapter(mList)
        context.getMainComponent().plus(FuckGoodsModule(this)).inject(this)
        with(mBinding!!) {
            mRecyclerView = recyclerView
            recyclerView.adapter = mAdapter
            recyclerView.layoutManager = GridLayoutManager(context, 2)
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    if (!recyclerView?.canScrollVertically(1)!!) {
                        mPresenter.getData(++mPage, GIRL)
                    }
                }

                override fun onScrollStateChanged(recyclerView: RecyclerView?, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                }
            })
            mPresenter.getData(mPage, GIRL)
        }

        mAdapter.setOnItemClickListener { pos ->
            val imageView = recyclerView.findViewHolderForAdapterPosition(pos)?.itemView?.findViewById(R.id.iv_girl) as ImageView
            ImageActivity.startActivity(context, imageView, mList[pos].url)
        }
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    override fun setData(results: List<FuckGoods>) {
        mList.addAll(results)
        mAdapter.notifyDataSetChanged()
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        val GIRL = "GIRL"
        fun newInstance(): GirlFragment {
            val fragment = GirlFragment()
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