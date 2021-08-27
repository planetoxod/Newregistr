package com.your_teachers.trafficrules.stuff.observableStuff.UserObserver

import com.your_teachers.trafficrules.objects.User

interface UserObserver {

    fun changeI(user: User)

    fun updateI(user: User)

    fun removeI()

}