package com.brilliantzhao.andframework.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import com.brilliantzhao.andframework.databinding.ItemGirlBinding
import com.brilliantzhao.baselibrary.adapter.BaseBindingAdapter
import com.brilliantzhao.baselibrary.adapter.DataBoundViewHolder
import com.brilliantzhao.baselibrary.examplebean.FuckGoods

/**
 * description:
 * Date: 2018/2/2 10:28
 * User: BrilliantZhao
 */
class GirlAdapter(private val mList: List<FuckGoods>) : BaseBindingAdapter<ItemGirlBinding>() {

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: DataBoundViewHolder<ItemGirlBinding>, position: Int) {
        super.onBindViewHolder(holder, position)
        holder.binding.girl = mList[holder.adapterPosition]

        holder.binding.executePendingBindings()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DataBoundViewHolder<ItemGirlBinding> {
        return DataBoundViewHolder(ItemGirlBinding.inflate(LayoutInflater.from(parent.context),
                parent, false))
    }

}