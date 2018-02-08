package com.brilliantzhao.baselibrary.util

import android.databinding.BindingAdapter
import android.widget.ImageView
import com.bumptech.glide.Glide

/**
 * description: 数据绑定的工具类
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
@BindingAdapter("load_image")
fun loadImage(imageView: ImageView, url: String?) =
        Glide.with(imageView.context).load(url)
                .crossFade()
                .into(imageView)

@BindingAdapter("load_asset")
fun loadAsset(imageView: ImageView, id: Int) =
        Glide.with(imageView.context).load(id).into(imageView)
