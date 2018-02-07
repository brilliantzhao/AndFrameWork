package com.brilliantzhao.andframework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.brilliantzhao.andframework.databinding.ItemFuckgoodsBinding
import com.brilliantzhao.baselibrary.adapter.BaseBindingAdapter
import com.brilliantzhao.baselibrary.adapter.DataBoundViewHolder
import com.brilliantzhao.baselibrary.examplebean.FuckGoods

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class FuckGoodsAdapter(private val mList: List<FuckGoods>) : BaseBindingAdapter<ItemFuckgoodsBinding>() {

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemFuckgoodsBinding>, position: Int) {
        super.onBindViewHolder(holder, position)

        holder.binding.fuckgoods = mList[position]
        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemFuckgoodsBinding> {
        return DataBoundViewHolder(ItemFuckgoodsBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }
}