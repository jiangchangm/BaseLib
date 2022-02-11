package com.jiangcm.base.bus.ipc.core

import android.os.Bundle
import com.jiangcm.base.bus.ipc.consts.IpcConst

class IntProcessor : Processor {
    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is Int) {
            return false
        }
        bundle?.putInt(IpcConst.KEY_VALUE, value)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getInt(IpcConst.KEY_VALUE)
    }
}