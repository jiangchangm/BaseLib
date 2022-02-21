package com.jiangcm.common.bean

data class DemoResponse(
    val curPage: Int ,
    var over: Boolean = false,
    var pageCount: Int ,
    var size: Int ,
    var total: Int,
)