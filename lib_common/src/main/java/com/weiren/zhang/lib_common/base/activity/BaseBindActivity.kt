package com.weiren.zhang.lib_common.base.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding

abstract class BaseBindActivity<DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mBind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBind = getViewBinding()
        setContentView(mBind.root)
        initWindow()
        initView()
    }

    abstract fun getViewBinding(): DB

    abstract fun initView()

    open fun initWindow() {

    }

    override fun onDestroy() {
        super.onDestroy()
        if (::mBind.isInitialized) {
            mBind.unbind()
        }
    }
}