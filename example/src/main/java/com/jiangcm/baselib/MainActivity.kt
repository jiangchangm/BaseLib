package com.jiangcm.baselib

import android.util.Log
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jiangcm.common.core.AppManager
import com.jiangcm.common.repository.network.vm.TestViewModel
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.base.base.BaseVmActivity
import com.jiangcm.base.bus.Bus
import com.jiangcm.base.bus.LiveEventBus
import com.jiangcm.base.ext.Terror
import com.jiangcm.base.log.LogUtils

class MainActivity : BaseVmActivity<TestViewModel>() {


    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    private val binding by binding<ActivityMainBinding>(R.layout.activity_main)


    override fun initView() {
        binding.vm = mViewModel
    }

    override fun initData() {
        showProgress(cancel = false)
        mViewModel.refreshProjectList()
    }

    override fun observe() {
        super.observe()
        mViewModel.retResponse.observe(this) {
            proDialogDismiss()
        }
        mViewModel.retError.observe(this) {
            Terror(it.toString())
        }
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }
}