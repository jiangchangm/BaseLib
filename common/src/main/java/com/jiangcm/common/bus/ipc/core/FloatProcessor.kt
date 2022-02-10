package com.jiangcm.common.bus.ipc.core

import android.os.Bundle
import com.jiangcm.common.bus.ipc.consts.IpcConst

class FloatProcessor : Processor {

    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is Float) {
            return false
        }
        bundle?.putFloat(IpcConst.KEY_VALUE, value)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getFloat(IpcConst.KEY_VALUE)
    }
}