package com.brilliantzhao.frameproject

import com.brilliantzhao.baselibrary.base.BaseApplication

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class SampleApplication : BaseApplication() {

    //##########################  custom variables start ##########################################

    init {
        instance = this
    }

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun onCreate() {
        super.onCreate()

    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    companion object {
        lateinit var instance: SampleApplication
    }

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    //######################   override third methods end  ########################################

}