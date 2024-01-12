package com.weiren.zhang.movie_kotlin.activity.movieinfomain;

import android.app.SharedElementCallback
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Parcelable
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import com.weiren.zhang.lib_common.constant.BaseConstant
import com.weiren.zhang.lib_common.ext.fromJson
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.lib_common.ext.immersionStatusBar
import com.weiren.zhang.lib_common.ext.toJson
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.MovieInfoMainActivityBinding
import com.weiren.zhang.movie_kotlin.model.movielist.MovieListModel
import com.weiren.zhang.movie_kotlin.module.MovieManager
import com.weiren.zhang.movie_kotlin.viewmodel.movieinfomain.MovieInfoMainViewModel

@AndroidEntryPoint
@Route(path = RouterPath.MovieInfoMain.PATH_MovieInfoMain_HOME)
class MovieInfoMainActivity :
    BaseBindVMActivity<MovieInfoMainViewModel, MovieInfoMainActivityBinding>() {

    private var mMovieInfoFragment: Fragment? = null
    private var mStoreInfoFragment: Fragment? = null
    private var mMovieTimeTabsFragment: Fragment? = null
    private var mVideoFragment: Fragment? = null

    @JvmField
    @Autowired(name = BaseConstant.Movie_ID_KEY)
    var movieModelJSON: String = ""

    private lateinit var movieListModel: MovieListModel

    override fun getViewBinding() = MovieInfoMainActivityBinding.inflate(layoutInflater)

    override fun initWindow() {
        window.sharedElementsUseOverlay = false
        setExitSharedElementCallback(object : SharedElementCallback() {
            override fun onCaptureSharedElementSnapshot(
                sharedElement: View,
                viewToGlobalMatrix: Matrix?,
                screenBounds: RectF?
            ): Parcelable {
                sharedElement.alpha = 1f
                return super.onCaptureSharedElementSnapshot(
                    sharedElement,
                    viewToGlobalMatrix,
                    screenBounds
                )
            }
        })
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
            println(movieListModel.title)
            MovieManager.addMovieRecord(this, movieListModel)
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun initView() {
        //immersionStatusBar(true, android.R.color.white, true, 0.2f)
        initBottomNavigation()
    }

    override fun initData() {
        //set actionbar title
        ARouter.getInstance().inject(this)
        movieListModel = fromJson(movieModelJSON)
        actionbar!!.title = movieListModel.title

        mViewModel.getSelected().observerKt { index ->
            switchFragment(index)
        }
    }

    override fun initEvent() {

    }

    private fun initBottomNavigation() {
        //去掉底部默认选中背景
        //mBind.mBottomNavigationView.itemIconTintList = null
        mBind.mBottomNavigationView.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.item_daily -> saveAndSwitch(0)
                R.id.item_discover -> saveAndSwitch(1)
                R.id.item_hot -> saveAndSwitch(2)
                R.id.item_person -> saveAndSwitch(3)
            }
            true
        }
    }

    private fun saveAndSwitch(index: Int) {
        mViewModel.saveSelect(index)
        switchFragment(index)
    }

    private fun switchFragment(position: Int) {
        val transaction = supportFragmentManager.beginTransaction()
        hideFragments(transaction)
        when (position) {
            0 -> mMovieInfoFragment?.let {
                transaction.show(it)
            } ?: (ARouter.getInstance().build(RouterPath.MovieInfoMain.PATH_MovieInfo_HOME)
                .withString(BaseConstant.Movie_ID_KEY, toJson(movieListModel))
                .navigation() as Fragment).let {
                mMovieInfoFragment = it
                transaction.add(R.id.mContentFL, it, RouterPath.MovieInfoMain.PATH_MovieInfo_HOME)
            }
            1 -> mStoreInfoFragment?.let {
                transaction.show(it)
            } ?: (ARouter.getInstance().build(RouterPath.MovieInfoMain.PATH_StoreInfo_HOME)
                .withString(BaseConstant.Movie_ID_KEY, toJson(movieListModel))
                .navigation() as Fragment).let {
                mStoreInfoFragment = it
                transaction.add(R.id.mContentFL, it, RouterPath.MovieInfoMain.PATH_StoreInfo_HOME)
            }
            2 -> mMovieTimeTabsFragment?.let {
                transaction.show(it)
            } ?: (ARouter.getInstance().build(RouterPath.MovieInfoMain.PATH_MovieTimeTabs_HOME)
                .withString(BaseConstant.Movie_ID_KEY, toJson(movieListModel))
                .navigation() as Fragment).let {
                mMovieTimeTabsFragment = it
                transaction.add(
                    R.id.mContentFL,
                    it,
                    RouterPath.MovieInfoMain.PATH_MovieTimeTabs_HOME
                )
            }
            3 -> mVideoFragment?.let {
                transaction.show(it)
            } ?: (ARouter.getInstance().build(RouterPath.MovieInfoMain.PATH_Video_HOME)
                .withString(BaseConstant.Movie_ID_KEY, toJson(movieListModel))
                .navigation() as Fragment).let {
                mVideoFragment = it
                transaction.add(R.id.mContentFL, it, RouterPath.MovieInfoMain.PATH_Video_HOME)
            }
        }
        transaction.commitNowAllowingStateLoss()
    }

    //隐藏所有的Fragment
    private fun hideFragments(transaction: FragmentTransaction) {
        mMovieInfoFragment?.let { transaction.hide(it) }
        mStoreInfoFragment?.let { transaction.hide(it) }
        mMovieTimeTabsFragment?.let { transaction.hide(it) }
        mVideoFragment?.let { transaction.hide(it) }
    }
}
