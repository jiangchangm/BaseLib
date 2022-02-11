package com.jiangcm.base.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider

abstract class BaseVmFragment<VM : BaseViewModel, T : ViewDataBinding>(@LayoutRes val layoutId: Int) :
    Fragment(layoutId) {

    protected open lateinit var mViewModel: VM
    private var lazyLoaded = false
    private var _binding: T? = null
    private val binding get() = _binding!!

    private fun <T : ViewDataBinding> binding(
        inflater: LayoutInflater,
        @LayoutRes layoutId: Int,
        container: ViewGroup?
    ): T = DataBindingUtil.inflate<T>(inflater, layoutId, container, false).apply {
        lifecycleOwner = this@BaseVmFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = binding(inflater, layoutId, container)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initViewModel()
        observe()
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)

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
