package com.weiren.zhang.movie_kotlin.activity.theaterlist;

import android.view.Menu
import android.view.MenuItem
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.fromJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.adapter.theaterlist.TheaterResultAdapter
import com.weiren.zhang.movie_kotlin.databinding.RecyclerviewBinding
import com.weiren.zhang.movie_kotlin.model.theaterlist.TheaterInfoModel
import com.weiren.zhang.movie_kotlin.module.TheaterManager
import com.weiren.zhang.movie_kotlin.viewmodel.theaterlist.TheaterResultViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@Route(path = RouterPath.Theater.PATH_TheaterResult_HOME)
class TheaterResultActivity : BaseBindVMActivity<TheaterResultViewModel, RecyclerviewBinding>() {

    private val mAdapter by lazy { TheaterResultAdapter(this) }

    @JvmField
    @Autowired(name = BaseConstant.Theater_ID_KEY)
    var theaterInfoModelJSON: String = ""

    private lateinit var theaterInfoModel: TheaterInfoModel

    override fun getViewBinding() = RecyclerviewBinding.inflate(layoutInflater)

    override fun initWindow() {

    }

    // method to inflate the options menu when
    // the user opens the menu for the first time
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.myfavourite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here.
        val id = item.getItemId()

        if (id == R.id.myfavourite) {
            println(theaterInfoModel.name)
            TheaterManager.addTheaterRecord(this, theaterInfoModel)
            return true
        }
        return super.onOptionsItemSelected(item)
    }


    override fun initView() {
        mBind.mRecyclerView.layoutManager = LinearLayoutManager(this)
        mBind.mSwipeRefreshLayout.setOnRefreshListener {
            lazyLoadData()
        }
        mBind.mRecyclerView.adapter = mAdapter
        mBind.mSwipeRefreshLayout.isRefreshing = true
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
        theaterInfoModel = fromJson(theaterInfoModelJSON)
        actionbar!!.title = theaterInfoModel.name
        lazyLoadData()
    }

    private fun lazyLoadData() {
        mViewModel.getTheaterResultList(theaterInfoModel.id).observerKt {
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

    override fun handlerError() {
        mAdapter.loadMoreModule.loadMoreFail()
    }

    override fun initEvent() {

    }
}
