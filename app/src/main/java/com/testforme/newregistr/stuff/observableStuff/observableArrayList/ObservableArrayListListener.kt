package com.testforme.newregistr.stuff.observableStuff.observableArrayList

import java.util.*

interface ObservableArrayListListener<T> : EventListener {

    fun add(t: T)

    fun set()

    fun remove()

    fun clear()

    fun addAll()

    fun removeRange()

    fun retainAll()

    fun inAll()
}
