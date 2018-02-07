package com.brilliantzhao.andframework.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import com.brilliantzhao.andframework.databinding.FragmentAboutBinding
import com.brilliantzhao.baselibrary.base.BaseBingingFragment

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class FragmentHolder : BaseBingingFragment<FragmentAboutBinding>() {

    //##########################  custom variables start ##########################################

    //##########################   custom variables end  ##########################################

    //###################### override custom metohds start ########################################

    override fun createDataBinding(inflater: LayoutInflater?, container: ViewGroup?,
                                   savedInstanceState: Bundle?): FragmentAboutBinding {
        return FragmentAboutBinding.inflate(inflater!!, container, false)
    }

    override fun initView() {
        val text = "Kotlin \n"
                .plus("Dagger 2\n")
                .plus("Rxjava\n")
                .plus("Retrofit 2\n")
                .plus("OkHttp 3\n")
                .plus("DataBinding\n")
                .plus("DeepLinkDispatch\n")
                .plus("Gson\n")
                .plus("Glide")
        mBinding?.tvThank?.text = text
    }

    override fun initEvent() {
    }

    override fun initData() {
    }

    //######################  override custom metohds end  ########################################

    //######################      custom metohds start     ########################################

    //######################    custom metohds end   ##############################################

    //######################  override third methods start ########################################

    //######################   override third methods end  ########################################

}