package com.jiangcm.permission.callback;

import androidx.annotation.NonNull;

import com.jiangcm.permission.request.ExplainScope;
import com.jiangcm.permission.request.PermissionBuilder;

import java.util.List;

/**
 * Callback for {@link PermissionBuilder#onExplainRequestReason(ExplainReasonCallbackWithBeforeParam)} method.
 *
 * @author guolin
 * @since 2020/6/7
 */
public interface ExplainReasonCallbackWithBeforeParam {

    /**
     * Called when you should explain why you need these permissions.
     * @param scope
     *          Scope to show rationale dialog.
     * @param deniedList
     *          Permissions that you should explain.
     * @param beforeRequest
     *          Indicate it's before or after permission request. Work with {@link PermissionBuilder#explainReasonBeforeRequest()}
     */
    void onExplainReason(@NonNull ExplainScope scope, @NonNull List<String> deniedList, boolean beforeRequest);

}
