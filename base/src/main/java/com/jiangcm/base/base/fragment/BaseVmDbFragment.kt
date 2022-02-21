package com.jiangcm.base.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.jiangcm.base.base.vm.BaseViewModel

abstract class  BaseVmDbFragment<VM : BaseViewModel, DB : ViewDataBinding> : BaseVmFragment<VM>() {

    lateinit var mDatabind: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabind=  DataBindingUtil.inflate<DB>(inflater, layoutId(), container, false).apply {
            lifecycleOwner = this@BaseVmDbFragment
        }
        return mDatabind.root
    }

}
