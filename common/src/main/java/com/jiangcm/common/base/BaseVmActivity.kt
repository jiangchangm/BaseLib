package com.jiangcm.common.base

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmActivity<VM : BaseViewModel> : BaseActivity() {

    protected open lateinit var mViewModel: VM

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel()
        observe()
        initView()
        initData()
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
    open fun observe() {

    }

    /**
     * 数据初始化相关
     */
    open fun initView() {

    }

    /**
     * 懒加载数据
     */
    open fun initData() {

    }

}
