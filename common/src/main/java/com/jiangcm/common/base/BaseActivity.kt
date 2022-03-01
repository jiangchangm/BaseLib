package com.jiangcm.common.base

import android.os.Bundle
import androidx.databinding.ViewDataBinding
import com.alibaba.android.arouter.launcher.ARouter
import com.jiangcm.base.base.activity.BaseVmDbActivity
import com.jiangcm.base.base.vm.BaseViewModel
import com.jiangcm.common.R


abstract class BaseActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmDbActivity<VM, DB>() {

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        overridePendingTransition(R.anim.slide_right_to_left_in, R.anim.slide_right_to_left_out)
    }

    override fun initData() {
        ARouter.getInstance().inject(this)
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.slide_left_to_right_in, R.anim.slide_left_to_right_out)
    }
}