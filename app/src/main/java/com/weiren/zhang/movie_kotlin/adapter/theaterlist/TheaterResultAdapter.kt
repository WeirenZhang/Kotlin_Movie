package com.weiren.zhang.movie_kotlin.adapter.theaterlist

import android.app.Activity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.adapter.TypesItemAdapter

import com.weiren.zhang.movie_kotlin.databinding.TheaterResultItemBinding
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterResultModel

class TheaterResultAdapter(private val mActivity: Activity) :
    BaseQuickAdapter<TheaterResultModel, BaseViewHolder>(R.layout.theater_result_item), LoadMoreModule {

    private val viewPool = RecyclerView.RecycledViewPool()

    init {
        setOnItemClickListener { adapter, view, position ->
            val itemData = data[position]
            println(itemData.theaterlist_name)

            val movieListModel = MovieListModel(
                itemData.theaterlist_name,
                itemData.en,
                "",
                itemData.release_foto,
                itemData.id
            )

            ARouter.getInstance().build(RouterPath.MovieInfoMain.PATH_MovieInfoMain_HOME)
                .withString(BaseConstant.Movie_ID_KEY, toJson(movieListModel))
                //.withBoolean(BaseConstant.VIDEO_IS_FROM_RELATE_KEY, false)
                //.withParcelable(BaseConstant.VIDEO_IS_FROM_PLAYLIST_KEY, viewAttr)
                .navigation()
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<TheaterResultItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TheaterResultModel) {
        val binding = DataBindingUtil.getBinding<TheaterResultItemBinding>(holder.itemView)
        binding?.model = item
        /*
        // Create layout manager with initial prefetch item count
        val TypeslayoutManager = GridLayoutManager(
            binding?.types?.getContext(),
            3
        )

        val TimeslayoutManager = GridLayoutManager(
            binding?.times?.getContext(),
            3
        )

        TypeslayoutManager.initialPrefetchItemCount = item.tapbox.size
        TimeslayoutManager.initialPrefetchItemCount = item.theater_time.size

        val typeItemAdapter = TypeItemAdapter()
        typeItemAdapter.setList(item.tapbox)
        val timeItemAdapter = TimeItemAdapter()
        timeItemAdapter.setList(item.theater_time)

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
        */
        val layoutManager = LinearLayoutManager(
            binding?.types?.getContext(),
            LinearLayoutManager.VERTICAL,
            false
        )
        layoutManager.setInitialPrefetchItemCount(item.types.size)

        // Create sub item view adapter

        // Create sub item view adapter

        val typesItemAdapter = TypesItemAdapter(2)
        typesItemAdapter.setList(item.types)

        if (binding != null) {
            binding.types.setLayoutManager(layoutManager)
            binding.types.setAdapter(typesItemAdapter)
            binding.types.setRecycledViewPool(viewPool)
        }
    }
}