package com.jiangcm.base.bus.ipc.core

import android.os.Bundle
import com.jiangcm.base.bus.ipc.consts.IpcConst
import com.jiangcm.base.bus.ipc.json.GsonConverter
import com.jiangcm.base.bus.ipc.json.JsonConverter

class GsonProcessor : Processor {

    private val jsonConverter: JsonConverter = GsonConverter()
    override  fun writeToBundle(bundle: Bundle?, value: Any?): Boolean {
        val json: String? = jsonConverter.toJson(value)
        bundle?.putString(IpcConst.KEY_VALUE, json)
        bundle?.putString(IpcConst.KEY_CLASS_NAME, value?.javaClass?.canonicalName)
        return true
    }

    @Throws(ClassNotFoundException::class)
    override fun createFromBundle(bundle: Bundle?): Any {
        val json = bundle?.getString(IpcConst.KEY_VALUE)
        val className = bundle?.getString(IpcConst.KEY_CLASS_NAME)
        var classType: Class<*>? = null
        try {
            classType = Class.forName(className?:"")
        } catch (e: ClassNotFoundException) {
            val last = className?.lastIndexOf('.')
            if (last != -1) {
                val pn = className?.substring(0, last?:0)
                val cn = className?.substring(last?:0 + 1)
                classType = Class.forName("$pn$$cn")
            }
        }
        return jsonConverter.fromJson(json, classType)
    }
}