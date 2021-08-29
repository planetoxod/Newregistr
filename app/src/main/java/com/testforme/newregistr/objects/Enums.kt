package com.testforme.newregistr.objects

import com.google.gson.annotations.SerializedName

enum class ViewType {
    ChartView,
    EmptyView,
    AuthView
}

enum class AuthResponseType {
    @SerializedName("100")
    Success,
    @SerializedName("101")
    AuthError,
    @SerializedName("102")
    TokenError
}

enum class ErrorText {
    InvalidViewTypeError,
    LoadingError,
    UserIsNullError,
    FileNotFoundError,
    CompressError,
    CardError,
    UnhandledError
}

enum class ViewErrorCodes {
    NAME_IS_EMPTY,
    LOGIN_IS_EMPTY,
    EMAIL_IS_EMPTY,
    EMAIL_NOT_VALID,
    PASSWORD_IS_EMPTY,
    PASSWORD_TOO_SHORT
}