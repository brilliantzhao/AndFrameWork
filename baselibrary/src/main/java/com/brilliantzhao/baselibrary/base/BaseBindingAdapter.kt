package com.brilliantzhao.baselibrary.adapter

import android.databinding.ViewDataBinding
import android.support.v7.widget.RecyclerView

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
abstract class BaseBindingAdapter<B : ViewDataBinding> : RecyclerView.Adapter<DataBoundViewHolder<B>>() {
    var mListener: ((pos: Int) -> Unit)? = null

    override fun onBindViewHolder(holder: DataBoundViewHolder<B>, position: Int) {
        holder.binding.root.setOnClickListener {
            mListener?.invoke(holder.adapterPosition)
        }
    }

    fun setOnItemClickListener(listener: ((pos: Int) -> Unit)) {
        mListener = listener
    }

}