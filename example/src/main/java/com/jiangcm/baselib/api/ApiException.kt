package com.jiangcm.baselib.api

class ApiException(var code: Int, override var message: String) : RuntimeException()