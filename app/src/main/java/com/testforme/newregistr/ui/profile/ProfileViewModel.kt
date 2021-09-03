package com.testforme.newregistr.ui.profile

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.testforme.newregistr.R
import com.testforme.newregistr.objects.ErrorText
import com.testforme.newregistr.stuff.application.SharedPrefHelper

class ProfileViewModel() : ViewModel() {
    private lateinit var profileModel: ProfileModel
    private var somethingWent: String=""

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

    private fun changeField(){
        _name.value = user?.name ?: somethingWent
        _phone.value = user?.phone ?: somethingWent
        _email.value = user?.email ?: somethingWent
        _birthday.value = user?.birthday ?: somethingWent
        _dtCreate.value = user?.dt_create ?: somethingWent
        _enabled.value = user?.enabled ?: somethingWent
    }

    val user =  SharedPrefHelper.getInstance().getUserObject()

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

    private val _dtCreate = MutableLiveData<String>().apply {
        value = user?.dt_create ?: somethingWent
    }
    val dtCreate: LiveData<String> = _dtCreate

    private val _enabled = MutableLiveData<String>().apply {
        value = user?.enabled ?: somethingWent
    }
    val enabled: LiveData<String> = _enabled

   private val _toast = MutableLiveData<ErrorText>().apply {
        value = ErrorText.NoError
   }
    var toast: LiveData<ErrorText> = _toast

    fun startRegister() {
        profileModel.startRegister()
    }
}