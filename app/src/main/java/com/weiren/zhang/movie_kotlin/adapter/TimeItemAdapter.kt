package com.weiren.zhang.movie_kotlin.adapter

import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.TimeItemBinding
import com.weiren.zhang.movie_kotlin.databinding.VideoItemBinding
import com.weiren.zhang.movie_kotlin.model.TimeModel

class TimeItemAdapter() :
    BaseQuickAdapter<TimeModel, BaseViewHolder>(R.layout.time_item), LoadMoreModule {

    init {

    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<VideoItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TimeModel) {
        val binding = DataBindingUtil.getBinding<TimeItemBinding>(holder.itemView)
        binding?.model = item
    }
}