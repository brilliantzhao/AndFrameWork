package com.brilliantzhao.andframework.component

import com.brilliantzhao.andframework.contract.FuckGoodsContract
import com.brilliantzhao.andframework.fragment.AndroidFragment
import com.brilliantzhao.andframework.fragment.GirlFragment
import com.brilliantzhao.andframework.fragment.IOSFragment
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@Subcomponent(modules = arrayOf(FuckGoodsModule::class))
interface FuckGoodsComponent {

    fun inject(fragment: AndroidFragment)

    fun inject(fragment: IOSFragment)

    fun inject(fragment: GirlFragment)
}

@Module
class FuckGoodsModule(private val mView: FuckGoodsContract.View) {
    @Provides
    fun getView() = mView
}