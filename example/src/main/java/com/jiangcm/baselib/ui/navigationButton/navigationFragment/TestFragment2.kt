package com.jiangcm.baselib.ui.navigationButton.navigationFragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jiangcm.baselib.R
import com.jiangcm.baselib.databinding.FragmentTest2Binding
import com.jiangcm.baselib.ui.navigationButton.TestFragmentVM
import com.jiangcm.common.base.BaseFragment

class TestFragment2 : BaseFragment<TestFragmentVM, FragmentTest2Binding>() {


    override fun viewModelClass(): Class<TestFragmentVM> = TestFragmentVM::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
    }

    override fun initData() {
        super.initData()
        mDatabind.test2.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment2_to_testFragment42)
        }
    }

    override fun lazyLoadData() {
        mViewModel.str.value = "测试2"
    }

    override fun layoutId(): Int = R.layout.fragment_test2
}