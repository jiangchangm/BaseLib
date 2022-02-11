package com.jiangcm.base.base

import android.os.Bundle
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmActivity<VM : BaseViewModel> : BaseLifecycleActivity() {

    protected open lateinit var mViewModel: VM

    protected inline fun <reified T : ViewDataBinding> binding(
        @LayoutRes resId: Int
    ): Lazy<T> = lazy { DataBindingUtil.setContentView<T>(this, resId).apply {
        lifecycleOwner = this@BaseVmActivity
    } }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
        setTranslucentStatus()
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
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * 订阅，LiveData、Bus
     */
    open fun observe() {}

    open fun initView() {}
    open fun initData() {}

}
