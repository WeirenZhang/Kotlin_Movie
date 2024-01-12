package com.weiren.zhang.movie_kotlin.adapter.movieinfomain

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.adapter.TypesItemAdapter
import com.weiren.zhang.movie_kotlin.databinding.MovieTimeResultItemBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.MovieTimeResultModel
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterInfoModel

class MovieTimeResultAdapter(private val mActivity: Activity) :
    BaseQuickAdapter<MovieTimeResultModel, BaseViewHolder>(R.layout.movie_time_result_item), LoadMoreModule {

    private val viewPool = RecycledViewPool()

    init {
        setOnItemClickListener { adapter, view, position ->
            val itemData = data[position]
            println("isNotEmpty" + itemData.id)
            val theaterInfoModel = TheaterInfoModel(
                itemData.id,
                itemData.theater,
                "",
                "",
            )
            ARouter.getInstance().build(RouterPath.Theater.PATH_TheaterResult_HOME)
                .withString(BaseConstant.Theater_ID_KEY, toJson(theaterInfoModel))
                //.withBoolean(BaseConstant.VIDEO_IS_FROM_RELATE_KEY, false)
                //.withParcelable(BaseConstant.VIDEO_IS_FROM_PLAYLIST_KEY, viewAttr)
                .navigation()
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<MovieTimeResultItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: MovieTimeResultModel) {
        val binding = DataBindingUtil.getBinding<MovieTimeResultItemBinding>(holder.itemView)
        binding?.model = item
        // Create layout manager with initial prefetch item count

        // Create layout manager with initial prefetch item count

        val layoutManager = LinearLayoutManager(
            binding?.types?.getContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        layoutManager.setInitialPrefetchItemCount(item.types.size)

        // Create sub item view adapter

        // Create sub item view adapter

        val typesItemAdapter = TypesItemAdapter(3)
        typesItemAdapter.setList(item.types)

        if (binding != null) {
            binding.types.setLayoutManager(layoutManager)
            binding.types.setAdapter(typesItemAdapter)
            binding.types.setRecycledViewPool(viewPool)
        }
    }
}