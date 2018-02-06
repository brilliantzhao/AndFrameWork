package com.brilliantzhao.andframework.presenter

import com.brilliantzhao.andframework.contract.RandomContract
import com.brilliantzhao.andframework.model.RandomModel
import rx.android.schedulers.AndroidSchedulers
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class RandomPresenter
@Inject constructor(private val mModel: RandomModel,
                    private val mView: RandomContract.View) : RandomContract.Presenter, BasePresenter() {
    override fun getRandom(type: String) {
        addSubscription(mModel.getRandom(type).observeOn(AndroidSchedulers.mainThread())
                .subscribe({ res ->
                    if (!res.error) {
                        mView.onRandom(res.results[0])
                    }
                }, {}))
    }
}