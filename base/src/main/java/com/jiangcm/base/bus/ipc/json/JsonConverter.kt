package com.jiangcm.base.bus.ipc.json

interface JsonConverter {
    fun toJson(value: Any?): String?
    fun <T> fromJson(json: String?, classOfT: Class<T>?): T
}