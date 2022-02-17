package com.jiangcm.permission.callback;

import androidx.annotation.NonNull;

import com.jiangcm.permission.request.ForwardScope;
import com.jiangcm.permission.request.PermissionBuilder;

import java.util.List;

/**
 * Callback for {@link PermissionBuilder#onForwardToSettings(ForwardToSettingsCallback)} method.
 *
 * @author guolin
 * @since 2020/6/7
 */
public interface ForwardToSettingsCallback {

    /**
     * Called when you should tell user to allow these permissions in settings.
     * @param scope
     *          Scope to show rationale dialog.
     * @param deniedList
     *          Permissions that should allow in settings.
     */
    void onForwardToSettings(@NonNull ForwardScope scope, @NonNull List<String> deniedList);

}