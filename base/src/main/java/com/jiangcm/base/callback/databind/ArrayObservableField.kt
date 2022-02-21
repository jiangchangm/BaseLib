package com.jiangcm.base.callback.databind

import androidx.databinding.ObservableField


class ArrayObservableField<T>(value: MutableList<T> = mutableListOf()) :
    ObservableField<MutableList<T>>(value) {

    override fun get(): MutableList<T>? {
        return super.get()
    }
}