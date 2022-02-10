package com.jiangcm.common.bus.ipc.json

interface JsonConverter {
    fun toJson(value: Any?): String?
    fun <T> fromJson(json: String?, classOfT: Class<T>?): T
}