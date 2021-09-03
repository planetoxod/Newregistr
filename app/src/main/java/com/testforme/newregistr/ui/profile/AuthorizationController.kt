package com.testforme.newregistr.ui.profile

import com.google.gson.Gson
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.retrofit.AuthResponse
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
        val errorList = checkStrings(profileViewModel.user)
        if (profileViewModel.user != null && errorList.isEmpty()) {
            val loginBody = profileViewModel.user.let { LoginBody(it.id, it.phone) }

            val authApi = RetrofitApi.getInstance().create(AuthApi::class.java)

            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            val idDevice:Long=System.currentTimeMillis()
            headers["X-APP-ID"] = idDevice.toString()

            val authQuery = authApi.authWithPass(headers,loginBody)

            authQuery.enqueue(object : Callback<AuthResponse> {
                override fun onResponse(
                    call: Call<AuthResponse>,
                    response: Response<AuthResponse>
                ) {
                    response.body()?.let { it ->
                        //  mView?.hideProgressDialog()

                        if (it.token != "") {
                            val user = userHelper.user
                            if (user != null) {
                                user.token = it.token
                                user.let {
                                    SharedPrefHelper.getInstance()
                                        .writePreferences("user", Gson().toJson(it, User::class.java))
                                    userHelper.user = it
                                    profileViewModel.showToast(ErrorText.Success)
                                    //  mView?.closeView()
                                }
                            }

                        } else profileViewModel.showToast(ErrorText.UnhandledError)

                    } ?: run {
                        //                mView?.hideProgressDialog()
//                mView?.showToast(ErrorText.UnhandledError)

                    }
                }

                override fun onFailure(call: Call<AuthResponse>, t: Throwable) {
                    //            mView?.hideProgressDialog()
//                mView?.showToast(ErrorText.LoadingError)
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

}