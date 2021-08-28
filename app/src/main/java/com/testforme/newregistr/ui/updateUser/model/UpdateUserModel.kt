package com.testforme.newregistr.ui.bottomSheets.UpdateUser.model

import com.google.gson.Gson
import com.testforme.newregistr.ui.bottomSheets.UpdateUser.stuff.UpdateApi
import com.testforme.newregistr.ui.bottomSheets.UpdateUser.stuff.UpdateUserContract
import com.testforme.newregistr.retrofit.RetrofitApi
import com.testforme.newregistr.objects.User
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class UpdateUserModel : UpdateUserContract.Model {
    override fun updateUserInfo(user: User, onFinishedListener: UpdateUserContract.Model.OnFinishedListener) {
        val updateApi = RetrofitApi.getInstance().create(UpdateApi::class.java)

        val updateQuery = updateApi.updateUser(user)
        updateQuery.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    onFinishedListener.onFinished(it)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun uploadImage(user: User, avatar: File, onFinishedListener: UpdateUserContract.Model.OnFinishedListener) {
        val uploadApi = RetrofitApi.getInstance().create(UpdateApi::class.java)

        val requestFile = avatar.asRequestBody("multipart/form-data".toMediaTypeOrNull())

        // MultipartBody.Part используется, чтобы передать имя файла
        val fileBody = MultipartBody.Part.createFormData("avatar", avatar.name, requestFile)

        val userBody = Gson().toJson(user).toRequestBody("multipart/form-data".toMediaTypeOrNull())

        val uploadQuery = uploadApi.uploadImage(userBody, fileBody)
        uploadQuery.enqueue(object : Callback<User> {
            override fun onResponse(call: Call<User>, response: Response<User>) {
                response.body()?.let {
                    onFinishedListener.onFinished(it)
                }
            }

            override fun onFailure(call: Call<User>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }
}