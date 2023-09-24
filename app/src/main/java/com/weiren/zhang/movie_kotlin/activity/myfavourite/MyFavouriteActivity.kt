package com.weiren.zhang.movie_kotlin.activity.myfavourite;

import android.app.SharedElementCallback
import android.graphics.Matrix
import android.graphics.RectF
import android.os.Parcelable
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.weiren.zhang.lib_common.base.activity.BaseBindVMActivity
import dagger.hilt.android.AndroidEntryPoint
import com.weiren.zhang.lib_common.router.RouterPath
import com.weiren.zhang.movie_kotlin.R
import com.weiren.zhang.movie_kotlin.databinding.MyfavouriteActivityBinding
import com.weiren.zhang.movie_kotlin.viewmodel.myfavourite.MyFavouriteViewModel

@AndroidEntryPoint
@Route(path = RouterPath.MyFavourite.PATH_MyFavourite_HOME)
class MyFavouriteActivity :
    BaseBindVMActivity<MyFavouriteViewModel, MyfavouriteActivityBinding>() {

    private var mMovieMyFavouriteFragment: Fragment? = null
    private var mTheaterMyFavouriteFragment: Fragment? = null

    override fun getViewBinding() = MyfavouriteActivityBinding.inflate(layoutInflater)

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

    override fun initView() {
        //immersionStatusBar(true, android.R.color.white, true, 0.2f)
        initBottomNavigation()
    }

    override fun initData() {
        //set actionbar title
        actionbar!!.title = "我的最愛"

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
            0 -> mMovieMyFavouriteFragment?.let {
                transaction.show(it)
            } ?: (ARouter.getInstance().build(RouterPath.MyFavourite.PATH_MovieMyFavourite_HOME)
                .navigation() as Fragment).let {
                mMovieMyFavouriteFragment = it
                transaction.add(R.id.mContentFL, it, RouterPath.MyFavourite.PATH_MovieMyFavourite_HOME)
            }
            1 -> mTheaterMyFavouriteFragment?.let {
                transaction.show(it)
            } ?: (ARouter.getInstance().build(RouterPath.MyFavourite.PATH_TheaterMyFavourite_HOME)
                .navigation() as Fragment).let {
                mTheaterMyFavouriteFragment = it
                transaction.add(R.id.mContentFL, it, RouterPath.MyFavourite.PATH_TheaterMyFavourite_HOME)
            }
        }
        transaction.commitNowAllowingStateLoss()
    }

    //隐藏所有的Fragment
    private fun hideFragments(transaction: FragmentTransaction) {
        mMovieMyFavouriteFragment?.let { transaction.hide(it) }
        mTheaterMyFavouriteFragment?.let { transaction.hide(it) }
    }
}
