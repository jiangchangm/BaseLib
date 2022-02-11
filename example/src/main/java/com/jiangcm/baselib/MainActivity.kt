package com.jiangcm.baselib

import com.jiangcm.base.core.AppManager
import com.jiangcm.base.repository.network.vm.TestViewModel
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.common.base.BaseVmActivity
import com.jiangcm.common.ext.Terror
import com.jiangcm.common.log.LogUtils

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
        mViewModel.retResponse.observe(this, {
            proDialogDismiss()
        })
        mViewModel.retError.observe(this, {
            Terror(it.toString())
        })
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }
}