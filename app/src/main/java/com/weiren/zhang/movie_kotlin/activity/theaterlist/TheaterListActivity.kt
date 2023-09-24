package com.weiren.zhang.movie_kotlin.activity.theaterlist;

import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.fromJson
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.adapter.theaterlist.TheaterListAdapter
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterAreaModel
import com.weiren.zhang.movie_kotlin.viewmodel.theaterlist.TheaterListViewModel

@AndroidEntryPoint
@Route(path = RouterPath.Theater.PATH_TheaterList_HOME)
class TheaterListActivity : BaseBindVMActivity<TheaterListViewModel, RecyclerviewBinding>() {

    private val mAdapter by lazy { TheaterListAdapter(this) }

    @JvmField
    @Autowired(name = BaseConstant.Area_ID_KEY)
    var theaterAreaModelJSON: String = ""

    private lateinit var theaterAreaModel: TheaterAreaModel

    override fun getViewBinding() = RecyclerviewBinding.inflate(layoutInflater)

    override fun initWindow() {

    }

    override fun initView() {
        mBind.mRecyclerView.layoutManager = LinearLayoutManager(this)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mBind.mSwipeRefreshLayout.isRefreshing = false;
        mBind.mSwipeRefreshLayout.isEnabled = false;
        mBind.mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
        theaterAreaModel = fromJson(theaterAreaModelJSON)
        actionbar!!.title = theaterAreaModel.theater_top
        mAdapter.setList(theaterAreaModel.theater_list)
    }

    override fun hideLoading() {

    }

    override fun handlerError() {
        mAdapter.loadMoreModule.loadMoreFail()
    }

    override fun initEvent() {

    }
}
