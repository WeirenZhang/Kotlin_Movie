package com.weiren.zhang.movie_kotlin.activity.movielist;

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.adapter.movielist.MovieListAdapter
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.viewmodel.movielist.MovieComingsoonListViewModel

@AndroidEntryPoint
@Route(path = RouterPath.MovieList.PATH_ComIngSoon_HOME)
class MovieComingsoonListActivity :
    BaseBindVMActivity<MovieComingsoonListViewModel, RecyclerviewBinding>() {

    private val mAdapter by lazy { MovieListAdapter(this) }

    override fun getViewBinding() = RecyclerviewBinding.inflate(layoutInflater)

    override fun initWindow() {

    }

    override fun initView() {
        actionbar!!.title = "即將上映"
        mBind.mRecyclerView.layoutManager = LinearLayoutManager(this)
        mBind.mSwipeRefreshLayout.setOnRefreshListener {
            lazyLoadData()
        }
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            getMovieList()
        }
        mBind.mRecyclerView.adapter = mAdapter
        mBind.mSwipeRefreshLayout.isRefreshing = true
    }

    override fun initData() {
        //getMovieList()
        lazyLoadData()
    }

    private fun lazyLoadData() {
        mViewModel.getMovieList(true).observerKt {
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
        mViewModel.getMovieList(false).observerKt {
            println("MovieComingsoonListActivity size:" + it.size)
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
