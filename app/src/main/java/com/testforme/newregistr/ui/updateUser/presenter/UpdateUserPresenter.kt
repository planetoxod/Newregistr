package com.testforme.newregistr.ui.bottomSheets.UpdateUser.presenter

import com.testforme.newregistr.ui.bottomSheets.UpdateUser.model.UpdateUserModel
import com.testforme.newregistr.ui.bottomSheets.UpdateUser.stuff.UpdateUserContract
import com.testforme.newregistr.stuff.baseMVP.BasePresenterImpl
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import okhttp3.ResponseBody
import java.io.File

class UpdateUserPresenter(val model: UpdateUserModel) : BasePresenterImpl<UpdateUserContract.View>(), UpdateUserContract.Presenter {

    override fun viewIsReady() {

    }

    override fun updateUserInfo(user: User) {
        mView?.showProgressDialog()
        model.updateUserInfo(user, object : UpdateUserContract.Model.OnFinishedListener  {
            override fun onFinished(user: User) {
                mView?.updateUser(user)
                mView?.hideProgressDialog()
                mView?.dismissI()
            }

            override fun onFinished() {
                mView?.hideProgressDialog()
                mView?.showToast(ErrorText.UnhandledError, false)
            }

            override fun onFailure(t: Throwable) {
                mView?.hideProgressDialog()
                mView?.showToast(t.localizedMessage ?: "UnhandledError", false)
            }
        })
    }

    override fun uploadImage(user: User, avatar: File) {
        mView?.showIconProgressBar()
        model.uploadImage(user, avatar, object : UpdateUserContract.Model.OnFinishedListener {
            override fun onFinished(user: User) {
                mView?.updateUser(user)
                mView?.hideIconProgressBar()
            }

            override fun onFinished() {
                mView?.hideIconProgressBar()
                mView?.showToast(ErrorText.UnhandledError, false)
            }

            override fun onFailure(t: Throwable) {
                mView?.hideIconProgressBar()
                mView?.showToast(t.localizedMessage ?: "UnhandledError", false)
            }
        })
    }
}