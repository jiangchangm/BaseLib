package com.jiangcm.permission.callback;

import androidx.annotation.NonNull;

import com.jiangcm.permission.request.PermissionBuilder;

import java.util.List;

/**
 * Callback for {@link PermissionBuilder#request(RequestCallback)} method.
 *
 * @author guolin
 * @since 2020/6/7
 */
public interface RequestCallback {

    /**
     * Callback for the request result.
     * @param allGranted
     *          Indicate if all permissions that are granted.
     * @param grantedList
     *          All permissions that granted by user.
     * @param deniedList
     *          All permissions that denied by user.
     */
    void onResult(boolean allGranted, @NonNull List<String> grantedList, @NonNull List<String> deniedList);

}
