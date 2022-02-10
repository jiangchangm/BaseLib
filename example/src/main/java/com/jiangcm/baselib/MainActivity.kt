package com.jiangcm.baselib

import android.widget.TextView
import androidx.databinding.DataBindingUtil
import com.jiangcm.base.core.AppManager
import com.jiangcm.base.repository.network.vm.TestViewModel
import com.jiangcm.baselib.databinding.ActivityMainBinding
import com.jiangcm.common.base.BaseVmActivity
import com.jiangcm.common.ext.Terror

class MainActivity : BaseVmActivity<TestViewModel>() {

    private lateinit var mainBinding: ActivityMainBinding

    override fun viewModelClass(): Class<TestViewModel> = TestViewModel::class.java

    override fun initView() {
        super.initView()
        mainBinding= DataBindingUtil.setContentView(this, R.layout.activity_main)

    }

    override fun initData() {
        mViewModel.refreshProjectList()
    }

    override fun observe() {
        super.observe()
        mViewModel.retResponse.observe(this, {
            findViewById<TextView>(R.id.textView)?.text = it?.toString()
        })
        mViewModel.retError.observe(this, {
           Terror(it.toString())
        })
    }

    override fun onBackPressed() {
        AppManager.instance.doubleBackToExit()
    }
}