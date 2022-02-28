package com.jiangcm.common.binding

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.jiangcm.common.ext.ImgOption
import com.jiangcm.common.ext.loadImgUrlWithGlide
import com.jiangcm.common.ext.loadImgUrlWithThumbnailAuto

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

@BindingAdapter("app:imageUrl")
fun loadImage(imageView: ImageView, url: String?) {
    imageView.loadImgUrlWithThumbnailAuto(url, ImgOption.imgOption)
}
