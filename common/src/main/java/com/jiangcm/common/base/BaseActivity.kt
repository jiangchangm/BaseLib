package com.jiangcm.common.base

import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.jiangcm.base.base.activity.BaseVmDbActivity
import com.jiangcm.base.base.vm.BaseViewModel

abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    override fun initData() {
        ARouter.getInstance().inject(this)
    }
}