package com.jiangcm.baselib.ui.navigationButton.navigationFragment

import android.os.Bundle
import com.jiangcm.baselib.R
import com.jiangcm.baselib.databinding.FragmentTest3Binding
import com.jiangcm.baselib.ui.navigationButton.TestFragmentVM
import com.jiangcm.common.base.BaseFragment

class TestFragment3 : BaseFragment<TestFragmentVM, FragmentTest3Binding>() {


    override fun viewModelClass(): Class<TestFragmentVM> = TestFragmentVM::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
    }

    override fun lazyLoadData() {
        mViewModel.str.value = "测试3"
    }

    override fun layoutId(): Int = R.layout.fragment_test3
}