package com.example.joblogic.presentation.list

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.joblogic.databinding.ItemContactBinding
import com.example.joblogic.databinding.ItemProductBinding
import com.example.joblogic.presentation.list.holder.BaseViewHolder
import com.example.joblogic.presentation.list.holder.ContactViewHolder
import com.example.joblogic.presentation.list.holder.ProductViewHolder

class BaseAdapter: RecyclerView.Adapter<BaseViewHolder>() {

    private val items = mutableListOf<ItemData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val viewDataBinding = DataBindingUtil.inflate<ViewDataBinding>(inflater,viewType,parent,false)

        return LayoutInflater.from(parent.context).inflate(viewType, parent, false).run {
            when (viewType) {
                ItemType.TYPE_CONTACT -> ContactViewHolder(viewDataBinding as ItemContactBinding)
                ItemType.TYPE_PRODUCT -> ProductViewHolder(viewDataBinding as ItemProductBinding)
                else -> throw UnsupportedOperationException("$viewType was not supported")
            }
        }
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]) {
            is ItemData.ContactItem -> ItemType.TYPE_CONTACT
            is ItemData.ProductItem -> ItemType.TYPE_PRODUCT
        }
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun updateData(data:List<ItemData>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

}