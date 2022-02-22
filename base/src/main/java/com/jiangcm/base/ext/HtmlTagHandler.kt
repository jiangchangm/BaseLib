package com.jiangcm.base.ext

import android.graphics.Color
import android.text.Editable
import android.text.Html
import android.text.Spanned
import android.text.TextUtils
import android.text.style.AbsoluteSizeSpan
import android.text.style.ForegroundColorSpan
import org.xml.sax.XMLReader
import java.lang.reflect.Field


class HtmlTagHandler(tagName: String) : Html.TagHandler {
    // 自定义标签名称
    private val tagName: String

    // 标签开始索引
    private var startIndex = 0

    // 标签结束索引
    private var endIndex = 0

    // 存放标签所有属性键值对
    val attributes: HashMap<String, String> = HashMap()
    override fun handleTag(
        opening: Boolean,
        tag: String,
        output: Editable,
        xmlReader: XMLReader
    ) {
        // 判断是否是当前需要的tag
        if (tag.equals(tagName, ignoreCase = true)) {
            // 解析所有属性值
            parseAttributes(xmlReader)
            if (opening) {
                startHandleTag(tag, output, xmlReader)
            } else {
                endEndHandleTag(tag, output, xmlReader)
            }
        }
    }

    fun startHandleTag(tag: String?, output: Editable, xmlReader: XMLReader?) {
        startIndex = output.length
    }

    fun endEndHandleTag(tag: String?, output: Editable, xmlReader: XMLReader?) {
        endIndex = output.length

        // 获取对应的属性值
        val color = attributes["color"]
        val size = attributes["size"]?.split("px".toRegex())?.toTypedArray()?.get(0)

        // 设置颜色
        if (!TextUtils.isEmpty(color)) {
            output.setSpan(
                ForegroundColorSpan(Color.parseColor(color)), startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
        // 设置字体大小
        if (!TextUtils.isEmpty(size)) {
            output.setSpan(
                size?.toInt()?.let { AbsoluteSizeSpan(it) }, startIndex, endIndex,
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
            )
        }
    }

    /**
     * 解析所有属性值
     *
     * @param xmlReader
     */
    private fun parseAttributes(xmlReader: XMLReader) {
        try {
            val elementField: Field = xmlReader.javaClass.getDeclaredField("theNewElement")
            elementField.setAccessible(true)
            val element: Any = elementField.get(xmlReader)
            val attsField: Field = element.javaClass.getDeclaredField("theAtts")
            attsField.setAccessible(true)
            val atts: Any = attsField.get(element)
            val dataField: Field = atts.javaClass.getDeclaredField("data")
            dataField.setAccessible(true)
            val data =
                dataField.get(atts) as Array<String>
            val lengthField: Field = atts.javaClass.getDeclaredField("length")
            lengthField.setAccessible(true)
            val len = lengthField.get(atts) as Int
            for (i in 0 until len) {
                attributes[data[i * 5 + 1]] = data[i * 5 + 4]
            }
        } catch (e: Exception) {
        }
    }

    init {
        this.tagName = tagName
    }
}