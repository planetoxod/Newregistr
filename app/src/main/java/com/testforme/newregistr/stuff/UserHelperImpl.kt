package com.testforme.newregistr.stuff

import com.google.gson.Gson
import com.testforme.newregistr.stuff.application.SharedPrefHelper
import com.testforme.newregistr.objects.User
import com.testforme.newregistr.stuff.observableStuff.UserObserver.UserObservableImpl
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class UserHelperImpl : UserObservableImpl() {

    var user: User? = null
        set(value) {
            field = value

            notifyUserChange()
        }

    fun updateUser(user: User) {
        this.user?.let {thisUser ->
            thisUser.id = user.id
            thisUser.birthday = user.birthday
            thisUser.avatar = user.avatar
            thisUser.name = user.name
            thisUser.dt_create = user.dt_create
            thisUser.email = user.email
            thisUser.enabled = user.enabled
            thisUser.phone = user.phone
            thisUser.token = user.token

            notifyUserUpdate()
        }
    }

    @DelicateCoroutinesApi
    fun removeUser() {
        this.user = null
        notifyUserRemove()
        GlobalScope.launch {
            SharedPrefHelper.getInstance().writePreferences("user", "")
        }
    }

    private fun notifyUserUpdate() {
        user?.let {
            updateI(it)
            SharedPrefHelper.getInstance().writePreferences("user", Gson().toJson(it))
        }
    }

    private fun notifyUserChange() {
        user?.let {
            changeI(it)
        }
    }

    private fun notifyUserRemove() {
        removeI()
    }

}