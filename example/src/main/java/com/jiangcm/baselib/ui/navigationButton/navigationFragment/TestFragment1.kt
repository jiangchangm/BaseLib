package com.jiangcm.baselib.ui.navigationButton.navigationFragment

import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityOptionsCompat
import androidx.navigation.ActivityNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import cc.shinichi.library.ImagePreview
import com.jiangcm.baselib.R
import com.jiangcm.baselib.databinding.FragmentTest1Binding
import com.jiangcm.baselib.ui.navigationButton.TestFragmentVM
import com.jiangcm.common.base.BaseFragment

class TestFragment1 : BaseFragment<TestFragmentVM, FragmentTest1Binding>() {

    private val imgPath: String =
        "https://gimg2.baidu.com/image_search/src=http%3A%2F%2Fimg9.51tietu.net%2Fpic%2F2019-091110%2Fc1sswqrvwwqc1sswqrvwwq.jpg&refer=http%3A%2F%2Fimg9.51tietu.net&app=2002&size=f9999,10000&q=a80&n=0&g=0n&fmt=jpeg?sec=1648626630&t=e4fba33498f762b48478e40d0484b94c"

    override fun viewModelClass(): Class<TestFragmentVM> = TestFragmentVM::class.java

    override fun initView(savedInstanceState: Bundle?) {
        super.initView(savedInstanceState)
        mDatabind.vm = mViewModel
        mDatabind.click = ProxyClick()
    }

    override fun initData() {
        super.initData()
        mDatabind.toTest2.setOnClickListener {
            findNavController().navigate(R.id.action_testFragment1_to_testFragment2)
        }
        mDatabind.toTest3.setOnClickListener {
            val extras = FragmentNavigatorExtras(mDatabind.img to "testImg")
            findNavController().navigate(
                R.id.action_testFragment1_to_testFragment3,
                null,
                null,
                extras
            )
        }
        mDatabind.toTest5.setOnClickListener {
            val pair = androidx.core.util.Pair<View, String>(mDatabind.img, "testImg")
            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(requireActivity(), pair)
            val extras = ActivityNavigator.Extras.Builder().setActivityOptions(option).build()
            findNavController().navigate(
                R.id.action_testFragment1_to_testActivity,
                null, null, extras
            )
        }
        mViewModel.str.value = "??????1"
        mViewModel.imgPath.value = imgPath
    }


    override fun lazyLoadData() {
    }

    override fun layoutId(): Int = R.layout.fragment_test1

    inner class ProxyClick {
        fun checkImg() {
            ImagePreview.getInstance().setContext(requireActivity())
                .setIndex(0)
                .setImage(imgPath)
                .setTransitionView(mDatabind.img)
                .setTransitionShareElementName("shared_element_container")
                .start()
        }
    }
}





















