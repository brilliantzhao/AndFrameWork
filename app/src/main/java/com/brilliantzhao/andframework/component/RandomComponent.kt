package com.brilliantzhao.andframework.component

import com.brilliantzhao.andframework.activity.MainActivity
import com.brilliantzhao.andframework.contract.RandomContract
import dagger.Module
import dagger.Provides
import dagger.Subcomponent

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@Subcomponent(modules = arrayOf(RandomModule::class))
interface RandomComponent {
    fun inject(activity: MainActivity)
}

@Module
class RandomModule(private val mView: RandomContract.View) {
    @Provides
    fun getView() = mView
}