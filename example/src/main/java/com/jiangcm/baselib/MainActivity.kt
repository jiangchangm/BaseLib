package com.jiangcm.baselib

import android.os.Bundle
import com.jiangcm.common.base.BaseActivity

class MainActivity : BaseActivity() {

    override fun layoutRes(): Int = R.layout.activity_main

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showProgress(getString(R.string.app_loading))
    }

}