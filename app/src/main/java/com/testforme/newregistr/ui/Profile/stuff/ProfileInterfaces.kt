package com.testforme.newregistr.ui.Profile.stuff

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.testforme.newregistr.stuff.baseMVP.BaseModel
import com.testforme.newregistr.stuff.baseMVP.BasePresenter
import com.testforme.newregistr.stuff.baseMVP.BaseView
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewType
import com.testforme.newregistr.stuff.UserHelperImpl

interface ProfileInterfaces {

    interface Model : BaseModel {

        fun authWithPass(manager: FragmentManager)

        fun openEditBottomSheet(bundle: Bundle, manager: FragmentManager)

        interface OnFinishedListener : BaseModel.OnFinishedListener

    }

    interface Presenter : BasePresenter<View> {

        fun viewIsReady(userHelper: UserHelperImpl)

        fun updateI(user: User)

        fun changeI(user: User)

        fun removeI()

        fun auth(userHelper: UserHelperImpl)

        fun openEditBottomSheet(bundle: Bundle)

    }

    interface View : BaseView {

        fun receiveData(user: User)

        fun showView(viewType: ViewType)

        fun hideView(viewType: ViewType)

    }
}