package com.testforme.newregistr.ui.bottomSheets.Registration.view

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import cn.pedant.SweetAlert.SweetAlertDialog
import com.facebook.*
import com.facebook.login.LoginResult
import com.google.android.gms.auth.api.Auth
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.testforme.newregistr.R
import com.testforme.newregistr.objects.ViewErrorCodes
import com.testforme.newregistr.objects.ZygoteUser
import com.testforme.newregistr.retrofit.ResponseCodes
import com.testforme.newregistr.stuff.application.SubApplication
import com.testforme.newregistr.stuff.baseMVP.BaseFragmentImpl
import com.testforme.newregistr.ui.activities.MainActivity.Companion.RC_SIGN_IN
import com.testforme.newregistr.ui.bottomSheets.BaseBottomSheet
import com.testforme.newregistr.ui.registration.stuff.RegistrationContract
import com.testforme.newregistr.ui.bottomSheets.Registration.model.RegistrationModel
import com.testforme.newregistr.ui.bottomSheets.Registration.presenter.RegistrationPresenter
import kotlinx.android.synthetic.main.register_main.*
import kotlinx.coroutines.*

class RegistrationView : BaseFragmentImpl(), RegistrationContract.View {

    private var delayShowProgressDialog: Boolean = false

    private lateinit var presenter: RegistrationPresenter
    private lateinit var progressDialog: SweetAlertDialog

    private lateinit var callbackManager: CallbackManager
    private  var authWithFacebook=false

