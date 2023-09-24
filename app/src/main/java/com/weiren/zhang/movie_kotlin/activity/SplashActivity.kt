package com.weiren.zhang.movie_kotlin.activity

import androidx.lifecycle.lifecycleScope
import com.weiren.zhang.lib_common.base.activity.BaseBindActivity
import com.weiren.zhang.lib_common.ext.startActivity
import com.weiren.zhang.movie_kotlin.databinding.ActivitySplashBinding
import kotlinx.coroutines.delay

class SplashActivity : BaseBindActivity<ActivitySplashBinding>() {

    override fun getViewBinding() = ActivitySplashBinding.inflate(layoutInflater)

    override fun initView() {
        mBind.context = this
        lifecycleScope.launchWhenCreated {
            delay(500)
            startActivity<HomeActivity>()
            finish()
        }
    }
}