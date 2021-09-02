package com.testforme.newregistr.ui.profile

import com.google.gson.Gson
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.retrofit.LoginBody
import com.testforme.newregistr.retrofit.RetrofitApi
import com.testforme.newregistr.stuff.UserHelperImpl
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthorizationController(profileViewModelA: ProfileViewModel) {
    private lateinit var userHelper: UserHelperImpl
    private val profileViewModel=profileViewModelA

    fun authWithPass() {
        val authApi = RetrofitApi.getInstance().create(AuthApi::class.java)
        val errorList = checkStrings(profileViewModel.user)

        if (profileViewModel.user != null && errorList.isEmpty()) {
            val loginBody = profileViewModel.user.let { LoginBody(it.id, it.phone) }

            val authQuery = loginBody.let { authApi.authWithPass(it) }

            authQuery.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    response.body()?.let { body ->
                        onFinishedListener.onFinished(body)

                    } ?: onFinishedListener.onFinished()
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    onFinishedListener.onFailure(t)
                }
            })
        } else {
//        mView?.setViewError(errorList)
        }
    }


    private fun checkStrings(user: User?): List<ViewErrorCodes> {
        val result = arrayListOf<ViewErrorCodes>()

        if (user == null) result.add(ViewErrorCodes.USER_IS_EMPTY)
        else {
            with(user) {
                if (id.isBlank()) result.add(ViewErrorCodes.LOGIN_IS_EMPTY)
                when (phone.length) {
                    0 -> result.add(ViewErrorCodes.PHONE_IS_EMPTY)
                    in (1..6) -> result.add(ViewErrorCodes.PHONE_TOO_SHORT)
                    else -> {
                    }
                }

            }
        }

        return result
    }

    private var onFinishedListener = object : OnFinishedListener {
        override fun onFinished(authResponse: AuthResponse) {
            //  mView?.hideProgressDialog()

            if (authResponse.token != "") {
                val user = userHelper.user
                if (user != null) {
                    user.token = authResponse.token
                    user.let {
                        SharedPrefHelper.getInstance()
                            .writePreferences("user", Gson().toJson(it, User::class.java))
                        userHelper.user = it
                        profileViewModel.showToast(ErrorText.Success)
                        //  mView?.closeView()
                    }
                }

            } else profileViewModel.showToast(ErrorText.UnhandledError)

        }

        override fun onFinished() {
//                mView?.hideProgressDialog()
//                mView?.showToast(ErrorText.UnhandledError)
        }

        override fun onFailure(t: Throwable) {
//            mView?.hideProgressDialog()
//                mView?.showToast(ErrorText.LoadingError)
        }

    }

}