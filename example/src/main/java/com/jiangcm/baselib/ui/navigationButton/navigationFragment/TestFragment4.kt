package com.jiangcm.baselib.ui.navigationButton.navigationFragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jiangcm.baselib.R
import com.jiangcm.baselib.databinding.FragmentTest4Binding
import com.jiangcm.baselib.ui.navigationButton.TestFragmentVM
import com.jiangcm.common.base.BaseFragment

class TestFragment4 : BaseFragment<TestFragmentVM, FragmentTest4Binding>() {


    override fun viewModelClass(): Class<TestFragmentVM> = TestFragmentVM::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
    }

    override fun initData() {
        super.initData()
        mDatabind.test4.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment4_to_testFragment12)
        }
    }

    override fun lazyLoadData() {
        mViewModel.str.value = "测试4"
    }

    override fun layoutId(): Int = R.layout.fragment_test4
}