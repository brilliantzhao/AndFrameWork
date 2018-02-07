package com.brilliantzhao.andframework.activity

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Build
import android.os.Bundle
import android.widget.ImageView
import com.brilliantzhao.andframework.R
import com.brilliantzhao.andframework.databinding.ActivityImageBinding
import com.brilliantzhao.baselibrary.base.BaseBindingActivity

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class ImageActivity : BaseBindingActivity<ActivityImageBinding>() {

    override fun getContentViewId(): Int {
        return R.layout.activity_image
    }

    override fun createDataBinding(savedInstanceState: Bundle?): ActivityImageBinding {
        return DataBindingUtil.setContentView(this, getContentViewId())
    }

    override fun initView() {
        mBinding.url = intent.getStringExtra(IMG)
        mBinding.root.setOnClickListener {

            supportFinishAfterTransition()
        }
    }

    override fun initEvent() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initData() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object {
        val IMG = "IMG"
        fun startActivity(context: Context, imageView: ImageView, url: String) {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(IMG, url)
            if (Build.VERSION.SDK_INT > 21) {
                context.startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(context as Activity, imageView, "img").toBundle())
            } else {
                context.startActivity(intent)
            }
        }
    }
}
