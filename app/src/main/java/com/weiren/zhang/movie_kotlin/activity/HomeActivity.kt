package com.weiren.zhang.movie_kotlin.activity

import android.widget.AdapterView
import android.widget.SimpleAdapter
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindActivity
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.ActivityMainBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.VideoModel
import dagger.hilt.android.AndroidEntryPoint
import java.util.ArrayList
import java.util.HashMap

@AndroidEntryPoint
class HomeActivity : BaseBindActivity<ActivityMainBinding>() {

    override fun getViewBinding() = ActivityMainBinding.inflate(layoutInflater)

    private val image = intArrayOf(
        R.drawable.enl_2,
        R.drawable.enl_1,
        R.drawable.enl_1,
        R.drawable.enl_4,
        R.drawable.enl_4,
        R.drawable.enl_5,
        R.drawable.enl_3,
        R.drawable.enl_6
    )
    private val imgText = arrayOf("本周新片", "本期首輪","本期二輪", "近期上映", "新片快報", "電影院", "我的最愛", "網路訂票")

    override fun initView() {
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "電影時刻表"
        //set back button

        val items = ArrayList<Map<String, Any>>()
        for (i in image.indices) {
            val item = HashMap<String, Any>()
            item["image"] = image[i]
            item["text"] = imgText[i]
            items.add(item)
        }
        val adapter = SimpleAdapter(
            this,
            items, R.layout.grid_item, arrayOf("image", "text"),
            intArrayOf(R.id.image, R.id.text)
        )
        mBind.mainPageGridview.numColumns = 3
        mBind.mainPageGridview.adapter = adapter

        val videoModel = VideoModel(
            "網路訂票",
            "https://www.ezding.com.tw/faq?comeFromApp=true&device=app",
            ""
        )

        mBind.mainPageGridview.onItemClickListener =
            AdapterView.OnItemClickListener { _, _, position, _ ->
                //Toast.makeText(this@HomeActivity, "你選擇了" + imgText[position], Toast.LENGTH_SHORT).show()
                when (position) {
                    0 -> ARouter.getInstance().build(RouterPath.MovieList.PATH_MovieList_HOME)
                        .withString(BaseConstant.Home_ID_KEY, "0").navigation();
                    1 -> ARouter.getInstance().build(RouterPath.MovieList.PATH_MovieList_HOME)
                        .withString(BaseConstant.Home_ID_KEY, "1").navigation();
                    2 -> ARouter.getInstance().build(RouterPath.MovieList.PATH_MovieList_HOME)
                        .withString(BaseConstant.Home_ID_KEY, "2").navigation();
                    3 -> ARouter.getInstance().build(RouterPath.MovieList.PATH_MovieList_HOME)
                        .withString(BaseConstant.Home_ID_KEY, "3").navigation();
                    4 -> ARouter.getInstance().build(RouterPath.MovieList.PATH_MovieList_HOME)
                        .withString(BaseConstant.Home_ID_KEY, "4").navigation();
                    5 -> ARouter.getInstance().build(RouterPath.Theater.PATH_Area_HOME)
                        .navigation();
                    6 -> ARouter.getInstance().build(RouterPath.MyFavourite.PATH_MyFavourite_HOME)
                        .navigation();
                    7 -> ARouter.getInstance().build(RouterPath.WebView.PATH_WebView_HOME)
                        .withString(BaseConstant.Video_ID_KEY, toJson(videoModel))
                        .navigation();
                }
            }
    }
}