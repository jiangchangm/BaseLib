package com.jiangcm.permission.request

interface ChainTask {
    /**
     * Get the ExplainScope for showing RequestReasonDialog.
     * @return Instance of ExplainScope.
     */
    var explainScope: ExplainScope

    /**
     * Get the ForwardScope for showing ForwardToSettingsDialog.
     * @return Instance of ForwardScope.
     */
    var forwardScope: ForwardScope

    /**
     * Do the request logic.
     */
    fun request()

    /**
     * Request permissions again when user denied.
     * @param permissions
     * Permissions to request again.
     */
    fun requestAgain(permissions: List<String>)

    /**
     * Finish this task and notify the request result.
     */
    fun finish()
}