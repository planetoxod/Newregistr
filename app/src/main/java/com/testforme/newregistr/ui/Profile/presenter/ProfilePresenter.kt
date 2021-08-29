package com.testforme.newregistr.ui.botNavView.Profile.presenter

import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import com.testforme.newregistr.ui.Profile.stuff.ProfileInterfaces
import com.testforme.newregistr.ui.botNavView.Profile.model.ProfileModel
import com.testforme.newregistr.ui.bottomSheets.Auth.AuthContract
import com.testforme.newregistr.ui.bottomSheets.Auth.model.AuthModel
import com.testforme.newregistr.stuff.baseMVP.BasePresenterImpl
import com.testforme.newregistr.objects.AuthResponseType
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.objects.ViewType
import com.testforme.newregistr.stuff.UserHelperImpl

class ProfilePresenter(private val fragmentManager: FragmentManager,
                       val model: ProfileModel)
    : BasePresenterImpl<ProfileInterfaces.View>(), ProfileInterfaces.Presenter {

    override fun viewIsReady(userHelper: UserHelperImpl) {
//        mView?.showProgressDialog()
        userHelper.user = SharedPrefHelper.getInstance().getUserObject()
        AuthModel().authWithToken(object : AuthContract.Model.OnFinishedListener {
            override fun onFinished(type: AuthResponseType, user: User?, message: String) {
                mView?.hideProgressDialog()
                when (type) {
                    AuthResponseType.Success -> {
                        user?.let {
                            if (userHelper.user != it) {
                                userHelper.updateUser(it)
                            }
                        }
                    }
                    AuthResponseType.AuthError -> {

                    }
                    AuthResponseType.TokenError -> {
                        userHelper.removeUser()
                    }
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
        })
    }

    override fun viewIsReady() {

    }

    override fun updateI(user: User) {
        mView?.receiveData(user)
    }

    override fun changeI(user: User) {
        mView?.receiveData(user)
        mView?.hideView(ViewType.AuthView)
    }

    override fun removeI() {
        mView?.showView(ViewType.AuthView)
    }

    override fun auth(userHelper: UserHelperImpl) {
        model.authWithPass(fragmentManager)
    }

    override fun openEditBottomSheet(bundle: Bundle) {
        model.openEditBottomSheet(bundle, fragmentManager)
    }
}