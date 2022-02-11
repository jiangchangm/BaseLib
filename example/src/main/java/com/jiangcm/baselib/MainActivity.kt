package com.jiangcm.baselib

import com.jiangcm.base.core.AppManager
import com.jiangcm.base.repository.network.vm.TestViewModel
import com.jiangcm.common.base.BaseVmActivity
import com.jiangcm.common.ext.Terror

class MainActivity : BaseVmActivity<TestViewModel>() {


    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun initView() {
        super.initView()
    }

    override fun initData() {
        mViewModel.refreshProjectList()
    }

    override fun observe() {
        super.observe()
//        mViewModel.retResponse.observe(this, {
//            findViewById<TextView>(R.id.textView)?.text = it?.toString()
//        })
        mViewModel.retError.observe(this, {
           Terror(it.toString())
        })
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }
}