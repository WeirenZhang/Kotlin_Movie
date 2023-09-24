package com.weiren.zhang.movie_kotlin.adapter.movieinfomain

import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.adapter.TimeItemAdapter
import com.weiren.zhang.movie_kotlin.adapter.TypeItemAdapter
import com.weiren.zhang.movie_kotlin.databinding.TypesItemBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.TypesModel

class TypesItemAdapter() :
    BaseQuickAdapter<TypesModel, BaseViewHolder>(R.layout.types_item), LoadMoreModule {

    private val typesviewPool = RecycledViewPool()
    private val timesviewPool = RecycledViewPool()

    init {

    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<TypesItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TypesModel) {
        val binding = DataBindingUtil.getBinding<TypesItemBinding>(holder.itemView)
        binding?.model = item

        // Create layout manager with initial prefetch item count
        val TypeslayoutManager = GridLayoutManager(
            binding?.types?.getContext(),
            3
        )

        val TimeslayoutManager = GridLayoutManager(
            binding?.times?.getContext(),
            3
        )

        TypeslayoutManager.initialPrefetchItemCount = item.types.size
        TimeslayoutManager.initialPrefetchItemCount = item.times.size

        val typeItemAdapter = TypeItemAdapter()
        typeItemAdapter.setList(item.types)
        val timeItemAdapter = TimeItemAdapter()
        timeItemAdapter.setList(item.times)

        if (binding != null) {
            binding.types.setLayoutManager(TypeslayoutManager)
            binding.types.setAdapter(typeItemAdapter)
            //binding.types.setRecycledViewPool(typesviewPool)
        }

        if (binding != null) {
            binding.times.setLayoutManager(TimeslayoutManager)
            binding.times.setAdapter(timeItemAdapter)
            //binding.times.setRecycledViewPool(timesviewPool)
        }
    }
}