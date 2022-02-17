package com.jiangcm.permission.request

import com.jiangcm.permission.dialog.RationaleDialog
import com.jiangcm.permission.dialog.RationaleDialogFragment
import kotlin.jvm.JvmOverloads

class ExplainScope internal constructor(
    private val pb: PermissionBuilder,
    private val chainTask: ChainTask
) {
    /**
     * Show a rationale dialog to explain to user why you need these permissions.
     * @param permissions
     * Permissions that to request.
     * @param message
     * Message that show to user.
     * @param positiveText
     * Text on the positive button. When user click, PermissionX will request permissions again.
     * @param negativeText
     * Text on the negative button. When user click, PermissionX will finish request.
     */
    @JvmOverloads
    fun showRequestReasonDialog(permissions: List<String>, message: String, positiveText: String, negativeText: String? = null) {
        pb.showHandlePermissionDialog(chainTask, true, permissions, message, positiveText, negativeText)
    }

    /**
     * Show a rationale dialog to explain to user why you need these permissions.
     * @param dialog
     * Dialog to explain to user why these permissions are necessary.
     */
    fun showRequestReasonDialog(dialog: RationaleDialog) {
        pb.showHandlePermissionDialog(chainTask, true, dialog)
    }

    /**
     * Show a rationale dialog to explain to user why you need these permissions.
     * @param dialogFragment
     * DialogFragment to explain to user why these permissions are necessary.
     */
    fun showRequestReasonDialog(dialogFragment: RationaleDialogFragment) {
        pb.showHandlePermissionDialog(chainTask, true, dialogFragment)
    }
}