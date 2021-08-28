package com.testforme.newregistr.ui.registration.stuff

import com.facebook.AccessToken
import com.testforme.newregistr.retrofit.ResponseBody
import com.testforme.newregistr.retrofit.ResponseCodes
import com.testforme.newregistr.stuff.baseMVP.BaseModel
import com.testforme.newregistr.stuff.baseMVP.BasePresenter
import com.testforme.newregistr.stuff.baseMVP.BaseView
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.stuff.UserHelperImpl
import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.stuff.baseMVP.BaseModel
import com.testforme.newregistr.stuff.UserHelperImpl

interface RegistrationContract {

    interface Model : BaseModel {

        fun getUserFacebookInfo(accessToken: AccessToken, onFinishedListener: OnFinishedListener)

        fun register(user: ZygoteUser, onFinishedListener: OnFinishedListener)

        interface OnFinishedListener : BaseModel.OnFinishedListener {

            fun onFinished(responseList: List<ResponseBody>)

            fun onFinished(zygoteUser: ZygoteUser)

        }

    }

    interface Presenter : BasePresenter<View> {

        fun register(name: String, login: String, email: String, password: String)

        fun auth(userHelperImpl: UserHelperImpl)

    }

    interface View : BaseView {
        fun setUserDataFromFacebook(zygoteUser: ZygoteUser)

        fun setUserData(zygoteUser: ZygoteUser)

        fun setServerError(responseCode: ResponseCodes)

        fun setViewError(viewErrorCodeList: List<ViewErrorCodes>)

        fun hideViewError()

        fun initFacebook()

        fun auth()

        fun closeView()

    }
}
