package com.weiren.zhang.movie_kotlin.fragment.movieinfomain

import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.android.tu.loadingdialog.LoadingDailog
import com.google.android.material.tabs.TabLayoutMediator
import com.weiren.zhang.lib_common.base.fragment.BaseBindVMFragment
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.MovieTimeTabsFragmentBinding
import com.weiren.zhang.movie_kotlin.model.movieinfomain.MovieDateTabItemModel
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain.MovieTimeResultViewModel
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.util.*


@AndroidEntryPoint
@Route(path = RouterPath.MovieInfoMain.PATH_MovieTimeTabs_HOME)
class MovieTimeTabsFragment :
    BaseBindVMFragment<MovieTimeResultViewModel, MovieTimeTabsFragmentBinding>() {

    @JvmField
    @Autowired(name = BaseConstant.Movie_ID_KEY)
    var movieModelJSON: String = ""

    private val mLoadingDialog: LoadingDailog by lazy {
        LoadingDailog.Builder(mActivity)
            .setMessage(getString(R.string.daily_loading_text))
            .create()
    }

    private lateinit var movieListModel: MovieListModel

    override val bindingInflater: (LayoutInflater, ViewGroup?, Bundle?) -> MovieTimeTabsFragmentBinding
        get() = { layoutInflater, viewGroup, _ ->
            MovieTimeTabsFragmentBinding.inflate(layoutInflater, viewGroup, false)
        }

    override fun initView() {
        ARouter.getInstance().inject(this)
        movieListModel = fromJson(movieModelJSON)
    }

    override fun lazyLoadData() {

        mViewModel.getMovieDateResult(movieListModel.id).observerKt {
            if (it.isNotEmpty()) {
                initTabInfo(it, 0)

                // setup the alert builder
                val builder = AlertDialog.Builder(mActivity)
                builder.setTitle("選擇日期")

                // add a list
                val list = it.mapTo(arrayListOf()) { it.date }
                val cs: Array<CharSequence> = list.toArray(arrayOfNulls<CharSequence>(list.size))
                builder.setItems(cs) { dialog, which ->
                    initTabInfo(it, which)
                }

                // create and show the alert dialog
                val dialog = builder.create()
                binding.date.setOnClickListener {
                    dialog.show()
                }

                binding.mEmptyLL.isVisible = false
            } else {
                binding.mEmptyLL.isVisible = true
            }
        }
    }

    override fun showLoading() {
        mLoadingDialog.show()
    }

    override fun hideLoading() {
        mLoadingDialog.dismiss()
    }

    private fun initTabInfo(tabInfo: List<MovieDateTabItemModel>, which: Int) {
        val fragmentList = mutableListOf<Fragment>()
        binding.date.text = tabInfo[which].date

        tabInfo[which].list.forEach { data ->
            binding.mTabLayout.addTab(binding.mTabLayout.newTab())
            fragmentList.add(MovieTimeResultFragment.newInstance(toJson(data)))
        }
        binding.mViewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int = fragmentList.size

            override fun createFragment(position: Int): Fragment = fragmentList[position]
        }
        TabLayoutMediator(
            binding.mTabLayout, binding.mViewPager
        ) { tab, position ->
            tab.text = tabInfo[which].list[position].area
        }.attach()
    }
}