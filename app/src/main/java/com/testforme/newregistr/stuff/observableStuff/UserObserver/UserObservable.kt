package com.your_teachers.trafficrules.stuff.observableStuff.UserObserver

import com.your_teachers.trafficrules.objects.User

interface UserObservable {

    fun add(userObserver: UserObserver)
    fun remove(userObserver: UserObserver)
    fun removeAll()

    fun updateI(user: User)

    fun changeI(user: User)

    fun removeI()

}
