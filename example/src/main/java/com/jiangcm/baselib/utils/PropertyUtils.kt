@file:Suppress("RECEIVER_NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
package com.jiangcm.baselib.utils

import java.util.*

/**
 * @author: jcm
 * @email: jiangcm@aplus-it.cn
 * @createTime: 20-6-3
 */
object PropertyUtils {

    const val BASE_API_URL = "api"
    const val IS_DEBUG = "isDebug"

    private var cacheMap = mutableMapOf<String, String>()

    @JvmStatic
    @Suppress("all")
    fun getPropertiesByName(name: String): String {
        var proper: String? = cacheMap[name]
        if (!proper.isNullOrBlank()) {
            return proper
        }
        val properties = Properties()
        PropertyUtils::class.java.classLoader
            .getResourceAsStream("conf.properties").use {
                properties.load(it)
                proper = properties.getProperty(name)
                proper?.let { p ->
                    cacheMap.put(name, p)
                }
            }
        return proper ?: ""
    }
}
