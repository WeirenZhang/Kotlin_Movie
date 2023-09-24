package com.weiren.zhang.movie_kotlin.adapter.movieinfomain

import android.app.Activity
import androidx.databinding.DataBindingUtil
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.MovieInfoItemBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.MovieInfoModel

class MovieInfoAdapter(private val mActivity: Activity) :
    BaseQuickAdapter<MovieInfoModel, BaseViewHolder>(R.layout.movie_info_item), LoadMoreModule {

    init {
        /*
        addChildClickViewIds(R.id.iv_cover, R.id.tv_share)
        setOnItemChildClickListener { adapter, view, position ->
            val itemData = data[position].data
            when (view.id) {
                R.id.iv_cover -> {
                    go2VideoPlayerActivity(
                        mActivity,
                        view,
                        itemData
                    )
                }
                R.id.tv_share -> {
                    ShareUtils.shareText(mActivity, itemData.playUrl)
                }
            }
        }
        */
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<MovieInfoItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: MovieInfoModel) {
        val binding = DataBindingUtil.getBinding<MovieInfoItemBinding>(holder.itemView)
        binding?.model = item
    }
}