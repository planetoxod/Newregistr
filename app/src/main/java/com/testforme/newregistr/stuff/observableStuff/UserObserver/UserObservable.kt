package com.testforme.newregistr.stuff.observableStuff.UserObserver

import com.testforme.newregistr.objects.User

interface UserObservable {

    fun add(userObserver: UserObserver)
    fun remove(userObserver: UserObserver)
    fun removeAll()

    fun updateI(user: User)

    fun changeI(user: User)

    fun removeI()

}
