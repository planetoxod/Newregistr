package com.your_teachers.trafficrules.retrofit

import com.google.gson.annotations.SerializedName

data class ResponseBody(
        @SerializedName("responseCode")
        val code: ResponseCodes,
        @SerializedName("responseMessage")
        val message: String)
