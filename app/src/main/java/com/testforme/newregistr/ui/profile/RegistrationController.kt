package com.testforme.newregistr.ui.profile

import android.provider.Settings
import androidx.lifecycle.MutableLiveData
import com.testforme.newregistr.retrofit.RegResponseBody
import com.testforme.newregistr.ui.profile.ProfileViewModel
import com.testforme.newregistr.ui.profile.RegistrationApi
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
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistrationController(profileViewModelA: ProfileViewModel) {
    private lateinit var userHelper: UserHelperImpl
    private val profileViewModel = profileViewModelA

    fun register() {

        //  mView?.hideViewError()

        if (profileViewModel.user != null ) {
            val errorList = checkStrings(profileViewModel.user!!)
            if (errorList.isEmpty()) {

                profileViewModel.progressDialogShow()

                val zygoteUser = ZygoteUser(
                    profileViewModel.user!!.phone, profileViewModel.user!!.name,
                    profileViewModel.user!!.email, profileViewModel.user!!.birthday,
                    profileViewModel.user!!.avatarURL
                )

                val regApi = RetrofitApi.getInstance().create(RegistrationApi::class.java)

                val headers = HashMap<String, String>()
                headers["Content-Type"] = "application/json"
                val idDevice: Long = System.currentTimeMillis()
                headers["X-APP-ID"] = idDevice.toString()

                val regQuery = regApi.singUp(headers, zygoteUser)
                regQuery.enqueue(object : Callback<RegResponseBody> {
                    override fun onResponse(
                        call: Call<RegResponseBody>,
                        response: Response<RegResponseBody>
                    ) {
                        if (response.body() != null) {

                            with(response.body()) {
                                if (this?.id =="") {
                                    //mView?.setServerError(it.code)
                                    //profileViewModel.setServerError(it.code)
                                }else{
                                     //mView?.auth()
                                }
//                                val _showProgressDialog = MutableLiveData<Boolean>(true)
//                                profileViewModel.showProgressDialog = _showProgressDialog
                            }
                        }
                        profileViewModel.progressDialogHide()
                    }

                    override fun onFailure(call: Call<RegResponseBody>, t: Throwable) {
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

        }

        return result
    }

}