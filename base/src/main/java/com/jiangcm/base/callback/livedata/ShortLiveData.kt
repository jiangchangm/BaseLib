package com.jiangcm.base.callback.livedata

import androidx.lifecycle.MutableLiveData


class ShortLiveData : MutableLiveData<Short>() {
    override fun getValue(): Short {
        return super.getValue() ?: 0
    }
}