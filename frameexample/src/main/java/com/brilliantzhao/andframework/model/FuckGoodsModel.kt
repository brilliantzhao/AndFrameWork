package com.brilliantzhao.andframework.model

import com.brilliantzhao.andframework.contract.FuckGoodsContract
import com.brilliantzhao.andframework.fragment.AndroidFragment
import com.brilliantzhao.andframework.fragment.GirlFragment
import com.brilliantzhao.andframework.fragment.IOSFragment
import com.brilliantzhao.baselibrary.api.GankApi
import com.brilliantzhao.baselibrary.base.JsonResult
import com.brilliantzhao.baselibrary.projectbean.FuckGoods
import rx.Observable
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class FuckGoodsModel
@Inject constructor(private val api: GankApi) : FuckGoodsContract.Model {

    override fun getData(page: Int, type: String): Observable<JsonResult<List<FuckGoods>>> {
        when (type) {
            AndroidFragment.ANDROID -> return api.getAndroidData(page)
            IOSFragment.IOS -> return api.getiOSData(page)
            GirlFragment.GIRL -> return api.getGirlData(page)
            else -> return api.getAndroidData(page)
        }
    }
}
