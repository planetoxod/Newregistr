package com.your_teachers.trafficrules.stuff

import com.facebook.login.LoginManager
import com.google.gson.Gson
import com.your_teachers.trafficrules.stuff.application.SharedPrefHelper
import com.your_teachers.trafficrules.objects.User
import com.your_teachers.trafficrules.stuff.observableStuff.UserObserver.UserObservableImpl
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
            thisUser.login = user.login
            thisUser.name = user.name
            thisUser.imageURL = user.imageURL
            thisUser.email = user.email
            thisUser.registerDate = user.registerDate

            notifyUserUpdate()
        }
    }

    fun removeUser() {
        this.user = null
        notifyUserRemove()
        GlobalScope.launch {
            LoginManager.getInstance().logOut()
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