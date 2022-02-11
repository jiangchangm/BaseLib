package com.jiangcm.base.bus.ipc.core

import android.os.Bundle
import com.jiangcm.base.bus.ipc.consts.IpcConst
import java.io.Serializable

class SerializableProcessor : Processor {
    override fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        if (value !is Serializable) {
            return false
        }
        bundle?.putSerializable(IpcConst.KEY_VALUE, value as Serializable?)
        return true
    }

    override fun createFromBundle(bundle: Bundle?): Any? {
        return bundle?.getSerializable(IpcConst.KEY_VALUE)
    }
}