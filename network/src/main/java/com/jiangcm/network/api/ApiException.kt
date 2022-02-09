package com.jiangcm.network.api

class ApiException(var code: Int, override var message: String) : RuntimeException()