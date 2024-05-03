package com.myapp.giftwing.sealed

import androidx.annotation.StringRes
import com.myapp.giftwing.R

sealed class AllError(@StringRes var title: Int, @StringRes var message: Int){
    object Network: AllError(
        title = R.string.net_conn_err_title,
        message = R.string.net_conn_err_message,
    )
    object Empty: AllError(
        title = R.string.no_avail_data_err_title,
        message = R.string.no_avail_data_err_body,
    )
    class Custom(
        title: Int,
        message: Int = R.string.unknown_err_body,
    ): AllError(
        title = title,
        message = message,
    )
    object Unknown: AllError(
        title = R.string.unknown_err_title,
        message = R.string.unknown_err_body,
    )
}
