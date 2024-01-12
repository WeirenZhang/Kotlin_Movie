package com.weiren.zhang.movie_kotlin.adapter.movielist

import android.app.Activity
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.MovieListItemBinding
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel

class MovieListAdapter(private val mActivity: Activity) :
    BaseQuickAdapter<MovieListModel, BaseViewHolder>(R.layout.movie_list_item), LoadMoreModule {

    init {
        setOnItemClickListener { adapter, view, position ->
            val itemData = data[position]
            println(itemData.title)
            ARouter.getInstance().build(RouterPath.MovieInfoMain.PATH_MovieInfoMain_HOME)
                .withString(BaseConstant.Movie_ID_KEY, toJson(itemData))
                //.withBoolean(BaseConstant.VIDEO_IS_FROM_RELATE_KEY, false)
                //.withParcelable(BaseConstant.VIDEO_IS_FROM_PLAYLIST_KEY, viewAttr)
                .navigation()
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<MovieListItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: MovieListModel) {
        val binding = DataBindingUtil.getBinding<MovieListItemBinding>(holder.itemView)
        binding?.model = item
    }
}