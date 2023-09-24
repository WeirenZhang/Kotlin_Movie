package com.weiren.zhang.movie_kotlin.fragment.movieinfomain

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.weiren.zhang.lib_common.base.fragment.BaseBindFragment
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.adapter.movieinfomain.MovieTimeResultAdapter
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.MovieTimeTabItemModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = RouterPath.MovieInfoMain.PATH_MovieTimeTabs_HOME)
class MovieTimeResultFragment : BaseBindFragment<RecyclerviewBinding>() {

    private val mAdapter: MovieTimeResultAdapter by lazy { MovieTimeResultAdapter(mActivity) }

    private lateinit var movieTimeTabItemModel: MovieTimeTabItemModel

    companion object {
        const val Movie_Time_Result_KEY = "movie_time_result_key"
        fun newInstance(Movie_Time_Result: String): MovieTimeResultFragment {
            val fragment = MovieTimeResultFragment()
            val arguments = Bundle()
            arguments.putString(Movie_Time_Result_KEY, Movie_Time_Result)
            fragment.arguments = arguments
            return fragment
        }
    }

    override val bindingInflater: (LayoutInflater, ViewGroup?, Bundle?) -> RecyclerviewBinding
        get() = { layoutInflater, viewGroup, _ ->
            RecyclerviewBinding.inflate(layoutInflater, viewGroup, false)
        }

    override fun initView() {
        binding.mRecyclerView.layoutManager = LinearLayoutManager(context)
        mAdapter.loadMoreModule.isEnableLoadMoreIfNotFullPage = false
        binding.mSwipeRefreshLayout.isRefreshing = false;
        binding.mSwipeRefreshLayout.isEnabled = false;
        binding.mRecyclerView.adapter = mAdapter
    }

    override fun initData() {
        mAdapter.setList(mutableListOf())
        arguments?.getString(Movie_Time_Result_KEY)?.let { Movie_Time_Result ->
            movieTimeTabItemModel = fromJson(Movie_Time_Result)
            mAdapter.setList(movieTimeTabItemModel.data)
        }
    }

}