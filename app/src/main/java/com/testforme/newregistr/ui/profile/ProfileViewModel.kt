package com.testforme.newregistr.ui.profile

import android.content.Context
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.testforme.newregistr.R
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import kotlinx.coroutines.*

class ProfileViewModel() : ViewModel() {
    private lateinit var profileModel: ProfileModel
    private var somethingWent: String=""
    private var jobProgressDialog = GlobalScope.launch(){}

    fun setSomethingWent(context: Context){
        somethingWent=context.resources.getString(R.string.something_went)
        changeField()
    }

    fun setModel(model: ProfileModel){
        profileModel=model
        changeField()
    }

    fun showToast(errorText: ErrorText){
       _toast.value=errorText
    }

    fun showToast(text: String){
        _toastText.value=text
    }

    private fun changeField(){
        _name.value = user?.name ?: somethingWent
        _phone.value = user?.phone ?: somethingWent
        _email.value = user?.email ?: somethingWent
        _birthday.value = user?.birthday ?: somethingWent
        _dtCreate.value = user?.dt_create ?: somethingWent
        _enabled.value = user?.enabled ?: somethingWent
    }

    var user =  SharedPrefHelper.getInstance().getUserObject()

    private val _name = MutableLiveData<String>().apply {
        value = user?.name ?: somethingWent
    }
    val name: LiveData<String> = _name

    private val _phone = MutableLiveData<String>().apply {
        value = user?.phone ?: somethingWent
    }
    val phone: LiveData<String> = _phone

    private val _email = MutableLiveData<String>().apply {
        value = user?.email ?: somethingWent
    }
    val email: LiveData<String> = _email

    private val _birthday = MutableLiveData<String>().apply {
        value = user?.birthday ?: somethingWent
    }
    val birthday: LiveData<String> = _birthday

    private val _avatar = MutableLiveData<String>().apply {
        value = user?.avatarURL ?: somethingWent
    }

    val avatar: LiveData<String> = _avatar

    private val _dtCreate = MutableLiveData<String>().apply {
        value = user?.dt_create ?: somethingWent
    }
    val dtCreate: LiveData<String> = _dtCreate

    private val _enabled = MutableLiveData<String>().apply {
        value = user?.enabled ?: somethingWent
    }
    val enabled: LiveData<String> = _enabled

   private val _toast = MutableLiveData<ErrorText>()
    var toast: LiveData<ErrorText> = _toast

    private val _toastText = MutableLiveData<String>()
    var toastText: LiveData<String> = _toastText

    private val _showProgressDialog = MutableLiveData<Boolean>().apply {
        value = false
    }
    var showProgressDialog: LiveData<Boolean> = _showProgressDialog

    fun startRegister() {
        profileModel.startRegister()
    }

    fun progressDialogShow() {
        if (jobProgressDialog.isCompleted){
            jobProgressDialog =GlobalScope.launch() {
                delay(3000)
                _showProgressDialog.postValue(true)
            }
        }
    }

    fun progressDialogHide() {
        jobProgressDialog.cancel()
        _showProgressDialog.postValue(false)
    }
}