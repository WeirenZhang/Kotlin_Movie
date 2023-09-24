package com.weiren.zhang.movie_kotlin.activity.theaterlist;

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.adapter.theaterlist.TheaterAreaAdapter
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.viewmodel.theaterlist.TheaterAreaViewModel

@AndroidEntryPoint
@Route(path = RouterPath.Theater.PATH_Area_HOME)
class TheaterAreaActivity : BaseBindVMActivity<TheaterAreaViewModel, RecyclerviewBinding>() {

    private val mAdapter by lazy { TheaterAreaAdapter(this) }

    override fun getViewBinding() = RecyclerviewBinding.inflate(layoutInflater)

    override fun initWindow() {

    }

    override fun initView() {
        actionbar!!.title = "地區"
        mBind.mRecyclerView.layoutManager = LinearLayoutManager(this)
        mBind.mSwipeRefreshLayout.setOnRefreshListener {
            lazyLoadData()
        }
        mBind.mRecyclerView.adapter = mAdapter
        mBind.mSwipeRefreshLayout.isRefreshing = true
    }

    override fun initData() {
        getMovieList()
    }

    private fun lazyLoadData() {
        mViewModel.getAreaList().observerKt {
            if (it.isNotEmpty()) {
                mAdapter.setList(mutableListOf())
                mAdapter.addData(it)
                mBind.mEmptyLL.isVisible = false
            } else {
                mBind.mEmptyLL.isVisible = true
            }
        }
    }

    override fun hideLoading() {
        mBind.mSwipeRefreshLayout.isRefreshing = false
    }

    private fun getMovieList() {
        mViewModel.getAreaList().observerKt {
            println("size:" + it.size)
            if (it.isNotEmpty()) {
                println("isNotEmpty")
                mAdapter.loadMoreModule.loadMoreComplete()
                mAdapter.addData(it)
            } else {
                println("Empty")
                mAdapter.loadMoreModule.loadMoreEnd()
            }
        }
    }

    override fun handlerError() {
        mAdapter.loadMoreModule.loadMoreFail()
    }

    override fun initEvent() {

    }
}
