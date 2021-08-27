package com.your_teachers.trafficrules.stuff.baseMVP

interface BasePresenter<V : BaseView> {

    fun viewIsReady()

    fun attachView(view: V)

    fun detachView()

    fun getView(): V?

    fun isViewAttached(): Boolean
}
