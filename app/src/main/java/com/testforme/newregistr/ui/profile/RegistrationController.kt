package com.testforme.newregistr.ui.profile

import com.google.gson.Gson
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.retrofit.LoginBody
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
    private val profileViewModel=profileViewModelA

    fun register() {
      //  mView?.hideViewError()

        if (errorList.isEmpty()) {
            mView?.showProgressDialog()

            user = ZygoteUser(name, login, email, password)

            model.register(user, )
        }

        val errorList = checkStrings(profileViewModel.user)
     //   val errorList = checkStrings(name, login, email, password)
        if (profileViewModel.user != null && errorList.isEmpty()) {

            val loginBody = profileViewModel.user.let { LoginBody(it.id, it.phone) }
            val regApi = RetrofitApi.getInstance().create(RegistrationApi::class.java)

            val headers = HashMap<String, String>()
            headers["Content-Type"] = "application/json"
            val idDevice:Long=System.currentTimeMillis()
            headers["X-APP-ID"] = idDevice.toString()

            val regQuery = regApi.singUp(headers,user)
            regQuery.enqueue(object : Callback<List<ResponseBody>> {
                override fun onResponse(
                    call: Call<List<ResponseBody>>,
                    response: Response<List<ResponseBody>>
                ) {
                    if (response.body() != null){
                        response.body()!!.forEach {
                            when (it.code) {
                                ResponseCodes.EMAIL_ERROR -> {}//mView?.setServerError(it.code)

                                ResponseCodes.LOGIN_ERROR ->{
                                    // mView?.setServerError(it.code)
                                    // mView?.auth()
                                }

                                ResponseCodes.SUCCESS ->{} //mView?.auth()
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

    }

    private lateinit var user: ZygoteUser

    override fun register(name: String, login: String, email: String, password: String) {
        mView?.hideViewError()

        val errorList = checkStrings(name, login, email, password)
        if (errorList.isEmpty()) {
            mView?.showProgressDialog()

            user = ZygoteUser(name, login, email, password)

            model.register(user, )
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