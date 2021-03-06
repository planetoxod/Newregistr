package com.testforme.newregistr.retrofit

import com.google.gson.annotations.SerializedName

data class ResponseBody(
        @SerializedName("responseCode")
        val code: ResponseCodes,
        @SerializedName("responseMessage")
        val message: String)
