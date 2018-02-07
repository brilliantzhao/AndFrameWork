package com.brilliantzhao.andframework.contract

import com.brilliantzhao.baselibrary.base.JsonResult
import com.brilliantzhao.baselibrary.examplebean.FuckGoods
import rx.Observable

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
interface RandomContract{

    interface View{

        fun onRandom(goods: FuckGoods)

    }
    interface Model{

        fun getRandom(type: String): Observable<JsonResult<List<FuckGoods>>>
    }
    interface Presenter{

        fun getRandom(type: String)
    }

}