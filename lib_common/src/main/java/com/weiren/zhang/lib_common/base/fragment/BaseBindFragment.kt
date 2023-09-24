package com.weiren.zhang.lib_common.base.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseBindFragment<DB : ViewBinding> : Fragment() {

    private var mRootView: View? = null

    lateinit var mActivity: Activity

    private var mHasLoadData = false

    private var _binding: DB? = null
    protected val binding: DB get() = _binding!!

    abstract val bindingInflater: (LayoutInflater, ViewGroup?, Bundle?) -> DB

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as Activity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = bindingInflater.invoke(inflater, container, savedInstanceState)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onResume() {
        super.onResume()
        if (!mHasLoadData) {
            initData()
            mHasLoadData = true
        }
    }

    abstract fun initView()

    abstract fun initData()
}