package com.testforme.newregistr.ui.profile

import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.retrofit.ResponseBody
import retrofit2.Call
import retrofit2.http.*

interface RegistrationApi {

    @POST("signup")
    fun singUp(@HeaderMap headers: Map<String, String>, @Body user: ZygoteUser): Call<List<ResponseBody>>

}