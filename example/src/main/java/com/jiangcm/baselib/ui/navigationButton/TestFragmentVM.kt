package com.jiangcm.baselib.ui.navigationButton

import androidx.lifecycle.MutableLiveData
import com.jiangcm.base.callback.databind.StringObservableField
import com.jiangcm.common.base.AppBaseViewModel

class TestFragmentVM : AppBaseViewModel() {

    val str = MutableLiveData<String>()
    val imgPath = MutableLiveData<String>()

}