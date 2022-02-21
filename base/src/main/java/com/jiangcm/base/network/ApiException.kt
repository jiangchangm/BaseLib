package com.jiangcm.base.network

class ApiException(var code: Int, override var message: String) : RuntimeException()
