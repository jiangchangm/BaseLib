package com.jiangcm.common.ext

import android.graphics.Bitmap
import android.net.Uri
import android.util.Log
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.RequestManager
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.jiangcm.common.R
import com.jiangcm.common.utils.PropertyUtils

object ImgOption {

    val imgOption: RequestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .dontTransform()
        .dontAnimate()


    val defaultOption: RequestOptions = RequestOptions()
        .dontTransform()
        .dontAnimate()

}

var videoMap = mutableMapOf<String, Bitmap>()

fun ImageView?.loadImgUrlWithManager(
    glide: RequestManager,
    url: String?,
    shouldProcess: Boolean = false
) {
    this.loadImgUrlWithManager(glide, url, ImgOption.defaultOption, shouldProcess)
}

fun ImageView?.loadImgLocalUrlWithManager(
    glide: RequestManager,
    url: String?,
    shouldProcess: Boolean = false
) {
    this.loadImgUrlWithManager(glide, url, ImgOption.defaultOption, shouldProcess, false)
}


fun ImageView.loadImgResWithManager(glide: RequestManager, res: Int?) {
    glide.load(res)
        .apply(ImgOption.defaultOption)
        .into(this)
}

fun ImageView?.loadImgUrlWithManager(
    glide: RequestManager,
    url: String?,
    option: RequestOptions,
    shouldProcess: Boolean = false,
    shouldCheck: Boolean = true
) {
    val process = processImg(this, shouldProcess)
    val mUrl = if (shouldCheck) {
        checkUrl(url, process)
    } else {
        url
    }
    this?.let {
        glide.load(mUrl)
            .apply(option)
            .dontAnimate()
            .into(this)
    }
}

fun ImageView?.loadImgUrlWithGlide(
    url: String?,
    option: RequestOptions,
    shouldProcess: Boolean = false,
    shouldCheck: Boolean = true
) {
    val process = processImg(this, shouldProcess)
    val mUrl = if (shouldCheck) {
        checkUrl(url, process)
    } else {
        url
    }
    this?.let {
        Glide.with(it.context).load(mUrl)
            .apply(option)
            .dontAnimate()
            .into(this)
    }
}

fun ImageView?.loadImgUrlWithThumbnailAuto(
    url: String?,
    option: RequestOptions,
    shouldProcess: Boolean = false,
    shouldCheck: Boolean = true
) {
    val process = processImg(this, shouldProcess)
    val mUrl = if (shouldCheck) {
        checkUrl(url, process)
    } else {
        url
    }
    this?.let {
        Glide.with(it.context).load(mUrl)
            .apply(option)
            .thumbnail( 0.1f )
            .dontAnimate()
            .into(this)
    }
}

fun ImageView?.loadImgUriWithManager(
    glide: RequestManager,
    uri: Uri,
    option: RequestOptions
) {
    this?.let {
        glide.load(uri)
            .apply(option)
            .into(this)
    }
}


fun ImageView?.loadImgUrlWithManager(
    glide: RequestManager,
    url: Int?,
    option: RequestOptions
) {
    this?.let {
        glide.load(url)
            .apply(option)
            .into(it)
    }
}


fun ImageView?.loadImgUrlWithThumbnail(
    glide: RequestManager,
    thumb: String?,
    url: String?,
    option: RequestOptions,
    shouldProcess: Boolean = false
) {
    val process = processImg(this, shouldProcess)
    val mUrl = checkUrl(url, process)
    val thumbnailBuilder = glide.load(thumb)
    this?.let {
        glide.load(mUrl)
            .apply(option)
            .thumbnail(thumbnailBuilder)
            .into(this)
    }
}

fun checkUrl(string: String?, process: String = ""): String? {
    val s = when {
        string == null -> null
        string.startsWith("http") -> string
        string.startsWith("/admin") -> PropertyUtils.getPropertiesByName(PropertyUtils.BASE_API_URL) + string + process
        string.startsWith("/poster") -> PropertyUtils.getPropertiesByName(PropertyUtils.BASE_API_URL) + string
        string.startsWith("/") -> string
        else -> PropertyUtils.getPropertiesByName(PropertyUtils.BASE_API_URL) + string + process
    }
    return s
}

private fun processImg(img: ImageView?, shouldProcess: Boolean): String {
    if (!shouldProcess) {
        return ""
    }
    if (img == null) {
        return ""
    }
    val w = img.width
    val h = img.height
    return if (w > 1 && h > 1) {
        if (w >= h) {
            "${PropertyUtils.getPropertiesByName(PropertyUtils.RESIZE_W)}$w"
        } else {
            "${PropertyUtils.getPropertiesByName(PropertyUtils.RESIZE_H)}$w"
        }
    } else if (w > 1 && h <= 1) {
        "${PropertyUtils.getPropertiesByName(PropertyUtils.RESIZE_W)}$w"
    } else if (h > 1 && w <= 1) {
        "${PropertyUtils.getPropertiesByName(PropertyUtils.RESIZE_H)}$w"
    } else {
        ""
    }
}

