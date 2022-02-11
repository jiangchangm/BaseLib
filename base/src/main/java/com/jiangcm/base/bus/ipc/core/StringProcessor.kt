package com.jiangcm.base.bus.ipc.core

import android.os.Bundle
import com.jiangcm.base.bus.ipc.consts.IpcConst

class StringProcessor : Processor {

    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is String) {
            return false
        }
        bundle?.putString(IpcConst.KEY_VALUE, value as String?)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getString(IpcConst.KEY_VALUE)
    }
}