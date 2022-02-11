package com.jiangcm.common.base

import android.os.Bundle

abstract class BaseActivity : BaseLifecycleActivity(){


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(getLayoutResId())
        initView()
        initData()
    }

    abstract fun getLayoutResId(): Int
    open fun initView(){}
    open fun initData(){}

}
