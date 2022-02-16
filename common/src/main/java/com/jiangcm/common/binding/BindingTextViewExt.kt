package com.jiangcm.common.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("android:text")
fun TextView.setBindingText(text: String?) {
    setText(
        if (text.isNullOrEmpty() || text == "null") {
            ""
        } else {
            text
        }
    )
}
