package com.jiangcm.base.api

class ApiException(var code: Int, override var message: String) : RuntimeException()