package com.jiangcm.baselib.ui.navigationButton

import androidx.lifecycle.MutableLiveData
import com.jiangcm.common.base.BaseViewModel

class TestFragmentVM : BaseViewModel() {

    val str = MutableLiveData<String>()
    val imgPath = MutableLiveData<String>()

}