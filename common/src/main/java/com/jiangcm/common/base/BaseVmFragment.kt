package com.jiangcm.common.base

import android.os.Bundle
import android.view.View
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmFragment<VM : BaseViewModel> : BaseFragment() {

    protected open lateinit var mViewModel: VM
    private var lazyLoaded = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
    }

    override fun onResume() {
        super.onResume()
        // 实现懒加载
        if (!lazyLoaded) {
            lazyLoadData()
            lazyLoaded = true
        }
    }

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * 获取ViewModel的class
     */
    abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {
    }

    /**
     * View初始化相关
     */
    open fun initView() {
    }

    /**
     * 数据初始化相关
     */
    open fun initData() {
    }

    /**
     * 懒加载数据
     */
    open fun lazyLoadData() {
    }
}
