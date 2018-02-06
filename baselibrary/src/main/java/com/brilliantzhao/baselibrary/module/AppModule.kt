package com.brilliantzhao.baselibrary.module

import android.content.Context
import dagger.Module
import dagger.Provides

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@Module
class AppModule(private val context: Context) {
    @Provides
    fun provideContext() = context
}
