package com.weiren.zhang.movie_kotlin.adapter.theaterlist

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
import com.weiren.zhang.movie_kotlin.databinding.AreaListItemBinding
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterAreaModel

class TheaterAreaAdapter(private val mActivity: Activity) :
    BaseQuickAdapter<TheaterAreaModel, BaseViewHolder>(R.layout.area_list_item), LoadMoreModule {

    init {
        setOnItemClickListener { adapter, view, position ->
            val itemData = data[position]
            println(itemData.theater_top)
            ARouter.getInstance().build(RouterPath.Theater.PATH_TheaterList_HOME)
                .withString(BaseConstant.Area_ID_KEY, toJson(itemData))
                //.withBoolean(BaseConstant.VIDEO_IS_FROM_RELATE_KEY, false)
                //.withParcelable(BaseConstant.VIDEO_IS_FROM_PLAYLIST_KEY, viewAttr)
                .navigation()
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<AreaListItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TheaterAreaModel) {
        val binding = DataBindingUtil.getBinding<AreaListItemBinding>(holder.itemView)
        binding?.model = item
    }
}