package com.testforme.newregistr.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ProfileViewModel : ViewModel() {

    private val _name = MutableLiveData<String>().apply {
        value = "Иванов"
    }
    val name: LiveData<String> = _name

    private val _phone = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val phone: LiveData<String> = _phone

    private val _email = MutableLiveData<String>().apply {
        value = "ivanov@mail.ru"
    }
    val email: LiveData<String> = _email

    private val _birthday = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val birthday: LiveData<String> = _birthday

    private val _dt_create = MutableLiveData<String>().apply {
        value = "This is home Fragment"
    }
    val dt_create: LiveData<String> = _dt_create

    private val _enabled = MutableLiveData<String>().apply {
        value = "Enable"
    }
    val enabled: LiveData<String> = _enabled

}