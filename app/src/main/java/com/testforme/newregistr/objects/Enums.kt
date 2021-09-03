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
    UnhandledError,
    Success,
    NoError
}

enum class ViewErrorCodes {
    NAME_IS_EMPTY,
    USER_IS_EMPTY,
    UUID_IS_EMPTY,
    EMAIL_IS_EMPTY,
    EMAIL_NOT_VALID,
    PHONE_IS_EMPTY,
    BIRTHDAY_IS_EMPTY,
    AVATAR_IS_EMPTY,
    PHONE_TOO_SHORT

}