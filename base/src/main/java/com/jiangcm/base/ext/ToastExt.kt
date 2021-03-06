package com.jiangcm.base.ext

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.CheckResult
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.core.view.ViewCompat.setBackground
import com.jiangcm.base.R
import com.jiangcm.base.ext.ToastExt.DEFAULT_TEXT_COLOR
import com.jiangcm.base.ext.ToastExt.currentTypeface
import com.jiangcm.base.ext.ToastExt.textSize
import com.jiangcm.base.ext.ToastExt.tintIcon

var toastStr: CharSequence? = null
var toastTime: Long? = null

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence) =
    this.Tnormal(message, Toast.LENGTH_SHORT, null, false)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence,context:Context) =
    this.Tnormal(message,context, Toast.LENGTH_SHORT, null, false)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(@StringRes messageId: Int) =
    this.Tnormal(this.resources.getString(messageId), Toast.LENGTH_SHORT, null, false)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence, icon: Drawable) =
    this.Tnormal(message, Toast.LENGTH_SHORT, icon, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(@StringRes messageId: Int, icon: Drawable) =
    this.Tnormal(this.resources.getString(messageId), Toast.LENGTH_SHORT, icon, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence, duration: Int) =
    this.Tnormal(message, duration, null, false)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(@StringRes messageId: Int, duration: Int) =
    this.Tnormal(this.resources.getString(messageId), duration, null, false)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence, duration: Int,
                    icon: Drawable
) =
    this.Tnormal(message, duration, icon, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(@StringRes messageId: Int, duration: Int,
                    icon: Drawable
) =
    this.Tnormal(this.resources.getString(messageId), duration, icon, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence, duration: Int,
                    icon: Drawable?, withIcon: Boolean) {
    custom(this, message, icon, ToastExt.NORMAL_COLOR, duration, withIcon, true)
}

@Suppress("SpellCheckingInspection", "unused")
fun Context.Tnormal(message: CharSequence,context:Context, duration: Int,
                    icon: Drawable?, withIcon: Boolean) {
    custom(context, message, icon, ToastExt.NORMAL_COLOR, duration, withIcon, true)
}

@Suppress("SpellCheckingInspection", "unused")
fun Context.Twarning(message: CharSequence) =
    this.Twarning(message, Toast.LENGTH_SHORT, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Twarning(@StringRes messageId: Int) =
    this.Twarning(this.resources.getString(messageId), Toast.LENGTH_SHORT, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Twarning(message: CharSequence, duration: Int) =
    this.Twarning(message, duration, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Twarning(@StringRes messageId: Int, duration: Int) =
    this.Twarning(this.resources.getString(messageId), duration, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.Twarning(message: CharSequence,
                     duration: Int,
                     withIcon: Boolean) {
    custom(message, R.drawable.ic_error_outline_white_48dp,
        ToastExt.WARNING_COLOR, duration, withIcon, true)
}

@Suppress("SpellCheckingInspection", "unused")
@JvmOverloads
fun Context.Tinfo(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT,
    withIcon: Boolean = true) = custom(message, R.drawable.ic_info_outline_white_48dp,
    ToastExt.INFO_COLOR,
    duration, withIcon, true)

@Suppress("SpellCheckingInspection", "unused")
@JvmOverloads
fun Context.Tsuccess(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT,
    withIcon: Boolean = true) = custom(message, R.drawable.ic_check_white_48dp,
    ToastExt.SUCCESS_COLOR, duration, withIcon, true)

@Suppress("SpellCheckingInspection", "unused")
@JvmOverloads
fun Context.Terror(
    message: CharSequence,
    duration: Int = Toast.LENGTH_SHORT,
    withIcon: Boolean = true) = custom(message, R.drawable.ic_clear_white_48dp,
    ToastExt.ERROR_COLOR, duration, withIcon, true)

@Suppress("SpellCheckingInspection", "unused")
fun Context.custom(message: CharSequence, icon: Drawable,
                   duration: Int, withIcon: Boolean) = custom(this, message, icon, -1, duration, withIcon, false)

@Suppress("SpellCheckingInspection", "unused")
fun Context.custom(message: CharSequence, @DrawableRes iconRes: Int,
                   @ColorInt tintColor: Int, duration: Int,
                   withIcon: Boolean, shouldTint: Boolean) {
    custom(this, message, this.getDrawableBySdk(iconRes),
        tintColor, duration, withIcon, shouldTint)
}

object ToastExt {
    var DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
    var ERROR_COLOR = Color.parseColor("#D50000")
    var INFO_COLOR = Color.parseColor("#3F51B5")
    var SUCCESS_COLOR = Color.parseColor("#388E3C")
    var WARNING_COLOR = Color.parseColor("#FFA900")
    var NORMAL_COLOR = Color.parseColor("#353A3E")

    val LOADED_TOAST_TYPEFACE = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
    var currentTypeface = LOADED_TOAST_TYPEFACE
    var textSize = 16 // in SP

    var tintIcon = true
}

/**
 * ??? Activity ???Context ??? ??????????????????????????????????????? ?????????Context ?????????
 */
private fun custom(context: Context, message: CharSequence, inIcon: Drawable?,
                   @ColorInt tintColor: Int, duration: Int,
                   withIcon: Boolean, shouldTint: Boolean) {
    if (context is Activity) {
        context.runOnUiThread {
            customImpl(context, message, inIcon, tintColor, duration, withIcon, shouldTint)?.show()
        }
    } else {
        customImpl(context, message, inIcon, tintColor, duration, withIcon, shouldTint)?.show()
    }
}

@SuppressLint("InflateParams")
private fun customImpl(context: Context, message: CharSequence, inIcon: Drawable?,
                       @ColorInt tintColor: Int, duration: Int,
                       withIcon: Boolean, shouldTint: Boolean): Toast? {
    if (message.isBlank()) {
        return null
    }
    if (toastTime != null && message == toastStr) {
        if (System.currentTimeMillis() - (toastTime ?: 0L) <= 3000) {
            return null
        }
    }
    toastTime = System.currentTimeMillis()
    toastStr = message

    var icon = inIcon
    val currentToast = Toast(context)
    val toastLayout = (context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater)
        .inflate(R.layout.toast_layout, null)
    val toastIcon: ImageView = toastLayout.findViewById(R.id.toast_icon)
    val toastTextView: TextView = toastLayout.findViewById(R.id.toast_text)
    val drawableFrame: Drawable? = if (shouldTint) {
        context.tint9PatchDrawableFrame(tintColor)
    } else {
        context.getDrawableBySdk(R.drawable.toast_frame)
    }
    setBackground(toastLayout, drawableFrame)

    if (withIcon) {
        if (icon == null)
            throw IllegalArgumentException("Avoid passing 'icon' as null if 'withIcon' is set to true")
        if (tintIcon)
            icon = getTintIcon(icon, DEFAULT_TEXT_COLOR)
        setBackground(toastIcon, icon)
    } else {
        toastIcon.visibility = View.GONE
    }

    toastTextView.setTextColor(DEFAULT_TEXT_COLOR)
    toastTextView.text = message
    toastTextView.typeface = currentTypeface
    toastTextView.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize.toFloat())

    currentToast.view = toastLayout
    currentToast.duration = duration
    return currentToast
}

@Suppress("SpellCheckingInspection", "unused")
class ToastConfig private constructor() {
    @ColorInt
    private var DEFAULT_TEXT_COLOR = ToastExt.DEFAULT_TEXT_COLOR
    @ColorInt
    private var ERROR_COLOR = ToastExt.ERROR_COLOR
    @ColorInt
    private var INFO_COLOR = ToastExt.INFO_COLOR
    @ColorInt
    private var SUCCESS_COLOR = ToastExt.SUCCESS_COLOR
    @ColorInt
    private var WARNING_COLOR = ToastExt.WARNING_COLOR

    private var typeface = ToastExt.currentTypeface
    private var textSize = ToastExt.textSize

    private var tintIcon = ToastExt.tintIcon

    @CheckResult
    fun setTextColor(@ColorInt textColor: Int): ToastConfig {
        DEFAULT_TEXT_COLOR = textColor
        return this
    }

    @CheckResult
    fun setErrorColor(@ColorInt errorColor: Int): ToastConfig {
        ERROR_COLOR = errorColor
        return this
    }

    @CheckResult
    fun setInfoColor(@ColorInt infoColor: Int): ToastConfig {
        INFO_COLOR = infoColor
        return this
    }

    @CheckResult
    fun setSuccessColor(@ColorInt successColor: Int): ToastConfig {
        SUCCESS_COLOR = successColor
        return this
    }

    @CheckResult
    fun setWarningColor(@ColorInt warningColor: Int): ToastConfig {
        WARNING_COLOR = warningColor
        return this
    }

    @CheckResult
    fun setToastTypeface(typeface: Typeface): ToastConfig {
        this.typeface = typeface
        return this
    }

    @CheckResult
    fun setTextSize(sizeInSp: Int): ToastConfig {
        this.textSize = sizeInSp
        return this
    }

    @CheckResult
    fun tintIcon(tintIcon: Boolean): ToastConfig {
        this.tintIcon = tintIcon
        return this
    }

    fun apply() {
        ToastExt.DEFAULT_TEXT_COLOR = DEFAULT_TEXT_COLOR
        ToastExt.ERROR_COLOR = ERROR_COLOR
        ToastExt.INFO_COLOR = INFO_COLOR
        ToastExt.SUCCESS_COLOR = SUCCESS_COLOR
        ToastExt.WARNING_COLOR = WARNING_COLOR
        ToastExt.currentTypeface = typeface
        ToastExt.textSize = textSize
        ToastExt.tintIcon = tintIcon
    }

    companion object {

        val instance: ToastConfig
            @CheckResult
            get() = ToastConfig()

        fun reset() {
            ToastExt.DEFAULT_TEXT_COLOR = Color.parseColor("#FFFFFF")
            ToastExt.ERROR_COLOR = Color.parseColor("#D50000")
            ToastExt.INFO_COLOR = Color.parseColor("#3F51B5")
            ToastExt.SUCCESS_COLOR = Color.parseColor("#388E3C")
            ToastExt.WARNING_COLOR = Color.parseColor("#FFA900")
            ToastExt.currentTypeface = Typeface.create("sans-serif-condensed", Typeface.NORMAL)
            ToastExt.textSize = 16
            ToastExt.tintIcon = true
        }
    }
}