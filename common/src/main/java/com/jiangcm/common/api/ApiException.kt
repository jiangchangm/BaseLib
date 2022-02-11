package com.jiangcm.common.api

class ApiException(var code: Int, override var message: String) : RuntimeException()