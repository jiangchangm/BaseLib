package com.jiangcm.base.base.activity

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jiangcm.base.base.vm.BaseViewModel


abstract class  BaseVmDbActivity<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmActivity<VM>() {


    lateinit var mDatabind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBind() {
        mDatabind= DataBindingUtil.setContentView<DB>(this, layoutId()).apply {
            lifecycleOwner = this@BaseVmDbActivity
        }
    }

}
