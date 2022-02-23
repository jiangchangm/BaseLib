package com.jiangcm.permission.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment

abstract class RationaleDialogFragment : DialogFragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            dismiss()
        }
    }

    /**
     * Return the instance of positive button on the DialogFragment. Your DialogFragment must have a positive button to proceed request.
     * @return The instance of positive button on the DialogFragment.
     */
    abstract val positiveButton: View

    /**
     * Return the instance of negative button on the DialogFragment.
     * If the permissions that you request are mandatory, your DialogFragment can have no negative button.
     * In this case, you can simply return null.
     * @return The instance of positive button on the DialogFragment, or null if your DialogFragment has no negative button.
     */
    abstract val negativeButton: View

    /**
     * Provide permissions to request. These permissions should be the ones that shows on your RationaleDialogFragment.
     * @return Permissions list to request.
     */
    abstract val permissionsToRequest: List<String>
}