package com.weiren.zhang.movie_kotlin.adapter.myfavourite

import android.app.Activity
import androidx.appcompat.app.AlertDialog
import androidx.databinding.DataBindingUtil
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.MyfavouriteMovieListItemBinding
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import com.weiren.zhang.movie_kotlin.module.MovieManager

class MovieMyFavouriteAdapter(private val mActivity: Activity, listener: EventListener) :
    BaseQuickAdapter<MovieListModel, BaseViewHolder>(R.layout.myfavourite_movie_list_item),
    LoadMoreModule {

    var listener: EventListener? = null

    interface EventListener {
        fun onEvent()
    }

    init {
        addChildClickViewIds(R.id.delete)
        setOnItemChildClickListener { adapter, view, position ->
            val itemData = data[position]
            if (view.id == R.id.delete) {
                AlertDialog.Builder(mActivity)
                    .setTitle("提示")
                    .setMessage("您確定要刪除 " + itemData.title + " 嗎?")
                    .setPositiveButton("删除") { p0, p1 ->
                        MovieManager.removeMovieRecord(itemData)
                        data.removeAt(position)
                        notifyItemRemoved(position)
                        if (data.size == 0) {
                            listener.onEvent();
                        }
                    }
                    .setNeutralButton(
                        "取消"
                    ) { p0, p1 -> }
                    .show()

            }
        }
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
        DataBindingUtil.bind<MyfavouriteMovieListItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: MovieListModel) {
        val binding = DataBindingUtil.getBinding<MyfavouriteMovieListItemBinding>(holder.itemView)
        binding?.model = item
    }
}