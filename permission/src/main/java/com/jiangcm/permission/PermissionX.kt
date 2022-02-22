package com.jiangcm.permission

import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity

object PermissionX {
    /**
     * Init PermissionX to make everything prepare to work.
     *
     * @param activity An instance of FragmentActivity
     * @return PermissionCollection instance.
     */
    fun init(activity: FragmentActivity?): PermissionMediator {
        return PermissionMediator(activity!!)
    }

    /**
     * Init PermissionX to make everything prepare to work.
     *
     * @param fragment An instance of Fragment
     * @return PermissionCollection instance.
     */
    fun init(fragment: Fragment?): PermissionMediator {
        return PermissionMediator(fragment!!)
    }

    /**
     * A helper function to check a permission is granted or not.
     *
     * @param context Any context, will not be retained.
     * @param permission Specific permission name to check. e.g. [android.Manifest.permission.CAMERA].
     * @return True if this permission is granted, False otherwise.
     */
    fun isGranted(context: Context?, permission: String?): Boolean {
        return ContextCompat.checkSelfPermission(
            context!!,
            permission!!
        ) == PackageManager.PERMISSION_GRANTED
    }
}