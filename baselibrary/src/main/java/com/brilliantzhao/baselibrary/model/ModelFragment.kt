package com.brilliantzhao.baselibrary.model

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.brilliantzhao.baselibrary.base.BaseBingingFragment
import com.brilliantzhao.baselibrary.databinding.FragmentModelBinding

/**
 * description:
 * Date: 2018/2/7 14:17
 * User: BrilliantZhao
 */
class ModelFragment : BaseBingingFragment<FragmentModelBinding>() {

    //##########################  custom variables start ##########################################

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?,
                                   savedInstanceState: Bundle?): FragmentModelBinding {
        return FragmentModelBinding.inflate(inflater!!, container, false)
    }

    override fun initView() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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