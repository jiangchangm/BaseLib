package com.jiangcm.common.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jiangcm.common.ext.ImgOption
import com.jiangcm.common.ext.loadImgUrlWithGlide

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

@BindingAdapter("imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.loadImgUrlWithGlide(url, ImgOption.imgOption)
}
