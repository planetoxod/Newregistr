package com.testforme.newregistr.retrofit

import com.google.gson.annotations.SerializedName

enum class ResponseCodes(val code: Int) {
    @SerializedName("1")
    SUCCESS(1),
    @SerializedName("101")
    LOGIN_ERROR(101),
    @SerializedName("102")
    EMAIL_ERROR(102),

}