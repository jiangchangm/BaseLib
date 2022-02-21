package com.jiangcm.base.widght.guideview

/**
 * 遮罩系统运行异常的封装
 */
internal class BuildException : RuntimeException {
    private val mDetailMessage: String

    constructor() {
        mDetailMessage = "General error."
    }

    constructor(detailMessage: String) {
        mDetailMessage = detailMessage
    }

    override val message: String
        get() = "Build GuideFragment failed: $mDetailMessage"

    companion object {
        private const val serialVersionUID = 6208777692136933357L
    }
}