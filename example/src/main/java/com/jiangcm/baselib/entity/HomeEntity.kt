package com.jiangcm.baselib.entity

import com.chad.library.adapter.base.entity.SectionEntity

data class HomeEntity(
    val name: String = "",
    val path: String = "",
    val headerTitle: String = ""
) : SectionEntity {
    override val isHeader: Boolean
        get() = headerTitle.isNotBlank()
    override val itemType: Int
        get() = super.itemType


}