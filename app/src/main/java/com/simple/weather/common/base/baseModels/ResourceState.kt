package com.simple.weather.common.base.baseModels

data class ResourceState<out T>(val status: Status, val data: T?, val error: String?) {

    companion object {

        fun <T> loading(data: T? = null): ResourceState<T> {
            return ResourceState(Status.LOADING, data, null)
        }

        fun <T> success(data: T?): ResourceState<T> {
            return ResourceState(Status.SUCCESS, data, null)
        }

        fun <T> error(errorMsg: String, data: T? = null): ResourceState<T> {
            return ResourceState(Status.ERROR, data, errorMsg)
        }
    }
}