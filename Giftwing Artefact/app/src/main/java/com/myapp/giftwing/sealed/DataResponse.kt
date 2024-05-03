package com.myapp.giftwing.sealed

sealed class DataResponse<T>(
    var data: T? = null,
    var allError: com.myapp.giftwing.sealed.AllError? = null,
) {
    class Success<T>(data: T) : DataResponse<T>(data = data)
    class Error<T>(allError: com.myapp.giftwing.sealed.AllError) : DataResponse<T>(allError = allError)
}