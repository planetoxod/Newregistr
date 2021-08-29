package com.testforme.newregistr.stuff.observableStuff.UserObserver

import com.testforme.newregistr.objects.User
import java.util.ArrayList

open class UserObservableImpl : UserObservable {

    private val mObservers = ArrayList<UserObserver>()

    override fun add(userObserver: UserObserver) {
        mObservers.add(userObserver)
    }

    override fun remove(userObserver: UserObserver) {
        mObservers.remove(userObserver)
    }

    override fun removeAll() {
        mObservers.clear()
    }

    override fun updateI(user: User) {
        for (observer in mObservers) {
            observer.updateI(user)
        }
    }

    override fun changeI(user: User) {
        for (observer in mObservers) {
            observer.changeI(user)
        }
    }

    override fun removeI() {
        for (observer in mObservers) {
            observer.removeI()
        }
    }
}