package com.brilliantzhao.baselibrary.model

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.view.View
import com.brilliantzhao.baselibrary.R
import com.brilliantzhao.baselibrary.base.BaseBindingActivity
import com.brilliantzhao.baselibrary.databinding.ActivityModelBinding

/**
 * description:
 * Date: 2018/2/7 14:00
 * User: BrilliantZhao
 */
class ModelActivity : BaseBindingActivity<ActivityModelBinding>() {

    //##########################  custom variables start ##########################################

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun getContentViewId(): Int {
        return R.layout.activity_model
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityModelBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    override fun onClick(v: View) {
        when (v.id) {

        }
    }

    //######################   override third methods end  ########################################

}