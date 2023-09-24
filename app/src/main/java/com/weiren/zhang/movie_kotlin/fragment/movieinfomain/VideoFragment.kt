package com.weiren.zhang.movie_kotlin.fragment.movieinfomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.fragment.BaseBindVMFragment
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.adapter.movieinfomain.VideoAdapter
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain.VideoViewModel

@AndroidEntryPoint
@Route(path = RouterPath.MovieInfoMain.PATH_Video_HOME)
class VideoFragment : BaseBindVMFragment<VideoViewModel, RecyclerviewBinding>() {

    @JvmField
    @Autowired(name = BaseConstant.Movie_ID_KEY)
    var movieModelJSON: String = ""

    private lateinit var movieListModel: MovieListModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Bundle?) -> RecyclerviewBinding
        get() = { layoutInflater, viewGroup, _ ->
            RecyclerviewBinding.inflate(layoutInflater, viewGroup, false)
        }

    private val mAdapter: VideoAdapter by lazy { VideoAdapter(mActivity) }

    override fun initView() {
        ARouter.getInstance().inject(this)
        movieListModel = fromJson(movieModelJSON)

        binding.mSwipeRefreshLayout.isRefreshing = true
        binding.mSwipeRefreshLayout.setOnRefreshListener {
            lazyLoadData()
        }
        binding.mRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.mRecyclerView.adapter = mAdapter
    }

    override fun hideLoading() {
        binding.mSwipeRefreshLayout.isRefreshing = false
    }

    override fun lazyLoadData() {
        mViewModel.getVideo(movieListModel.movie_id).observerKt {
            if (it.isNotEmpty()) {
                mAdapter.setList(mutableListOf())
                mAdapter.addData(it)
                binding.mEmptyLL.isVisible = false
            } else {
                binding.mEmptyLL.isVisible = true
            }
        }
    }

    override fun handlerError() {
        mAdapter.loadMoreModule.loadMoreFail()
    }
}