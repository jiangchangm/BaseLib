package com.jiangcm.common.bus.ipc.json

import com.google.gson.Gson
import com.google.gson.JsonSyntaxException

class GsonConverter : JsonConverter {
    private val gson = Gson()

    override fun toJson(value: Any?): String {
        return gson.toJson(value)
    }

    @Throws(JsonSyntaxException::class)
    override fun <T> fromJson(json: String?, classOfT: Class<T>?): T {
        return gson.fromJson(json, classOfT)
    }
}
