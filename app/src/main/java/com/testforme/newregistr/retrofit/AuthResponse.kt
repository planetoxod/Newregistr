package com.testforme.newregistr.retrofit

import android.webkit.ConsoleMessage
import com.google.gson.annotations.SerializedName

data class AuthResponse(
    @SerializedName("token")
    val token: String,
    @SerializedName("dt_create_token")
    val dt_create_token: String,
    @SerializedName("dt_expiration_token")
    val dt_expiration_token: String,
    @SerializedName("detail")
    val detail: String,
    @SerializedName("msg")
    val message: String
)
