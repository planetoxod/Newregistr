package com.testforme.newregistr.ui.registration.stuff

import com.testforme.newregistr.retrofit.ResponseBody
import com.testforme.newregistr.objects.ZygoteUser
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface RegistrationApi {

    @POST("registration.php")
    fun singUp(@Body user: ZygoteUser): Call<List<ResponseBody>>

}