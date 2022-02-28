package com.jiangcm.baselib.ui.navigationButton.navigationFragment

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import com.jiangcm.baselib.R
import com.jiangcm.baselib.databinding.FragmentTest1Binding
import com.jiangcm.baselib.ui.navigationButton.TestFragmentVM
import com.jiangcm.common.base.BaseFragment

class TestFragment1 : BaseFragment<TestFragmentVM, FragmentTest1Binding>() {


    override fun viewModelClass(): Class<TestFragmentVM> = TestFragmentVM::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
    }

    override fun initData() {
        super.initData()
        mDatabind.toTest2.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment1_to_testFragment2)
        }
        mDatabind.toTest3.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment1_to_testFragment3)
        }
        mDatabind.toTest5.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment1_to_webActivity)
        }
    }


    override fun lazyLoadData() {
        mViewModel.str.value = "测试1"
    }

    override fun layoutId(): Int = R.layout.fragment_test1
}