package com.jiangcm.baselib.ui.navigationButton.navigationFragment

import android.os.Bundle
import androidx.transition.TransitionInflater
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

    override fun initData() {
        super.initData()
        sharedElementEnterTransition = TransitionInflater.from(requireContext())
            .inflateTransition(R.transition.shared_image)
        mViewModel.str.value = "测试3"
        mViewModel.imgPath.value =
            "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091110%2Fc1sswqrvwwqc1sswqrvwwq.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648626630&t=e4fba33498f762b48478e40d0484b94c"
    }

    override fun lazyLoadData() {

    }

    override fun layoutId(): Int = R.layout.fragment_test3
}