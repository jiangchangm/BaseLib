package com.jiangcm.base.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.jiangcm.base.R
import com.jiangcm.base.base.vm.BaseViewModel
import com.jiangcm.base.dialog.ProgressView
import com.jiangcm.base.dialog.ProgressViewImpl


abstract class BaseVmFragment<VM : BaseViewModel> : Fragment(), ProgressView {

    //是否第一次加载
    private var isFirst: Boolean = true

    lateinit var mViewModel: VM

    private val progressViewImpl: ProgressViewImpl by lazy {
        ProgressViewImpl(this.requireContext())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(layoutId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        initViewModel()
        initView(savedInstanceState)
        observe()
        onVisible()
        registerDefUIChange()
        initData()
    }

    /**
     * 注册 UI 事件
     */
    private fun registerDefUIChange() {
        mViewModel.loadingChange.showDialog.observe(viewLifecycleOwner, Observer {
            showProgress()
        })
        mViewModel.loadingChange.dismissDialog.observe(viewLifecycleOwner, Observer {
            proDialogDismiss()
        })
    }

    /**
     * 加载(转圈)对话框
     */
    fun showProgress(
        message: String = getString(R.string.app_loading),
        cancel: Boolean = true,
        img: Int? = R.drawable.ic_baseline_refresh_24,
        logo: Int? = null
    ) {
        showProgressDialog(message, cancel, img, logo)
    }

    /**
     * 加载(转圈)对话框
     */
    override fun showProgressDialog(message: String, cancel: Boolean, img: Int?, logo: Int?) {
        progressViewImpl.showProgressDialog(message, cancel, img, logo)
    }

    /**
     * 隐藏加载(转圈)对话框
     */
    override fun proDialogDismiss() {
        progressViewImpl.proDialogDismiss()
    }

    /**
     * 获取ViewModel的class
     */
    protected abstract fun viewModelClass(): Class<VM>

    /**
     * 初始化ViewModel
     */
    private fun initViewModel() {
        mViewModel = ViewModelProvider(this).get(viewModelClass())
    }

    /**
     * 懒加载
     */
    abstract fun lazyLoadData()


    override fun onResume() {
        super.onResume()
        onVisible()
    }

    /**
     * 是否需要懒加载
     */
    private fun onVisible() {
        if (lifecycle.currentState == Lifecycle.State.STARTED && isFirst) {
            lazyLoadData()
            //在Fragment中，只有懒加载过了才能开启网络变化监听
//            NetworkStateManager.instance.mNetworkStateCallback.observe(
//                viewLifecycleOwner,
//                Observer {
//                    //不是首次订阅时调用方法，防止数据第一次监听错误
//                    if (!isFirst) {
//                        onNetworkStateChanged(it)
//                    }
//                })
            isFirst = false
        }
    }


    open fun observe() {}

    open fun initView(savedInstanceState: Bundle?) {}
    open fun initData() {}

    abstract fun layoutId(): Int


}
