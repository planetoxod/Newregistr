package com.testforme.newregistr.ui.bottomSheets.UpdateUser.stuff

import com.testforme.newregistr.objects.User
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface UpdateApi {

    @POST("updateUserInfo.php")
    fun updateUser(@Body user: User): Call<User>

    @Multipart
    @POST("uploadImage.php")
    fun uploadImage(
            //RequestBody is no need, enough User,
            @Part("user") user: RequestBody,
            @Part file: MultipartBody.Part
    ): Call<User>
}
