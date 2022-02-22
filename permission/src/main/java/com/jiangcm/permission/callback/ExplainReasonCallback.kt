package com.jiangcm.permission.callback

import com.jiangcm.permission.request.ExplainScope

interface ExplainReasonCallback {
    /**
     * Called when you should explain why you need these permissions.
     * @param scope
     * Scope to show rationale dialog.
     * @param deniedList
     * Permissions that you should explain.
     */
    fun onExplainReason(scope: ExplainScope, deniedList: List<String?>)
}