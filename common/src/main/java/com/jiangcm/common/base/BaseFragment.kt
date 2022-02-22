package com.jiangcm.common.base

import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.jiangcm.base.base.fragment.BaseVmDbFragment
import com.jiangcm.base.base.vm.BaseViewModel

abstract class BaseFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbFragment<VM, DB>() {
    override fun initData() {
        ARouter.getInstance().inject(this)
    }
}