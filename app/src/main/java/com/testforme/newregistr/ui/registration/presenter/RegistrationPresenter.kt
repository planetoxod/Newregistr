package com.testforme.newregistr.ui.bottomSheets.Registration.presenter

import com.facebook.AccessToken
import com.google.gson.Gson
import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import com.testforme.newregistr.ui.bottomSheets.Auth.AuthContract
import com.testforme.newregistr.ui.bottomSheets.Auth.model.AuthModel
import com.testforme.newregistr.ui.registration.stuff.RegistrationContract
import com.testforme.newregistr.ui.bottomSheets.Registration.model.RegistrationModel
import com.testforme.newregistr.retrofit.ResponseBody
import com.testforme.newregistr.retrofit.ResponseCodes
import com.testforme.newregistr.stuff.baseMVP.BasePresenterImpl
import com.testforme.newregistr.objects.*
import com.testforme.newregistr.stuff.UserHelperImpl

class RegistrationPresenter(val model: RegistrationModel) : BasePresenterImpl<RegistrationContract.View>(), RegistrationContract.Presenter {

    private lateinit var user: ZygoteUser

    override fun viewIsReady() {

    }

    override fun register(name: String, login: String, email: String, password: String) {
        mView?.hideViewError()

        val errorList = checkStrings(name, login, email, password)
        if (errorList.isEmpty()) {
            mView?.showProgressDialog()

            user = ZygoteUser(name, login, email, password)

            model.register(user, object : RegistrationContract.Model.OnFinishedListener {
                override fun onFinished(responseList: List<ResponseBody>) {
                    responseList.forEach {
                        when (it.code) {
                            ResponseCodes.EMAIL_ERROR -> mView?.setServerError(it.code)

                            ResponseCodes.LOGIN_ERROR ->{
                                mView?.setServerError(it.code)
                                mView?.auth()
                            }

                            ResponseCodes.SUCCESS -> mView?.auth()
                        }
                    }
                    mView?.hideProgressDialog()
                }

                override fun onFinished(zygoteUser: ZygoteUser) {
                    mView?.setUserData(zygoteUser)
                }

                override fun onFinished() {

                }

                override fun onFailure(t: Throwable) {
                    mView?.hideProgressDialog()
                    mView?.showToast(t.localizedMessage ?: "UnhandledError", false)
                }
            })
        } else {
            mView?.setViewError(errorList)
        }
    }

    override fun getInfoFromFacebook(accessToken: AccessToken) {
        mView?.showProgressDialog()
        model.getUserFacebookInfo(accessToken, object : RegistrationContract.Model.OnFinishedListener {
            override fun onFinished(responseList: List<ResponseBody>) {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.UnhandledError)
            }

            override fun onFinished(zygoteUser: ZygoteUser) {
                mView?.hideProgressDialog()
                mView?.setUserDataFromFacebook(zygoteUser)
            }

            override fun onFinished() {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.UnhandledError)
            }

            override fun onFailure(t: Throwable) {
                mView?.hideProgressDialog()
                mView?.showToast(t.localizedMessage ?: "UnhandledError")
            }
        })
    }

    override fun auth(userHelperImpl: UserHelperImpl) {
        mView?.showProgressDialog()
        AuthModel().authWithPass(object : AuthContract.Model.OnFinishedListener {
            override fun onFinished(type: AuthResponseType, user: User?, message: String) {
                mView?.hideProgressDialog()
                when (type) {
                    AuthResponseType.Success -> {
                        user?.let {
                            SharedPrefHelper.getInstance().writePreferences("user", Gson().toJson(it, User::class.java))
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
        }, user.email!!, user.password!!)
    }

    private fun checkStrings(name: String, login: String, email: String, password: String) : List<ViewErrorCodes>{
        val result = arrayListOf<ViewErrorCodes>()

        if (name.isBlank())
            result.add(ViewErrorCodes.NAME_IS_EMPTY)

        if (login.isBlank())
            result.add(ViewErrorCodes.LOGIN_IS_EMPTY)

        if (email.isBlank())
            result.add(ViewErrorCodes.EMAIL_IS_EMPTY)
        else if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches())//проверка почты
            result.add(ViewErrorCodes.EMAIL_NOT_VALID)

        if (password.isBlank())
            result.add(ViewErrorCodes.PASSWORD_IS_EMPTY)
        else if (password.length < 6)
            result.add(ViewErrorCodes.PASSWORD_TOO_SHORT)

        return result
    }
}