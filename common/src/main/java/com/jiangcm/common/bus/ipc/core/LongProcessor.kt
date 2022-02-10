package com.jiangcm.common.bus.ipc.core

import android.os.Bundle
import com.jiangcm.common.bus.ipc.consts.IpcConst

class LongProcessor : Processor {

    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is Long) {
            return false
        }
        bundle?.putLong(IpcConst.KEY_VALUE, value)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getLong(IpcConst.KEY_VALUE)
    }
}
