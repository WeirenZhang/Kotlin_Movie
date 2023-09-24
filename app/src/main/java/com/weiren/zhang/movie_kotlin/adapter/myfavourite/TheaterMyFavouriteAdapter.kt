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
import com.weiren.zhang.movie_kotlin.databinding.MyfavouriteTheaterListItemBinding
import com.weiren.zhang.movie_kotlin.fragment.myfavourite.TheaterMyFavouriteFragment
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterInfoModel
import com.weiren.zhang.movie_kotlin.module.TheaterManager

class TheaterMyFavouriteAdapter(
    private val mActivity: Activity,
    listener: TheaterMyFavouriteFragment
) :
    BaseQuickAdapter<TheaterInfoModel, BaseViewHolder>(R.layout.myfavourite_theater_list_item),
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
                    .setMessage("您確定要刪除 " + itemData.name + " 嗎?")
                    .setPositiveButton("删除") { p0, p1 ->
                        TheaterManager.removeTheaterRecord(itemData)
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
            println(itemData.name)
            val movieListModel = TheaterInfoModel(
                itemData.id,
                itemData.name,
                itemData.adds,
                itemData.tel
            )
            ARouter.getInstance().build(RouterPath.Theater.PATH_TheaterResult_HOME)
                .withString(BaseConstant.Theater_ID_KEY, toJson(movieListModel))
                //.withBoolean(BaseConstant.VIDEO_IS_FROM_RELATE_KEY, false)
                //.withParcelable(BaseConstant.VIDEO_IS_FROM_PLAYLIST_KEY, viewAttr)
                .navigation()
        }
    }

    override fun onItemViewHolderCreated(viewHolder: BaseViewHolder, viewType: Int) {
        DataBindingUtil.bind<MyfavouriteTheaterListItemBinding>(viewHolder.itemView)
    }

    override fun convert(holder: BaseViewHolder, item: TheaterInfoModel) {
        val binding = DataBindingUtil.getBinding<MyfavouriteTheaterListItemBinding>(holder.itemView)
        binding?.model = item
    }
}