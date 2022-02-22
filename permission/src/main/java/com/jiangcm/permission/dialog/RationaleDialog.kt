package com.jiangcm.permission.dialog

import android.app.Dialog
import android.content.Context
import android.content.DialogInterface
import android.view.View

abstract class RationaleDialog : Dialog {
    constructor(context: Context) : super(context) {}
    constructor(context: Context, themeResId: Int) : super(context, themeResId) {}
    protected constructor(
        context: Context,
        cancelable: Boolean,
        cancelListener: DialogInterface.OnCancelListener?
    ) : super(context, cancelable, cancelListener) {
    }

    /**
     * Return the instance of positive button on the dialog. Your dialog must have a positive button to proceed request.
     * @return The instance of positive button on the dialog.
     */
    abstract val positiveButton: View

    /**
     * Return the instance of negative button on the dialog.
     * If the permissions that you request are mandatory, your dialog can have no negative button.
     * In this case, you can simply return null.
     * @return The instance of positive button on the dialog, or null if your dialog has no negative button.
     */
    abstract val negativeButton: View?

    /**
     * Provide permissions to request. These permissions should be the ones that shows on your rationale dialog.
     * @return Permissions list to request.
     */
    abstract val permissionsToRequest: List<String>
}