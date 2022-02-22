package com.jiangcm.permission.request

import com.jiangcm.permission.PermissionX
import java.util.*

internal class RequestNormalPermissions internal constructor(permissionBuilder: PermissionBuilder) :
    BaseTask(permissionBuilder) {

    override fun request() {
        val requestList = ArrayList<String>()
        for (permission in pb.normalPermissions) {
            if (PermissionX.isGranted(pb.activity, permission)) {
                pb.grantedPermissions.add(permission) // already granted
            } else {
                requestList.add(permission) // still need to request
            }
        }
        if (requestList.isEmpty()) { // all permissions are granted
            finish()
            return
        }
        if (pb.explainReasonBeforeRequest && (pb.explainReasonCallback != null || pb.explainReasonCallbackWithBeforeParam != null)) {
            pb.explainReasonBeforeRequest = false
            pb.deniedPermissions.addAll(requestList)
            if (pb.explainReasonCallbackWithBeforeParam != null) {
                // callback ExplainReasonCallbackWithBeforeParam prior to ExplainReasonCallback
                pb.explainReasonCallbackWithBeforeParam?.invoke(explainScope, requestList, true)
            } else {
                pb.explainReasonCallback?.invoke(explainScope, requestList)
            }
        } else {
            // Do the request at once. Always request all permissions no matter they are already granted or not, in case user turn them off in Settings.
            pb.requestNow(pb.normalPermissions, this)
        }
    }

    /**
     * If permission is denied by user and [ExplainScope.showRequestReasonDialog] or [ForwardScope.showForwardToSettingsDialog] is called,
     * when user clicked positive button, will call this method.
     * @param permissions   permissions to request again.
     */
    override fun requestAgain(permissions: List<String>) {
        val permissionsToRequestAgain: MutableSet<String> = HashSet(pb.grantedPermissions)
        permissionsToRequestAgain.addAll(permissions)
        if (permissionsToRequestAgain.isNotEmpty()) {
            pb.requestNow(permissionsToRequestAgain, this)
        } else {
            finish()
        }
    }
}