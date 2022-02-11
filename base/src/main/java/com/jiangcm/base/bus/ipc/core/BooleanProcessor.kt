package com.jiangcm.base.bus.ipc.core

import android.os.Bundle
import com.jiangcm.base.bus.ipc.consts.IpcConst

class BooleanProcessor : Processor {

    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is Boolean) {
            return false
        }
        bundle?.putBoolean(IpcConst.KEY_VALUE, value)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getBoolean(IpcConst.KEY_VALUE)
    }

}
