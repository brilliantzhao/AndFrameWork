package com.brilliantzhao.andframework.component

import com.brilliantzhao.andframework.SampleApplicationLike
import com.brilliantzhao.baselibrary.module.ApiModule
import dagger.Component
import javax.inject.Singleton

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@Singleton
@Component(modules = arrayOf(ApiModule::class))
interface ApiComponent {

    fun inject(app: SampleApplicationLike)

    fun plus(module: FuckGoodsModule): FuckGoodsComponent

    fun plus(module: RandomModule): RandomComponent
}

