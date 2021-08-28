package com.testforme.newregistr.ui.bottomSheets.Auth.model

import com.testforme.newregistr.stuff.application.SharedPrefHelper
import com.testforme.newregistr.ui.auth.stuff.AuthApi
import com.testforme.newregistr.Auth.AuthContract
import com.testforme.newregistr.retrofit.LoginBody
import com.testforme.newregistr.retrofit.RetrofitApi
import com.testforme.newregistr.stuff.UserHelperImpl
import com.google.gson.Gson
import com.testforme.newregistr.ui.auth.stuff.AuthContract
import com.testforme.newregistr.ui.bottomSheets.Auth.AuthResponse
import com.testforme.newregistr.retrofit.LoginBody
import com.testforme.newregistr.ui.bottomSheets.Auth.AuthApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthModel : AuthContract.Model {

    override fun authWithPass(onFinishedListener: AuthContract.Model.OnFinishedListener, email: String, password: String) {
        val authApi = RetrofitApi.getInstance().create(AuthApi::class.java)

        val loginBody = LoginBody(email, password)

        val authQuery = authApi.authWithPass(loginBody)

        authQuery.enqueue(object : Callback<AuthResponse>{
            override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                response.body()?.let { body ->
                    onFinishedListener.onFinished(body.type, body.user, body.message)
                } ?: onFinishedListener.onFinished()
            }

            override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                onFinishedListener.onFailure(t)
            }
        })
    }

    override fun authWithToken(onFinishedListener: AuthContract.Model.OnFinishedListener) {
        val userA = SharedPrefHelper.getInstance().getUserObject()
        userA?.let { user ->
            val authApi = RetrofitApi.getInstance().create(AuthApi::class.java)
            val authQuery = authApi.authWithToken(LoginBody(user.email, user.token))
            authQuery.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(call: Call<AuthResponse>, response: Response<AuthResponse>) {
                    response.body()?.let { body ->
                        onFinishedListener.onFinished(body.type, body.user, body.message)
                    } ?: onFinishedListener.onFinished()
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    onFinishedListener.onFailure(t)
                }
            })

        }
    }

    override fun saveNewInfo(userHelper: UserHelperImpl) {
        SharedPrefHelper.getInstance().writePreferences("user", Gson().toJson(userHelper.user))
    }
}

