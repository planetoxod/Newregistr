package com.testforme.newregistr.stuff.observableStuff.UserObserver

import com.testforme.newregistr.objects.User

interface UserObserver {

    fun changeI(user: User)

    fun updateI(user: User)

    fun removeI()

}