package com.testforme.newregistr.ui.bottomSheets.Auth.presenter

import com.google.gson.Gson
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import com.testforme.newregistr.ui.bottomSheets.Auth.AuthContract
import com.testforme.newregistr.ui.bottomSheets.Auth.model.AuthModel
import com.testforme.newregistr.stuff.baseMVP.BasePresenterImpl
import com.testforme.newregistr.objects.AuthResponseType
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.stuff.UserHelperImpl
import okhttp3.ResponseBody

class AuthPresenter(val userHelper: UserHelperImpl,
                    val model: AuthModel) : BasePresenterImpl<AuthContract.View>(), AuthContract.Presenter {

    override fun viewIsReady() {

    }

    override fun authWithPass(email: String, password: String) {
        mView?.hideViewError()
        val errorList = checkStrings(email, password)
        if (errorList.isEmpty()) {
            mView?.showProgressDialog()
            model.authWithPass(object : AuthContract.Model.OnFinishedListener {
                override fun onFinished(type: AuthResponseType, user: User?, message: String) {
                    mView?.hideProgressDialog()
                    when (type) {
                        AuthResponseType.Success -> {
                            user?.let {
                                SharedPrefHelper.getInstance().writePreferences("user", Gson().toJson(it, User::class.java))
                                userHelper.user = it
                                mView?.closeView()
                            }
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
            }, email, password)
        } else {
            mView?.setViewError(errorList)
        }
    }

    override fun authWithToken(onFinishedListener: AuthContract.Model.OnFinishedListener) {
        mView?.showProgressDialog()
        model.authWithToken(object : AuthContract.Model.OnFinishedListener {
            override fun onFinished(type: AuthResponseType, user: User?, message: String) {
                when (type) {
                    AuthResponseType.Success -> {
                        user?.let {
                            if (userHelper.user != it)
                                userHelper.updateUser(it)
                        }
                    }
                    AuthResponseType.AuthError -> {

                    }
                    AuthResponseType.TokenError -> {
                        userHelper.removeUser()
                    }
                }
                mView?.hideProgressDialog()
            }

            override fun onFinished() {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.UnhandledError)
            }

            override fun onFailure(t: Throwable) {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.LoadingError)
            }
        })
    }

    private fun checkStrings(login: String, password: String) : List<ViewErrorCodes>{
        val result = arrayListOf<ViewErrorCodes>()

        if (login.isBlank())
            result.add(ViewErrorCodes.LOGIN_IS_EMPTY)

        if (password.isBlank())
            result.add(ViewErrorCodes.PASSWORD_IS_EMPTY)
        else if (password.length < 6)
            result.add(ViewErrorCodes.PASSWORD_TOO_SHORT)

        return result
    }
}