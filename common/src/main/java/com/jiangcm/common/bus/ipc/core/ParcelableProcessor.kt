package com.jiangcm.common.bus.ipc.core

import android.os.Bundle
import android.os.Parcelable
import com.jiangcm.common.bus.ipc.consts.IpcConst

class ParcelableProcessor : Processor {

    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is Parcelable) {
            return false
        }
        bundle?.putParcelable(IpcConst.KEY_VALUE, value as Parcelable?)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getParcelable(IpcConst.KEY_VALUE)
    }
}