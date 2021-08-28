package com.testforme.newregistr.ui.updateUser.view

import android.os.Bundle
import android.text.Editable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.testforme.newregistr.stuff.application.SubApplication
import com.testforme.newregistr.ui.updateUser.model.UpdateUserModel
import com.testforme.newregistr.ui.updateUser.presenter.UpdateUserPresenter
import com.testforme.newregistr.ui.updateUser.stuff.UpdateUserContract
import com.testforme.newregistr.R
import com.testforme.newregistr.stuff.baseMVP.BaseFragmentImpl
import com.testforme.newregistr.objects.User
import com.xw.repo.XEditText
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.stuff.baseMVP.BaseFragmentImpl
import kotlinx.android.synthetic.main.edit_main.*
import kotlinx.coroutines.*

class UpdateUserView : BaseFragmentImpl(), UpdateUserContract.View {

    private var delayShowProgressDialog: Boolean = false

    private lateinit var presenter: UpdateUserPresenter

    private lateinit var progressDialog: SweetAlertDialog

    private var content: String? = null
    private var type: String? = null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.edit_main, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (savedInstanceState != null) {
            onRestoreInstanceStateI(savedInstanceState)
        } else {
            val bundle = this.arguments
            this.content = bundle?.getString("bundle")
            type = bundle?.getString("type")
        }

        initView(view)

        presenter.attachView(this)
    }


    override fun initView(view: View) {
        progressDialog = SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE)
        progressDialog.apply {
            setCancelable(false)
            showCancelButton(false)
            titleText = getString(R.string.please_wait)
        }

        editText.setText(content)
        editText.text?.length?.let { editText.setSelection(it) }

        editText.setOnXTextChangeListener(object : XEditText.OnXTextChangeListener{
            override fun afterTextChanged(s: Editable?) {
                s?.let {
                    if (s.isBlank())
                        editLayout.error = getString(R.string.error_empty)
                    else
                        editLayout.error = null
                }
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }
        })

        btnNext.setOnClickListener(mOnClickListener)
        btnPrevious.setOnClickListener(mOnClickListener)

        presenter = UpdateUserPresenter(UpdateUserModel())
    }

    private val mOnClickListener = View.OnClickListener { view ->
        when (view?.id) {
            R.id.btnNext -> {
                if (editText.text?.isBlank()!!) {
                    editLayout.error = getString(R.string.error_empty)
                } else {
                    val userHelper = (activity?.application as SubApplication).getUserHelper()
                    userHelper.user?.let { oldUser ->
                        val newUser = User(oldUser)

                        val result = editText.text.toString()
                        when (type) {
                            "name" -> newUser.name = result
                            "login" -> newUser.login = result
                            "email" -> newUser.email = result
                        }
                        presenter.updateUserInfo(newUser)
                    } ?: showToast(ErrorText.UserIsNullError)
                }
            }
            R.id.btnPrevious -> dismissI()
        }
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


    override fun showIconProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun cancelIconProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideIconProgressBar() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onRestoreInstanceStateI(savedInstanceState: Bundle) {
        type = savedInstanceState.getString("type")
        content = savedInstanceState.getString("bundle")
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        savedInstanceState.putString("type", type)
        savedInstanceState.putString("bundle", content)
        super.onSaveInstanceState(savedInstanceState)
    }


    override fun updateUser(user: User) {
        val userHelper = (activity?.application as SubApplication).getUserHelper()

        //setUser вызывает обновление всех подписчиков
        userHelper.user = user
    }

    override fun dismissI() {
        (parentFragment as BaseBottomSheet).dismiss()
    }

    override fun onDestroy() {
        presenter.detachView()

        super.onDestroy()
    }
}