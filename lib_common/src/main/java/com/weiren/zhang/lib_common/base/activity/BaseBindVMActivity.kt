package com.weiren.zhang.lib_common.base.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBar
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.lib_common.base.viewmodel.ErrorState
import com.weiren.zhang.lib_common.base.viewmodel.LoadState
import com.weiren.zhang.lib_common.base.viewmodel.SuccessState
import com.weiren.zhang.lib_common.ext.errorToast
import java.lang.reflect.ParameterizedType

abstract class BaseBindVMActivity<VM : BaseViewModel, DB : ViewDataBinding> : AppCompatActivity() {

    lateinit var mBind: DB
    lateinit var mViewModel: VM
    lateinit var actionbar: ActionBar

    override fun onCreate(savedInstanceState: Bundle?) {
        initWindow()
        super.onCreate(savedInstanceState)
        mBind = getViewBinding()
        setContentView(mBind.root)

        //actionbar
        actionbar = supportActionBar!!
        //set back button
        actionbar!!.setDisplayHomeAsUpEnabled(true)

        initView()
        initViewModel()
        initData()
        initEvent()
    }

    abstract fun getViewBinding(): DB

    private fun initViewModel() {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        mViewModel = ViewModelProvider(this)[parameterizedType.actualTypeArguments[0] as Class<VM>]
        mViewModel.mStateLiveData.observe(this) { state ->
            when (state) {
                LoadState -> {
                    showLoading()
                }
                SuccessState -> {
                    hideLoading()
                }
                is ErrorState -> {
                    hideLoading()
                    state.errorMsg?.let { errorToast(it) }
                    handlerError()
                }
            }
        }
    }

    //扩展liveData的observe函数
    protected fun <T : Any> LiveData<T>.observerKt(block: (T) -> Unit) {
        this.observe(this@BaseBindVMActivity) {
            block(it)
        }
    }

    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun handlerError() {

    }

    open fun initWindow() {

    }

    abstract fun initView()

    abstract fun initData()

    abstract fun initEvent()

    override fun onDestroy() {
        super.onDestroy()
        if (::mBind.isInitialized) {
            mBind.unbind()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}