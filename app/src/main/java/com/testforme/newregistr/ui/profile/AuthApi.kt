package com.testforme.newregistr.ui.profile

import com.google.gson.annotations.SerializedName
import com.testforme.newregistr.objects.AuthResponseType
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.retrofit.AuthResponse
import com.testforme.newregistr.retrofit.LoginBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {
    @Headers("Content-Type: application/json")
    @POST("signin")
    fun authWithPass(@Body loginBody: LoginBody): Call<AuthResponse>

//    @POST("auth.php?type=token")
//    fun authWithToken(@Body loginBody: LoginBody): Call<AuthResponse>
}

