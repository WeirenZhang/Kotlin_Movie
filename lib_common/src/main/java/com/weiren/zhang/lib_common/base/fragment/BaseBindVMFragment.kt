package com.weiren.zhang.lib_common.base.fragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModelProvider
import androidx.viewbinding.ViewBinding
import com.weiren.zhang.lib_common.base.viewmodel.BaseViewModel
import com.weiren.zhang.lib_common.base.viewmodel.ErrorState
import com.weiren.zhang.lib_common.base.viewmodel.LoadState
import com.weiren.zhang.lib_common.base.viewmodel.SuccessState
import com.weiren.zhang.lib_common.ext.errorToast
import java.lang.reflect.ParameterizedType

abstract class BaseBindVMFragment<VM : BaseViewModel, DB : ViewBinding> : BaseBindFragment<DB>() {

    lateinit var mViewModel: VM

    override fun initData() {
        initViewModel()
        lazyLoadData()
    }

    private fun initViewModel() {
        val parameterizedType = javaClass.genericSuperclass as ParameterizedType
        mViewModel = ViewModelProvider(this)[parameterizedType.actualTypeArguments[0] as Class<VM>]
        mViewModel.mStateLiveData.observe(viewLifecycleOwner) { state ->
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

    protected fun <T : Any> LiveData<T>.observerKt(block: (T) -> Unit) {
        this.observe(viewLifecycleOwner) {
            block(it)
        }
    }

    //由于每个页面的加载框可能不一致，所以此处暴露给子类实现
    open fun showLoading() {

    }

    open fun hideLoading() {

    }

    open fun handlerError() {

    }

    abstract fun lazyLoadData()
}