package com.brilliantzhao.andframework

import com.brilliantzhao.andframework.component.ApiComponent
import com.brilliantzhao.andframework.component.DaggerApiComponent
import com.brilliantzhao.baselibrary.base.BaseApplication
import com.brilliantzhao.baselibrary.module.ApiModule
import com.brilliantzhao.baselibrary.module.AppModule
import javax.inject.Inject

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class App : BaseApplication() {

    //##########################  custom variables start ##########################################

    init {
        instance = this
    }

    @Inject
    lateinit var apiComponent: ApiComponent

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun onCreate() {
        super.onCreate()

        // 代码中没有错误，编译成功才会生成 DaggerxxxComponent 文件,如遇到该文件没有自动生成，请先确认代码可以成功编译
        DaggerApiComponent.builder()
                .apiModule(ApiModule())
                .appModule(AppModule(this))
                .build()
                .inject(this)
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        lateinit var instance: App
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    //######################   override third methods end  ########################################

}