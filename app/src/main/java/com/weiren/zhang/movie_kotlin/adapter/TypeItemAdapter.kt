package com.weiren.zhang.movie_kotlin.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.TypeItemBinding
import com.weiren.zhang.movie_kotlin.databinding.VideoItemBinding
import com.weiren.zhang.movie_kotlin.model.TypeModel

class TypeItemAdapter() :
    BaseQuickAdapter<TypeModel, BaseViewHolder>(R.layout.type_item), LoadMoreModule {

    init {

    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<VideoItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TypeModel) {
        val binding = DataBindingUtil.getBinding<TypeItemBinding>(holder.itemView)
        binding?.model = item
    }
}