    private  var authWithGoogle=false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.register_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initView(view)
        presenter.viewIsReady()
    }

    override fun initView(view: View) {
        progressDialog = SweetAlertDialog(context)
        progressDialog.apply {
            changeAlertType(SweetAlertDialog.PROGRESS_TYPE)
            titleText = getString(R.string.please_wait)
            setCancelable(false)
        }

        btnRegist.setOnClickListener(mOnClickListener)
        btnLogin.setOnClickListener(mOnClickListener)
        btnGoogleLogin.setOnClickListener(mOnClickListener)

        presenter = RegistrationPresenter(RegistrationModel())

        initFacebook()

        presenter.attachView(this)
    }

    override fun initFacebook() {
        callbackManager = CallbackManager.Factory.create()

        btnFacebookLogin.setPermissions(listOf("email", "public_profile"))
        btnFacebookLogin.fragment = this

        btnFacebookLogin.registerCallback(callbackManager,
                object : FacebookCallback<LoginResult> {
                    override fun onSuccess(loginResult: LoginResult) {
                        authWithFacebook=true
                        presenter.getInfoFromFacebook(loginResult.accessToken)
                    }

                    override fun onCancel() {

                    }

                    override fun onError(exception: FacebookException) {
                        showToast(exception.localizedMessage ?: "UnhandledError")
                    }
                })

        if (AccessToken.getCurrentAccessToken() != null) {
            AccessToken.getCurrentAccessToken()?.let { presenter.getInfoFromFacebook(it) }
        }
    }

    override fun auth() {
        presenter.auth(activity?.let { (it.application as SubApplication).getUserHelper() }!!)
    }

    override fun setUserData(zygoteUser: ZygoteUser) {
        zygoteUser.name?.let { nameView.setTextEx(it) }
        zygoteUser.login?.let { loginView.setTextEx(it) }
        zygoteUser.email?.let { emailView.setTextEx(it) }
    }

    override fun setServerError(responseCode: ResponseCodes) {
        when (responseCode) {
            ResponseCodes.LOGIN_ERROR -> {
                loginLayout.error = getString(R.string.error_login_taken)
            }
            ResponseCodes.EMAIL_ERROR -> {
                emailLayout.error = getString(R.string.error_email_taken)
            }
            else -> showToast("UnhandledError", false)
        }
    }

    override fun setViewError(viewErrorCodeList: List<ViewErrorCodes>) {
        viewErrorCodeList.forEach {
            when (it) {
                ViewErrorCodes.NAME_IS_EMPTY -> nameLayout.error = getString(R.string.error_empty)
                ViewErrorCodes.LOGIN_IS_EMPTY -> loginLayout.error = getString(R.string.error_empty)
                ViewErrorCodes.EMAIL_IS_EMPTY -> emailLayout.error = getString(R.string.error_empty)
                ViewErrorCodes.PASSWORD_IS_EMPTY -> passwordLayout.error = getString(R.string.error_empty)
                ViewErrorCodes.PASSWORD_TOO_SHORT -> passwordLayout.error = getString(R.string.error_min_password_length_is, 6)
                ViewErrorCodes.EMAIL_NOT_VALID -> emailLayout.error = getString(R.string.error_invalid_email)
            }
        }
    }

    override fun hideViewError() {
        nameLayout.error = null
        loginLayout.error = null
        emailLayout.error = null
        passwordLayout.error = null
    }

    override fun setUserDataFromFacebook(zygoteUser: ZygoteUser) {
      zygoteUser.name?.let { nameView.setTextEx(it) }
      zygoteUser.login?.let { loginView.setTextEx(it) }
      zygoteUser.email?.let { emailView.setTextEx(it) }
      zygoteUser.password?.let { passwordView.setTextEx(it) }
        if (authWithFacebook){
            presenter.register(nameView.text.toString(),
                loginView.text.toString(),
                emailView.text.toString(),
                passwordView.text.toString())
            collapseBottomSheet()
            authWithFacebook=false
        }
    }

    private fun collapseBottomSheet(){
        val intent = Intent(BaseBottomSheet.COMMAND_BOTTOMSHEET)
        intent.putExtra(BaseBottomSheet.ACTION_BOTTOMSHEET, BottomSheetBehavior.STATE_COLLAPSED)
        context?.sendBroadcast(intent)
    }

    private fun signInGoogle() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestEmail()
            .build()
        val mGoogleSignInClient = this.activity?.let { GoogleSignIn.getClient(it, gso) }

        val signInIntent: Intent = mGoogleSignInClient!!.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    private val mOnClickListener: View.OnClickListener = View.OnClickListener {view ->
        when (view.id) {
            R.id.btnRegist -> {
                presenter.register(
                    nameView.text.toString(),
                    loginView.text.toString(),
                    emailView.text.toString(),
                    passwordView.text.toString()
                )
            }
            R.id.btnLogin -> {
                (parentFragment as BaseBottomSheet).showAuthFragment()
            }
            R.id.btnGoogleLogin ->{
                authWithGoogle=true
                signInGoogle()
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
            if (delayShowProgressDialog) {
                progressDialog.show()
            }
        }
    }

    override fun hideProgressDialog(){
        delayShowProgressDialog=false
        progressDialog.dismiss()
    }

    override fun onRestoreInstanceStateI(savedInstanceState: Bundle) {

    }

    fun setUserDataFromGoogle(zygoteUser: ZygoteUser) {
        zygoteUser.name?.let { nameView.setTextEx(it) }
        zygoteUser.login?.let { loginView.setTextEx(it) }
        zygoteUser.email?.let { emailView.setTextEx(it) }
        zygoteUser.password?.let { passwordView.setTextEx(it) }
        if (authWithGoogle){
            presenter.register(nameView.text.toString(),
                loginView.text.toString(),
                emailView.text.toString(),
                passwordView.text.toString())
            collapseBottomSheet()
            authWithGoogle=false
        }
    }

    private fun encodeString(s:String):String{
        val letters= listOf('a','b','c','d','e','f','h')
        var result=""
        var d:Char
        for (c in s) {
            d = if (c in '0'..'5') letters[c.toString().toInt()]
            else c
            result+=d
        }
        return  result
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (authWithGoogle) {
            if (requestCode == RC_SIGN_IN) {
                val result = Auth.GoogleSignInApi.getSignInResultFromIntent(data)
                if (result!=null && result.isSuccess) {
                    val acct = result.signInAccount
                    val zygoteUser: ZygoteUser
                    if (acct !=null) {
                        if (acct.givenName != null && acct.email != null && acct.id != null) {
//                            zygoteUser = ZygoteUser("tatjana.gundareva2@gmail.com",
//                                "tatjana.gundareva2@gmail.com" , "tatjana.gundareva2@gmail.com")
//                            zygoteUser.password = "012345"
                            val name=acct.givenName+"_g"
                            val login=acct.givenName+"_g"
                            val email="g_"+acct.email

                            zygoteUser = ZygoteUser(name, login, email)
                            zygoteUser.password = acct.id?.let { encodeString(it) }
                            setUserDataFromGoogle(zygoteUser)
                        }else showToast("givenName Error")
                    }else showToast("signInAccount Error")

                } else showToast("Auth.GoogleSignInApi Error")
            } else {
                showToast("UnhandledError")
            }
        }
//        else {
//            //facebook
//            callbackManager.onActivityResult(requestCode, resultCode, data)
//        }

        super.onActivityResult(requestCode, resultCode, data)
    }

    override fun onDestroy() {
        presenter.detachView()

        super.onDestroy()
    }
}