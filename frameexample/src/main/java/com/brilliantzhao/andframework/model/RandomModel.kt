package com.brilliantzhao.andframework.model

import com.brilliantzhao.andframework.contract.RandomContract
import com.brilliantzhao.baselibrary.api.ExampleApi
import com.brilliantzhao.baselibrary.base.JsonResult
import com.brilliantzhao.baselibrary.examplebean.FuckGoods
import rx.Observable
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class RandomModel
@Inject constructor(private val api: ExampleApi) : RandomContract.Model {

    override fun getRandom(type: String): Observable<JsonResult<List<FuckGoods>>> {
        return api.getRandom(type)
    }
}
