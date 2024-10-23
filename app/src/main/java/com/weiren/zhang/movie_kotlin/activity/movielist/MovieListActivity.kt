package com.weiren.zhang.movie_kotlin.activity.movielist;

import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import com.weiren.zhang.lib_common.constant.BaseConstant
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.adapter.movielist.MovieListAdapter
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.viewmodel.movielist.MovieListViewModel

@AndroidEntryPoint
@Route(path = RouterPath.MovieList.PATH_MovieList_HOME)
class MovieListActivity :
    BaseBindVMActivity<MovieListViewModel, RecyclerviewBinding>() {

    private val mAdapter by lazy { MovieListAdapter(this) }

    private val Text = arrayOf("本周新片", "本期首輪","本期二輪", "近期上映", "新片快報")

    @JvmField
    @Autowired(name = BaseConstant.Home_ID_KEY)
    var Home_ID: String = ""

    override fun getViewBinding() = RecyclerviewBinding.inflate(layoutInflater)

    override fun initWindow() {

    }

    override fun initView() {
        ARouter.getInstance().inject(this)
        actionbar!!.title = Text[Home_ID.toInt()]
        mBind.mRecyclerView.layoutManager = LinearLayoutManager(this)
        mBind.mSwipeRefreshLayout.setOnRefreshListener {
            lazyLoadData(Home_ID)
        }
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        mAdapter.loadMoreModule.setOnLoadMoreListener {
            getMovieList(Home_ID)
        }
        mBind.mRecyclerView.adapter = mAdapter
        mBind.mSwipeRefreshLayout.isRefreshing = true
    }

    override fun initData() {
        //getMovieList()
        lazyLoadData(Home_ID)
    }

    private fun lazyLoadData(Home_ID: String) {
        mViewModel.getMovieList(true, Home_ID).observerKt {
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

    private fun getMovieList(Home_ID: String) {
        mViewModel.getMovieList(false, Home_ID).observerKt {
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
