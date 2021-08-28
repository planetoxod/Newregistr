package com.testforme.newregistr.ui.bottomSheets.Auth.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import cn.pedant.SweetAlert.SweetAlertDialog
import com.testforme.newregistr.stuff.baseMVP.BaseFragmentImpl
import com.testforme.newregistr.stuff.application.SubApplication
import com.testforme.newregistr.ui.bottomSheets.Auth.AuthContract
import com.testforme.newregistr.ui.bottomSheets.Auth.model.AuthModel
import com.testforme.newregistr.ui.bottomSheets.Auth.presenter.AuthPresenter
import com.testforme.newregistr.ui.bottomSheets.BaseBottomSheet
import com.testforme.newregistr.R
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.stuff.UserHelperImpl
import com.testforme.newregistr.ui.activities.MainActivity
import kotlinx.android.synthetic.main.auth_main.*
import kotlinx.coroutines.*

class AuthView : BaseFragmentImpl(), AuthContract.View {

    private var delayShowProgressDialog: Boolean = false

    private lateinit var presenter: AuthPresenter
    private lateinit var progressDialog: SweetAlertDialog

    private lateinit var userHelper: UserHelperImpl

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.auth_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
    }

    override fun initView(view: View) {
        userHelper = (activity?.application as SubApplication).getUserHelper()

        progressDialog = SweetAlertDialog(context)
        progressDialog.apply {
            changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
            titleText = getString(R.string.please_wait)
            setCancelable(false)
        }

        btnLogin.setOnClickListener(mOnClickListener)
        btnRegist.setOnClickListener(mOnClickListener)

        presenter = AuthPresenter(userHelper, AuthModel())
        presenter.attachView(this)
    }

    override fun setViewError(viewErrorCodeList: List<ViewErrorCodes>) {
        viewErrorCodeList.forEach {
            when (it) {
                ViewErrorCodes.LOGIN_IS_EMPTY -> loginLayout.error = getString(R.string.error_empty)
                ViewErrorCodes.PASSWORD_IS_EMPTY -> passwordLayout.error = getString(R.string.error_empty)
                ViewErrorCodes.PASSWORD_TOO_SHORT -> passwordLayout.error = getString(R.string.error_min_password_length_is, 6)
                else -> showToast(ErrorText.UnhandledError, false)
            }
        }
    }

    override fun hideViewError() {
        loginLayout.error = null
        passwordLayout.error = null
    }

    private val mOnClickListener: View.OnClickListener = View.OnClickListener { view ->
        when (view.id) {
            R.id.btnLogin -> {
                presenter.authWithPass(loginView.text.toString(), passwordView.text.toString())
            }

            R.id.btnRegist -> {
                (parentFragment as BaseBottomSheet).showRegistrationFragment()
            }
        }
    }

    override fun closeView() {
        (parentFragment as BaseBottomSheet).dismiss()
    }

    @DelicateCoroutinesApi
    override fun showProgressDialog() {
        delayShowProgressDialog=true
        GlobalScope.launch(Dispatchers.IO) {
            delay(5000)
            if (delayShowProgressDialog && !progressDialog.isShowing) {
                progressDialog.show()
            }
        }

    }

    override fun hideProgressDialog() {
        delayShowProgressDialog=false
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }


    override fun onRestoreInstanceStateI(savedInstanceState: Bundle) {

    }

    override fun onDestroy() {
        presenter.detachView()

        super.onDestroy()
    }
}