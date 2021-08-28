package com.testforme.newregistr.ui.bottomSheets.Auth

import com.google.gson.annotations.SerializedName
import com.testforme.newregistr.objects.AuthResponseType
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.retrofit.LoginBody
import retrofit2.Call
import retrofit2.http.*

interface AuthApi {

    @POST("auth.php?type=auth")
    fun authWithPass(@Body loginBody: LoginBody): Call<AuthResponse>

    @POST("auth.php?type=token")
    fun authWithToken(@Body loginBody: LoginBody): Call<AuthResponse>
}

class AuthResponse(
    @SerializedName("type")
    val type: AuthResponseType,
    @SerializedName("user")
    val user: User?,
    @SerializedName("message")
    val message: String
)
