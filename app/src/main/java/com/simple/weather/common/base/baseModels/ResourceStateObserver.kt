package com.simple.weather.common.base.baseModels

import androidx.lifecycle.Observer
import com.simple.weather.common.base.BaseView

class ResourceStateObserver<T>(
    private val view: BaseView,
    private val onSuccess: (data: T?) -> Unit,
    private val onLoading: (isLoading: Boolean) -> Unit = DEFAULT_LOADING_ACTION,
    private val onError: (baseError: String) -> Unit = DEFAULT_ERROR_ACTION
) : Observer<ResourceState<T>> {

    companion object {
        private val DEFAULT_ERROR_ACTION: (baseError: String) -> Unit = {}
        private val DEFAULT_LOADING_ACTION: (isLoading: Boolean) -> Unit = {}
    }

    override fun onChanged(t: ResourceState<T>?) {
        // Loading state handler:
        onLoadingActionCheck(onLoading, isLoading = t?.status == Status.LOADING)

        // Error/success state handler:
        if (t?.status == Status.ERROR) {
            onErrorActionCheck(onError,
                baseError = (t.error ?: "\"Some error occurred.\\nPlease try later.\"")
            )
        } else if (t?.status == Status.SUCCESS) {
            onSuccess(t.data)
        }
    }

    private fun onLoadingActionCheck(
        onLoadingAction: (isLoading: Boolean) -> Unit = DEFAULT_LOADING_ACTION,
        isLoading: Boolean
    ) {
        if (onLoadingAction === DEFAULT_LOADING_ACTION) {
            // A lambda is NOT defined - use the default value

        } else {
            // A lambda is defined - no default value used
            onLoading(isLoading)
        }
    }

    private fun onErrorActionCheck(
        onErrorAction: (baseError: String) -> Unit = DEFAULT_ERROR_ACTION,
        baseError: String
    ) {
        if (onErrorAction === DEFAULT_ERROR_ACTION) {
            // A lambda is NOT defined - use the default value
        } else {
            // A lambda is defined - no default value used
            onError(baseError)
        }
    }
}