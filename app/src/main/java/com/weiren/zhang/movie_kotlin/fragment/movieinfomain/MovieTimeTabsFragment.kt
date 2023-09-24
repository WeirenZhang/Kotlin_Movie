package com.weiren.zhang.movie_kotlin.fragment.movieinfomain

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
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
import com.weiren.zhang.movie_kotlin.model.movieinfomain.MovieTimeTabItemModel
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

    var pickerDialog: OnDateSetListener? = null
    var calendar = Calendar.getInstance() //用來做date

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

        binding.date.setOnClickListener {
            //建立date的dialog
            val dialog = DatePickerDialog(
                mActivity,
                pickerDialog,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            val c = Calendar.getInstance()
            dialog.datePicker.minDate = c.timeInMillis
            c.add(Calendar.DAY_OF_MONTH, 9)
            dialog.datePicker.maxDate = c.timeInMillis
            dialog.show()
        }

        pickerDialog =
            OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                calendar.set(Calendar.YEAR, year)
                calendar.set(Calendar.MONTH, monthOfYear)
                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                val myFormat = "yyyy-MM-dd" // mention the format you need
                val sdf = SimpleDateFormat(myFormat, Locale.TAIWAN)

                println(sdf.format(calendar.time))
                binding.date.text = sdf.format(calendar.time)

                mViewModel.getMovieTimeResult(movieListModel.movie_id, sdf.format(calendar.time))
                    .observerKt {
                        if (it.isNotEmpty()) {
                            initTabInfo(it)
                            binding.mEmptyLL.isVisible = false
                        } else {
                            initTabInfo(it)
                            binding.mEmptyLL.isVisible = true
                        }
                    }
            }
    }

    override fun lazyLoadData() {
        val date = Date();
        val f1 = SimpleDateFormat("yyyy-MM-dd")
        val s2 = f1.format(date)
        binding.date.text = s2
        mViewModel.getMovieTimeResult(movieListModel.movie_id, s2).observerKt {
            if (it.isNotEmpty()) {
                initTabInfo(it)
                binding.mEmptyLL.isVisible = false
            } else {
                initTabInfo(it)
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

    private fun initTabInfo(tabInfo: List<MovieTimeTabItemModel>) {
        val fragmentList = mutableListOf<Fragment>()
        tabInfo.forEach { data ->
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
            tab.text = tabInfo[position].area
        }.attach()
    }

}