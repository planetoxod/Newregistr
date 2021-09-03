package com.testforme.newregistr.ui.profile

import com.google.gson.annotations.SerializedName
import com.testforme.newregistr.objects.AuthResponseType
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.retrofit.AuthResponse
import com.testforme.newregistr.retrofit.LoginBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @POST("signin")
    fun authWithPass(@HeaderMap headers: Map<String, String>, @Body loginBody: LoginBody): Call<AuthResponse>

}

