package com.testforme.newregistr.ui.bottomSheets.UpdateUser.stuff

import com.testforme.newregistr.stuff.baseMVP.BaseModel
import com.testforme.newregistr.stuff.baseMVP.BasePresenter
import com.testforme.newregistr.stuff.baseMVP.BaseView
import com.testforme.newregistr.objects.User
import java.io.File

interface UpdateUserContract {

    interface Model : BaseModel {

        fun updateUserInfo(user: User, onFinishedListener: OnFinishedListener)

        fun uploadImage(user: User, avatar: File, onFinishedListener: OnFinishedListener)

        interface OnFinishedListener : BaseModel.OnFinishedListener {

            fun onFinished(user: User)
        }

    }

    interface Presenter : BasePresenter<View> {

        fun updateUserInfo(user: User)

        fun uploadImage(user: User, avatar: File)

    }

    interface View : BaseView {

        fun showIconProgressBar()

        fun cancelIconProgressBar()

        fun hideIconProgressBar()

        fun updateUser(user: User)

        fun dismissI()
    }
}