package com.jiangcm.common.bus.ipc.core

import android.os.Bundle
import java.lang.Exception

interface Processor {
    @Throws(Exception::class)
    fun writeToBundle(bundle: Bundle?, value: Any?): Boolean

    @Throws(Exception::class)
    fun createFromBundle(bundle: Bundle?): Any?
}
