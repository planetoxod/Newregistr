package com.testforme.newregistr.ui.auth.stuff

import com.testforme.newregistr.stuff.baseMVP.BaseModel
import com.testforme.newregistr.stuff.baseMVP.BasePresenter
import com.testforme.newregistr.stuff.baseMVP.BaseView
import com.testforme.newregistr.objects.AuthResponseType
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.stuff.UserHelperImpl

interface AuthContract {

    interface Model : BaseModel {

        fun authWithPass(onFinishedListener: OnFinishedListener, email: String, password: String)

        fun authWithToken(onFinishedListener: OnFinishedListener)

        fun saveNewInfo(userHelper: UserHelperImpl)

        interface OnFinishedListener : BaseModel.OnFinishedListener {

            fun onFinished(type: AuthResponseType, user: User?, message: String)
        }
    }

    interface Presenter : BasePresenter<View> {

        fun authWithPass(email: String, password: String)

        fun authWithToken(onFinishedListener: Model.OnFinishedListener)

    }

    interface View : BaseView {

        fun setViewError(viewErrorCodeList: List<ViewErrorCodes>)

        fun hideViewError()

        fun closeView()

    }
}