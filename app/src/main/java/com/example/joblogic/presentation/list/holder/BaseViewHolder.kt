package com.example.joblogic.presentation.list.holder

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.example.joblogic.presentation.list.ItemData

abstract class BaseViewHolder(viewDataBinding: ViewDataBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
    abstract fun bind(listData: ItemData)
}