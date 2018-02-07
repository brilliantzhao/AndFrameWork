package com.brilliantzhao.andframework.contract

import com.brilliantzhao.baselibrary.base.JsonResult
import com.brilliantzhao.baselibrary.examplebean.FuckGoods
import rx.Observable

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
interface FuckGoodsContract {

    interface View {

        fun  setData(results: List<FuckGoods>)

    }

    interface Model {

        fun getData(page: Int,type:String): Observable<JsonResult<List<FuckGoods>>>
    }

    interface Presenter {

        open fun getData(page: Int, type: String)
    }
}