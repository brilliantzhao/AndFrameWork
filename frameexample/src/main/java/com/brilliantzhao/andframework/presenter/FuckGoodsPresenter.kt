package com.brilliantzhao.andframework.presenter

import android.util.Log
import com.brilliantzhao.andframework.contract.FuckGoodsContract
import com.brilliantzhao.andframework.model.FuckGoodsModel
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class FuckGoodsPresenter
@Inject constructor(private val mModel: FuckGoodsModel,
                    private val mView: FuckGoodsContract.View)
    : FuckGoodsContract.Presenter, BasePresenter() {

    override fun getData(page: Int, type: String) {
        addSubscription(mModel.getData(page, type).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    if (!res.error) {
                        mView.setData(res.results)
                    }
                }, { e -> Log.e("wing", "error android Presenter" + e.message) }))
    }

}