package com.jiangcm.base.bus.ipc.core

import android.os.Bundle
import com.jiangcm.base.bus.ipc.consts.IpcConst

class DoubleProcessor : Processor {

    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value == null) {
            return false
        }
        if (value !is Double) {
            return false
        }
        bundle?.putDouble(IpcConst.KEY_VALUE, ((value as Double?)!!))
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getDouble(IpcConst.KEY_VALUE)
    }
}