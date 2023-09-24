package com.weiren.zhang.movie_kotlin.adapter.movieinfomain

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
import com.weiren.zhang.movie_kotlin.databinding.VideoItemBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.VideoModel

class VideoAdapter(private val mActivity: Activity) :
    BaseQuickAdapter<VideoModel, BaseViewHolder>(R.layout.video_item), LoadMoreModule {

    init {
        setOnItemClickListener { adapter, view, position ->
            val itemData = data[position]
            println(itemData.title)
            ARouter.getInstance().build(RouterPath.WebView.PATH_WebView_HOME)
                .withString(BaseConstant.Video_ID_KEY, toJson(itemData))
                //.withBoolean(BaseConstant.VIDEO_IS_FROM_RELATE_KEY, false)
                //.withParcelable(BaseConstant.VIDEO_IS_FROM_PLAYLIST_KEY, viewAttr)
                .navigation()
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<VideoItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: VideoModel) {
        val binding = DataBindingUtil.getBinding<VideoItemBinding>(holder.itemView)
        binding?.model = item
    }
}