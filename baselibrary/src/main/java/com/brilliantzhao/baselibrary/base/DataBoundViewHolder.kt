package com.brilliantzhao.baselibrary.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class DataBoundViewHolder<T : ViewDataBinding>(val binding: T) : RecyclerView.ViewHolder(binding.root)