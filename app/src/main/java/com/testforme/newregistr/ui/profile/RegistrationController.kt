package com.testforme.newregistr.ui.profile

import com.google.gson.Gson
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.retrofit.ResponseBody
import com.testforme.newregistr.retrofit.ResponseCodes
import com.testforme.newregistr.retrofit.RetrofitApi
import com.testforme.newregistr.stuff.UserHelperImpl
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationController(profileViewModelA: ProfileViewModel) {
    private lateinit var userHelper: UserHelperImpl
    private val profileViewModel = profileViewModelA

    fun register() {

        //  mView?.hideViewError()

        if (profileViewModel.user != null ) {
            val errorList = checkStrings(profileViewModel.user)
            if (errorList.isEmpty()) {

                //  mView?.showProgressDialog()

                zygoteUser = ZygoteUser(
                    profileViewModel.user.phone, profileViewModel.user.name,
                    profileViewModel.user.email, profileViewModel.user.birthday,
                    profileViewModel.user.avatar
                )

                val regApi = RetrofitApi.getInstance().create(RegistrationApi::class.java)

                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                val idDevice: Long = System.currentTimeMillis()
                headers["X-APP-ID"] = idDevice.toString()

                val regQuery = regApi.singUp(headers, zygoteUser)
                regQuery.enqueue(object : Callback<List<ResponseBody>> {
                    override fun onResponse(
                        call: Call<List<ResponseBody>>,
                        response: Response<List<ResponseBody>>
                    ) {
                        if (response.body() != null) {
                            response.body()!!.forEach {
                                when (it.code) {
                                    ResponseCodes.EMAIL_ERROR -> {
                                    }//mView?.setServerError(it.code)

                                    ResponseCodes.LOGIN_ERROR -> {
                                        // mView?.setServerError(it.code)
                                        // mView?.auth()
                                    }

                                    ResponseCodes.SUCCESS -> {
                                    } //mView?.auth()
                                }
                            }
                            //  mView?.hideProgressDialog()
                        }

                    }

                    override fun onFailure(call: Call<List<ResponseBody>>, t: Throwable) {
                        //  mView?.setUserData(zygoteUser)
                    }
                })
            } else {
                //  mView?.setViewError(errorList)
            }
        }else{
            val errorList = arrayListOf<ViewErrorCodes>()
            errorList.add(ViewErrorCodes.USER_IS_EMPTY)
            //  mView?.setViewError(errorList)
        }
    }

    private lateinit var zygoteUser: ZygoteUser

    override fun register(name: String, login: String, email: String, password: String) {
        mView?.hideViewError()

        val errorList = checkStrings(name, login, email, password)
        if (errorList.isEmpty()) {
            mView?.showProgressDialog()

            zygoteUser = ZygoteUser(name, login, email, password)

            model.register(zygoteUser)
        } else {
            mView?.setViewError(errorList)
        }
    }

    override fun auth(userHelperImpl: UserHelperImpl) {
        mView?.showProgressDialog()
        AuthModel().authWithPass(object : AuthContract.Model.OnFinishedListener {
            override fun onFinished(type: AuthResponseType, user: User?, message: String) {
                mView?.hideProgressDialog()
                when (type) {
                    AuthResponseType.Success -> {
                        user?.let {
                            SharedPrefHelper.getInstance()
                                .writePreferences("user", Gson().toJson(it, User::class.java))
                            userHelperImpl.user = it
                        }
                        mView?.closeView()
                    }
                    AuthResponseType.AuthError -> mView?.showToast(message)
                    AuthResponseType.TokenError -> mView?.showToast(ErrorText.UnhandledError)
                }
            }

            override fun onFinished() {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.UnhandledError)
            }

            override fun onFailure(t: Throwable) {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.LoadingError)
            }
        }, zygoteUser.email!!, zygoteUser.password!!)
    }

    private fun checkStrings(user: User): List<ViewErrorCodes> {
        val result = arrayListOf<ViewErrorCodes>()

        with(user) {
            when (phone.length) {
                0 -> result.add(ViewErrorCodes.PHONE_IS_EMPTY)
                in (1..6) -> result.add(ViewErrorCodes.PHONE_TOO_SHORT)
                else -> {
                }
            }
            if (user.name.isBlank()) result.add(ViewErrorCodes.NAME_IS_EMPTY)
            if (user.email.isBlank()) result.add(ViewErrorCodes.EMAIL_IS_EMPTY)
            if (user.birthday.isBlank()) result.add(ViewErrorCodes.BIRTHDAY_IS_EMPTY)
            if (user.avatar.isBlank()) result.add(ViewErrorCodes.AVATAR_IS_EMPTY)
        }

        return result
    }

}